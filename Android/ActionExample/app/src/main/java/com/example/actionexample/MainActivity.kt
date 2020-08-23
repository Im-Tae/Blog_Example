package com.example.actionexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val permissionList = arrayOf(
        Manifest.permission.CALL_PHONE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        button_search.setOnClickListener {
            val uri = Uri.parse("http://google.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        button_map.setOnClickListener {
            val uri = Uri.parse("geo:35.159520, 126.852678")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        button_dial.setOnClickListener {
            val uri = Uri.parse("tel:010-0000-0000")
            val intent = Intent(Intent.ACTION_DIAL, uri)
            startActivity(intent)
        }

        button_call.setOnClickListener {
            val uri = Uri.parse("tel:010-0000-0000")
            val intent = Intent(Intent.ACTION_CALL, uri)
            startActivity(intent)
        }
    }

    private fun checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            for (permission in permissionList) {

                if (checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(permissionList, 0)
                }
            }
        }
    }
}