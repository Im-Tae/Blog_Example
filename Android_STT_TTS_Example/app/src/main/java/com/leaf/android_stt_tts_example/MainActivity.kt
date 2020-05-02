package com.leaf.android_stt_tts_example

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // permission 확인
        if (Build.VERSION.SDK_INT >= 23)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO), REQUEST_CODE)

        // tts에 TextToSpeech 값 넣어줌
        tts = TextToSpeech(this, this)

        // TTSButton 클릭시 startTTS() 함수 실행
        TTSButton.setOnClickListener { startTTS() }

        // STTButton 클릭시 onActivityResult를 이용한 startSTT() 함수 실행
        UseActivityResultSTTButton.setOnClickListener { startSTTUseActivityResult() }

        // STTButton 클릭시 startSTT() 함수 실행
        STTButton.setOnClickListener { startSTT() }
    }


    // RecognitionListener 사용한 예제
    private  fun startSTT() {
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(recognitionListener)
            startListening(speechRecognizerIntent)
        }

    }

    private val recognitionListener = object : RecognitionListener {

        override fun onReadyForSpeech(params: Bundle?) = Toast.makeText(this@MainActivity, "음성인식 시작", Toast.LENGTH_SHORT).show()

        override fun onRmsChanged(rmsdB: Float) {}

        override fun onBufferReceived(buffer: ByteArray?) {}

        override fun onPartialResults(partialResults: Bundle?) {}

        override fun onEvent(eventType: Int, params: Bundle?) {}

        override fun onBeginningOfSpeech() {}

        override fun onEndOfSpeech() {}

        override fun onError(error: Int) {
            when(error) {
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> Toast.makeText(this@MainActivity, "퍼미션 없음", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResults(results: Bundle) {
            viewText.text = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)!![0]
        }
    }



    // onActivityResult 사용한 예제
    private fun startSTTUseActivityResult() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            //putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        startActivityForResult(speechRecognizerIntent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            viewText.text = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]
        }
    }



    // TTS 예제
    private fun startTTS() {
        tts!!.speak(viewText.text.toString(), TextToSpeech.QUEUE_FLUSH, null, "")
    }

    // TextToSpeech override 함수
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.KOREA)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //doSomething
            } else {
                //doSomething
            }
        } else {
            //doSomething
        }

    }


    override fun onDestroy() {

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (speechRecognizer != null) {
            speechRecognizer!!.stopListening()
        }

        super.onDestroy()
    }
}
