package net.gibisoft.finslib

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import net.gibisoft.talamonti.R
import java.io.IOException
import java.net.InetAddress
import java.util.BitSet

object Utilities {
    fun toHexString(arr: IntArray): String {
        val builder = StringBuilder()
        for (i in arr) {
            builder.append(String.format("%02x ", i))
        }
        builder.append("(length: ").append(arr.size).append(")")
        return builder.toString()
    }

    fun toHexString(arr: ByteArray): String {
        val builder = StringBuilder()
        for (i in arr) {
            builder.append(String.format("%02x ", i))
        }
        builder.append("(length: ").append(arr.size).append(")")
        return builder.toString()
    }

    fun toBitSet(value: Int): BitSet {
        var varValue = value
        val bitSet = BitSet()
        var bit = 0
        while (varValue > 0) {
            bitSet[bit++] = varValue % 2 == 1
            varValue = varValue shr 1
        }
        return bitSet
    }

    fun toBitSet(values: IntArray): BitSet {
        val bitSet = BitSet()
        var bit = 0
        for (i in values.indices) {
            while (values[i] > 0) {
                bitSet[bit++] = values[i] % 2 == 1
                values[i] = values[i] shr 1
            }
        }
        return bitSet
    }

    fun checkResponse(context: Context?, mes: IntArray, rsp: IntArray?): Boolean {
        if (rsp == null) {
            Log.e("checkResponse", ContextCompat.getString(context!!, R.string.E010))
            return false
        }
        if (rsp.size < 14) {
            Log.e("checkResponse", ContextCompat.getString(context!!, R.string.E001))
            return false
        }
        if (rsp[0] != Message.ICF_RESPONSE) {
            Log.e("checkResponse", "error ICF_RESPONSE: ${rsp[0]}")
            return false
        }
        if (mes[3] != rsp[6]) {
            Log.e("checkResponse", ContextCompat.getString(context!!, R.string.E002))
            return false
        }
        if (mes[4] != rsp[7]) {
            Log.e("checkResponse", ContextCompat.getString(context!!, R.string.E003))
            return false
        }
        if (mes[5] != rsp[8]) {
            Log.e("checkResponse", ContextCompat.getString(context!!, R.string.E004))
            return false
        }
        if (mes[9] != rsp[9]) {
            Log.e("checkResponse", ContextCompat.getString(context!!, R.string.E005))
            return false
        }
        return if (rsp[12] == 0x00) {
            Log.e("checkResponse", ContextCompat.getString(context!!, R.string.M000))
            true
        } else {
            Log.e(
                "checkResponse",
                String.format("error: %02x,%02x", rsp[12], rsp[13])
            )
            false
        }
    }

    fun pingHost(ipaddress: String?): Boolean {
        return try {
            val inet = InetAddress.getByName(ipaddress)
            inet.isReachable(2000)
        } catch (ex: IOException) {
            Log.e("pingHost", ex.toString())
            false
        }
    }
}
