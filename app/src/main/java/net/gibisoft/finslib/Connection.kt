package net.gibisoft.finslib

import android.content.Context
import android.util.Log
import net.gibisoft.finslib.Utilities.checkResponse
import net.gibisoft.finslib.Utilities.toHexString
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.util.Arrays


class Connection(val context: Context, val serverAddress: String, val serverPort: Int) {

    private val SO_TIMEOUT: Int = 2000

    fun sendFinsMessage(message: Message): IntArray {
        val response = IntArray(32)
        try {
            DatagramSocket().use { ss ->
                ss.setSoTimeout(SO_TIMEOUT)
                val address = InetAddress.getByName(serverAddress)
                val msb: ByteArray = message.getMessageBytes()
                val dgreq =
                    DatagramPacket(msb, msb.size, address, serverPort)
                ss.send(dgreq)
                val buffer = ByteArray(32)
                val dgres = DatagramPacket(buffer, buffer.size)
                ss.receive(dgres)
                val nb = dgres.data
                for (i in 0..31) {
                    response[i] = nb[i].toInt() and 0xff
                }
            }
        } catch (ex: SocketTimeoutException) {
            Log.e("sendFinsMessage", "Timeout error: ${ex.message}")
        } catch (ex: IOException) {
            Log.e("sendFinsMessage", "Client error: ${ex.message}")
        }
        return response
    }

    fun writeRegister(memoryArea: Int, registerAddress: Int, values: IntArray?): Boolean {
        val tokens = serverAddress.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val destination = tokens[3].toInt()
        val fm = Message(
            destination,
            memoryArea, registerAddress, values, null
        )
        Log.i("writeRegister", "request: $fm")
        val response = sendFinsMessage(fm)
        Log.i("writeRegister", "response: ${toHexString(response)}")
        return checkResponse(context, fm.getMessage(), response)
    }

    fun writeBcdRegister(memoryArea: Int, registerAddress: Int, values: IntArray?): Boolean {
        val tokens = serverAddress.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val destination = tokens[3].toInt()
        val fm = Message(
            destination,
            memoryArea, registerAddress, values, Message.ValueType.BCD
        )
        Log.i("writeBcdRegister", "request: $fm")
        val response = sendFinsMessage(fm)
        Log.i("writeBcdRegister", "response: ${toHexString(response)}")
        return checkResponse(context, fm.getMessage(), response)
    }

    fun writeRegisterBits(
        memoryArea: Int,
        registerAddress: Int,
        bits: Int,
        values: IntArray?
    ): Boolean {
        val tokens = serverAddress.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val destination = tokens[3].toInt()
        val fm = Message(
            destination,
            memoryArea, registerAddress, bits, values, null
        )
        Log.i("writeRegisterBits", "request: $fm")
        val response = sendFinsMessage(fm)
        Log.i("writeRegisterBits", "response: ${toHexString(response)}")
        return checkResponse(context, fm.getMessage(), response)
    }

    fun readRegisterByte(memoryArea: Int, registerAddress: Int): Int {
        var payload: Int = Message.UNKNOWN_VALUE
        val tokens = serverAddress.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val destination = tokens[3].toInt()
        val fm = Message(
            destination,
            memoryArea, registerAddress, 1
        )
        Log.i("readRegisterByte", "request: $fm")
        val response = sendFinsMessage(fm)
        Log.i("readRegisterByte", "response: ${toHexString(response)}")
        if (checkResponse(context, fm.getMessage(), response)) {
            payload = response[14]
        }
        return payload
    }

    fun readRegisterBit(memoryArea: Int, registerAddress: Int, bits: Int): Int {
        var payload: Int = Message.UNKNOWN_VALUE
        val tokens = serverAddress.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val destination = tokens[3].toInt()
        val fm = Message(
            destination,
            memoryArea, registerAddress, bits, 1
        )
        Log.i("readRegisterBit", "request: $fm")
        val response = sendFinsMessage(fm)
        Log.i("readRegisterBit", "response: ${toHexString(response)}")
        if (checkResponse(context, fm.getMessage(), response)) {
            payload = response[14]
        }
        return payload
    }

    fun readRegisterBytes(memoryArea: Int, registerAddress: Int, size: Int): IntArray {
        var payload = intArrayOf(Message.UNKNOWN_VALUE)
        val tokens = serverAddress.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }
            .toTypedArray()
        val destination = tokens[3].toInt()
        val fm = Message(
            destination,
            memoryArea, registerAddress, size
        )
        Log.i("readRegisterBytes", "request: $fm")
        val response = sendFinsMessage(fm)
        Log.i("readRegisterBytes", "response: ${toHexString(response)}")
        if (checkResponse(context, fm.getMessage(), response)) {
            payload = Arrays.copyOfRange(response, 14, response.size - 3)
        }
        return payload
    }

    companion object {
        fun newInstance(context: Context, address: String, port: Int): Connection {
            return Connection(context, address, port)
        }

    }
}