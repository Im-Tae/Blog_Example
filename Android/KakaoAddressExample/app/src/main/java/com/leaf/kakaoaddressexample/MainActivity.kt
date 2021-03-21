package com.leaf.kakaoaddressexample

import android.app.Dialog
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        location_editText.setOnClickListener {
            showKakaoAddressWebView()
        }
    }

    private fun showKakaoAddressWebView() {

        webView.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            setSupportMultipleWindows(true)
        }

        webView.apply {
            addJavascriptInterface(WebViewData(), "php에서 적용한 name")
            webViewClient = client
            webChromeClient = chromeClient
            loadUrl("내 주소")
        }
    }

    private val client: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return false
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            handler?.proceed()
        }
    }

    private inner class WebViewData {
        @JavascriptInterface
        fun getAddress(zoneCode: String, roadAddress: String, buildingName: String) {

            CoroutineScope(Dispatchers.Default).launch {

                withContext(CoroutineScope(Dispatchers.Main).coroutineContext) {

                    location_editText.setText("($zoneCode) $roadAddress $buildingName")

                }
            }
        }
    }

    private val chromeClient = object : WebChromeClient() {

        override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message?): Boolean {

            val newWebView = WebView(this@MainActivity).apply {
                settings.javaScriptEnabled = true
            }

            val dialog = Dialog(this@MainActivity)

            dialog.setContentView(newWebView)

            val params = dialog.window!!.attributes.apply {
                width = ViewGroup.LayoutParams.MATCH_PARENT
                height = ViewGroup.LayoutParams.MATCH_PARENT
            }

            dialog.window!!.attributes = params
            dialog.show()

            newWebView.webChromeClient = object : WebChromeClient() {
                override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                    super.onJsAlert(view, url, message, result)
                    return true
                }

                override fun onCloseWindow(window: WebView?) {
                    dialog.dismiss()
                }
            }

            (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
            resultMsg.sendToTarget()

            return true
        }
    }
}