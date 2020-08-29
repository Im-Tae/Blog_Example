package com.imtae.bitmapencodedecodeexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        button.setOnClickListener {
            TedImagePicker.with(this)
                    .start { uri ->

                        Log.d("test", uri.toString())

                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

                        pref.edit().putString("bitmap", bitmapToString(bitmap)).apply()

                        image.setImageBitmap(bitmap)
                    }
        }

        if (pref.getString("bitmap", null) != "null") {
            val bitmap = stringToBitmap(pref.getString("bitmap", null).toString())

            image.setImageBitmap(bitmap)
        }

    }

    private fun bitmapToString(bitmap: Bitmap): String {

        val byteArrayOutputStream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun stringToBitmap(encodedString: String): Bitmap {

        val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)

        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    }

}