package com.example.httpexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.w3c.dom.Element
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        github_button.setOnClickListener {
            GitHubThread().start()
        }
    }

    inner class GitHubThread : Thread() {

        override fun run() {

            val url = URL("https://api.github.com/zen")
            val connection = url.openConnection()

            val input = connection.getInputStream()
            val inputStreamReader = InputStreamReader(input, "UTF-8")
            val bufferReader = BufferedReader(inputStreamReader)

            var str: String? = null

            val stringBuffer = StringBuffer()

            do {
                str = bufferReader.readLine()

                if (str != null) {
                    stringBuffer.append(str)
                }
            } while (str != null)

            val data = stringBuffer.toString()

            runOnUiThread {
                textView.text = data
            }
        }
    }

    inner class XMLThread : Thread() {

        override fun run() {

            val url = URL("링크")
            val connection = url.openConnection()

            val input = connection.getInputStream()

            val factory = DocumentBuilderFactory.newInstance()
            val builder = factory.newDocumentBuilder()
            val document = builder.parse(input)

            val root = document.documentElement

            val itemList = root.getElementsByTagName("item")

            for (i in 0 until itemList.length) {
                val itemElement = itemList.item(i) as Element

                val nameList = itemElement.getElementsByTagName("name")
                val ageList = itemElement.getElementsByTagName("age")

                val name = nameList.item(0) as Element
                val age = ageList.item(0) as Element
            }

        }
    }

    inner class JSONThread : Thread() {

        override fun run() {

            val url = URL("링크")
            val connection = url.openConnection()

            val input = connection.getInputStream()
            val inputStreamReader = InputStreamReader(input)
            val bufferReader = BufferedReader(inputStreamReader)

            var str: String? = null

            val stringBuffer = StringBuffer()

            do {
                str = bufferReader.readLine()

                if (str != null) {
                    stringBuffer.append(str)
                }
            } while (str != null)

            val root = JSONArray(stringBuffer.toString())

            for (i in 0 until root.length()) {

                val obj = root.getJSONObject(i)

                val name = obj.getString("name")
                val age = obj.getInt("age")
            }
        }
    }
}