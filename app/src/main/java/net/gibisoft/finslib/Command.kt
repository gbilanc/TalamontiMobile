package net.gibisoft.finslib

import java.nio.CharBuffer
import java.nio.charset.StandardCharsets
import java.util.Arrays


class Command(
    private val nodeNumber: String,
    private val headerCode: String,
    private val beginningWord: String,
    private val numberOfWords: String
) {

    fun getByteArray(): ByteArray {
        val charBuffer = CharBuffer.allocate(17)
        charBuffer.put('@')
        charBuffer.put(nodeNumber)
        charBuffer.put(headerCode)
        charBuffer.put(beginningWord)
        charBuffer.put(numberOfWords)
        charBuffer.put(getFcs(charBuffer.array()))
        charBuffer.put('*')
        charBuffer.put(0x0D.toChar())
        charBuffer.rewind()
        return toBytes(charBuffer)
    }

    private fun getFcs(ca: CharArray): String {
        var fcs = 0
        for (i in ca.indices) {
            fcs = fcs xor ca[i].code
        }
        return String.format("%02X", fcs)
    }

    private fun toBytes(charBuffer: CharBuffer): ByteArray {
        val byteBuffer = StandardCharsets.US_ASCII.encode(charBuffer)
        val bytes = Arrays.copyOfRange(
            byteBuffer.array(),
            byteBuffer.position(),
            byteBuffer.limit()
        )
        Arrays.fill(charBuffer.array(), '\u0000') // clear sensitive data
        Arrays.fill(byteBuffer.array(), 0.toByte()) // clear sensitive data
        return bytes
    }
}