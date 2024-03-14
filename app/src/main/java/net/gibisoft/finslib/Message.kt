package net.gibisoft.finslib


class Message {

    private var message = intArrayOf()

    enum class ValueType {
        HEX, BCD, BIT
    }

    enum class MessageType {
        CONNECT, READ_MEMORY, WRITE_MEMORY
    }

    constructor(
        destinationNodeAddress: Int,
        memoryArea: Int, registerAddress:
        Int, length: Int
    ) {
        this(
            MessageType.READ_MEMORY,
            DEFAULT_GCT,
            DEFAULT_DNA,
            destinationNodeAddress,
            DEFAULT_DA2,
            DEFAULT_SNA,
            DEFAULT_SA1,
            DEFAULT_SA2,
            DEFAULT_SID,
            memoryArea,
            registerAddress,
            0,
            length,
            null,
            null
        )
    }

    constructor(
        destinationNodeAddress: Int,
        memoryArea: Int,
        registerAddress: Int,
        bits: Int,
        length: Int
    ) {
        this(
            MessageType.READ_MEMORY,
            DEFAULT_GCT,
            DEFAULT_DNA,
            destinationNodeAddress,
            DEFAULT_DA2,
            DEFAULT_SNA,
            DEFAULT_SA1,
            DEFAULT_SA2,
            DEFAULT_SID,
            memoryArea,
            registerAddress,
            bits,
            length,
            null,
            null
        )
    }

    constructor(
        destinationNodeAddress: Int,
        memoryArea: Int,
        registerAddress: Int,
        values: IntArray?,
        valueType: ValueType?
    ) {
        this(
            MessageType.WRITE_MEMORY,
            DEFAULT_GCT,
            DEFAULT_DNA,
            destinationNodeAddress,
            DEFAULT_DA2,
            DEFAULT_SNA,
            DEFAULT_SA1,
            DEFAULT_SA2,
            DEFAULT_SID,
            memoryArea,
            registerAddress,
            0,
            1,
            values,
            valueType
        )
    }

    constructor(
        destinationNodeAddress: Int,
        memoryArea: Int,
        registerAddress: Int,
        bits: Int,
        values: IntArray?,
        valueType: ValueType?
    ) {
        this(
            MessageType.WRITE_MEMORY,
            DEFAULT_GCT,
            DEFAULT_DNA,
            destinationNodeAddress,
            DEFAULT_DA2,
            DEFAULT_SNA,
            DEFAULT_SA1,
            DEFAULT_SA2,
            DEFAULT_SID,
            memoryArea,
            registerAddress,
            bits,
            1,
            values,
            valueType
        )
    }

    private operator fun invoke(
        type: MessageType,
        permissibleNumberOfGateways: Int,
        destinationNetworkAddress: Int,
        destinationNodeAddress: Int,
        destinationUnitAddress: Int,
        sourceNetworkAddress: Int,
        sourceNodeAddress: Int,
        sourceUnitAddress: Int,
        sourceId: Int,
        memoryArea: Int,
        registerAddress: Int,
        bits: Int,
        length: Int,
        values: IntArray?,
        valueType: ValueType?
    ) {
        append(ICF_COMMAND)
        append(RSV)
        append(permissibleNumberOfGateways)
        append(destinationNetworkAddress)
        append(destinationNodeAddress)
        append(destinationUnitAddress)
        append(sourceNetworkAddress)
        append(sourceNodeAddress)
        append(sourceUnitAddress)
        append(sourceId)
        if (type == MessageType.READ_MEMORY) {
            append(CMD_MEMORY_AREA_READ)
            append(memoryArea)
            append(toWord(registerAddress))
            append(bits)
            append(toWord(length))
        } else if (type == MessageType.WRITE_MEMORY) {
            append(CMD_MEMORY_AREA_WRITE)
            append(memoryArea)
            append(toWord(registerAddress))
            append(bits)
            append(toWord(values!!.size))
            when (valueType) {
                ValueType.BIT -> append(values[0])
                ValueType.BCD -> {
                    if (bits == 0) {
                        append(toBcdWord(values))
                    } else {
                        append(values)
                    }
                }

                else -> append(toWord(values))
            }
        }
    }

    fun getMessage(): IntArray {
        return message
    }

    fun getMessageBytes(): ByteArray {
        val res = ByteArray(message.size)
        for (i in message.indices) {
            res[i] = message[i].toByte()
        }
        return res
    }

    private fun getPayload(): IntArray {
        return message.slice(16..message.size).toIntArray()
    }

    fun getPayloadBytes(): ByteArray {
        val payload = getPayload()
        val res = ByteArray(payload.size)
        for (i in payload.indices) {
            res[i] = payload[i].toByte()
        }
        return res
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (i in message) {
            builder.append(String.format("%02x ", i))
        }
        builder.append("(length: ").append(message.size).append(")")
        return builder.toString()
    }

    fun toStringPayload(): String {
        val payload = getPayload()
        val builder = java.lang.StringBuilder()
        for (i in payload) {
            builder.append(String.format("%02x ", i))
        }
        builder.append("(length: ").append(payload.size).append(")")
        return builder.toString()
    }

    private fun append(bytes: IntArray) {
        val res = IntArray(message.size + bytes.size)
        System.arraycopy(message, 0, res, 0, message.size)
        System.arraycopy(bytes, 0, res, message.size, bytes.size)
        message = res
    }

    private fun append(singleByte: Int) {
        val toAppend = intArrayOf(singleByte)
        append(toAppend)
    }

    private fun toWord(hexNumber: Int): IntArray {
        return intArrayOf(
            (hexNumber ushr 8).toByte().toInt(),
            hexNumber.toByte().toInt()
        )
    }

    private fun toWord(hexNumberArray: IntArray): IntArray {
        val res = IntArray(hexNumberArray.size * 2)
        var i = 0
        var j = 0
        while (i < res.size) {
            val word = toWord(hexNumberArray[j])
            res[i] = word[0]
            res[i + 1] = word[1]
            i += 2
            j++
        }
        return res
    }

    private fun toBcdWord(decNumber: Int): IntArray {
        val upper = decNumber / 100
        val lower = decNumber % 100
        return intArrayOf(
            16 * (upper / 10) + upper % 10,
            16 * (lower / 10) + lower % 10
        )
    }

    private fun toBcdWord(decNumberArray: IntArray): IntArray {
        val res = IntArray(decNumberArray.size * 2)
        var i = 0
        var j = 0
        while (i < res.size) {
            val bcdWord = toBcdWord(decNumberArray[j])
            res[i] = bcdWord[0]
            res[i + 1] = bcdWord[1]
            i += 2
            j++
        }
        return res
    }

    companion object {
        const val UNKNOWN_VALUE = -1
        const val NX_ADDRESS = "192.168.250.1"
        const val NX_PORT = 9600
        const val MAC_WORK_BIT = 0x31
        const val MAC_WORK_WORD = 0x81
        const val MAC_DATA_MEM = 0x82
        const val DEFAULT_GCT = 0x02 //fixed, max 0x07
        const val DEFAULT_DNA = 0x00 //destination network address, 0x00 = local
        const val DEFAULT_DA1 = 0x01 //destination node address,
        const val DEFAULT_DA2 = 0x00 //destination unit address, 0x00 = CPU
        const val DEFAULT_SNA = 0x00 //source network address, 0x00 = local
        const val DEFAULT_SA1 = 0x0a //source node address, 0x00 = CPU
        const val DEFAULT_SA2 = 0x00 //source unit address, 0x00 = local
        const val DEFAULT_SID = 0x00 //source ID
        const val ICF_COMMAND = 0x80
        const val ICF_RESPONSE = 0xC0
        const val RSV = 0x00
        val CMD_MEMORY_AREA_READ = intArrayOf(0x01, 0x01)
        val CMD_MEMORY_AREA_WRITE = intArrayOf(0x01, 0x02)

    }
}