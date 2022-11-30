package com.jubging.jubging.ui.jubging

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

@SuppressLint("MissingPermission")
class ConnectThread(
    private val myUUID: UUID,
    private val device: BluetoothDevice,
    private val handler: Handler
) : Thread() {
    companion object {
        private const val TAG = "CONNECT_THREAD"
    }

    private lateinit var connectedThread: ConnectedThread
    // BluetoothDevice 로부터 BluetoothSocket 획득
    private val connectSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        device.createRfcommSocketToServiceRecord(myUUID)
    }

    private inner class ConnectedThread(private val bluetoothSocket: BluetoothSocket) : Thread() {
        private lateinit var inputStream: InputStream
        private lateinit var outputStream: OutputStream
        init {
            try {
                // BluetoothSocket의 InputStream, OutputStream 초기화
                inputStream = bluetoothSocket.inputStream
                outputStream = bluetoothSocket.outputStream
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }

        override fun run() {
            Log.d(TAG, this.name)

            val buffer = ByteArray(1024)
            var bytes: Int

            //데이터 보내기
            //무게요청
            write("a")
            //뚜껑 열림
            write("b")
            while (true) {
                try {
                    // 데이터 받기(읽기)
                    bytes = inputStream.read(buffer)
                    var str: String = ""
                    for (i in 0..bytes-1){
                        str += buffer[i].toChar()
                    }
                    var msg: Message = Message()
                    var data = Bundle()
                    data.putString("data",str)
                    msg.data = data
                    handler.handleMessage(msg)
                    Log.d(TAG,this.name + "에서보냄")
                } catch (e: Exception) { // 기기와의 연결이 끊기면 호출
                    e.printStackTrace()
                    Log.d(TAG, "기기와의 연결이 끊겼습니다.")
                    break
                }
            }
        }

        fun write(str: String) {
            val bytes: ByteArray = str.toByteArray()

            try {
                // 데이터 전송
                outputStream.write(bytes)
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }

        fun cancel() {
            try {
                bluetoothSocket.close()
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
            }
        }
    }

    fun requestWeight(){
        connectedThread.write("a")
    }

    fun requestOpen(){
        connectedThread.write("b")
    }
    fun requestClose(){
        connectedThread.write("c")
    }

    override fun run() {
        try {
            // 연결 수행
            connectSocket?.connect()
            connectSocket?.let {
                connectedThread = ConnectedThread(bluetoothSocket = it)
                connectedThread.start()
            }
        } catch (e: IOException) { // 기기와의 연결이 실패할 경우 호출
            connectSocket?.close()
            throw Exception("연결 실패")
        }
    }

    fun cancel() {
        try {
            connectSocket?.close()
        } catch (e: IOException) {
            Log.d(TAG, e.message.toString())
        }
    }
}