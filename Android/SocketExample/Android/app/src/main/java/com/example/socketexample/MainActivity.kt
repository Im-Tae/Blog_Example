package com.example.socketexample

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.lang.Exception
import java.net.Socket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            SocketAsyncTask().execute()
        }
    }

    inner class SocketAsyncTask : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void?): String {
            try {

                val socket = Socket("192.168.55.131", 7777)

                val input = socket.getInputStream()
                val dataInputStream = DataInputStream(input)

                val output = socket.getOutputStream()
                val dataOutputStream = DataOutputStream(output)

                val intData = dataInputStream.readInt()
                val stringData = dataInputStream.readUTF()

                dataOutputStream.writeInt(numberEditText.text.toString().toInt())
                dataOutputStream.writeUTF(stringEditText.text.toString())

                socket.close()

                runOnUiThread {
                    serverDataTextView.text = "서버에서 받은 숫자 : $intData \n 서버에서 받은 문자열 : $stringData"
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return ""
        }
    }
}