package com.example.btmonitor

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*

class ConnectThread(private val device: BluetoothDevice, private val listener: ReceiveThread.Listener) : Thread() {
    private val uuid = "00001101-0000-1000-8000-00805F9B34FB"
    private var mySocket: BluetoothSocket? = null
    lateinit var rThread: ReceiveThread

    init {
        try {
            mySocket = device.createRfcommSocketToServiceRecord(UUID.fromString(uuid))
        } catch (i: IOException) {

        }
    }

    override fun run() {
        try {
            listener.onReceive("Connecting...")
            mySocket?.connect()
            listener.onReceive("Connected")
            rThread = ReceiveThread(mySocket!!, listener)
            rThread.start()
        } catch (i: IOException) {
            listener.onReceive("Can not connect to device")
            closeConnection()
        }
    }

    fun closeConnection() {
        try {
            mySocket?.close()
        } catch (i: IOException) {

        }
    }
}