package com.example.dialogexample

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    var progress: ProgressDialog? = null

    val data1 = arrayOf("1번째", "2번째", "3번째", "4번째", "5번째", "6번째", "7번째")

    val data2 = arrayOf("1111", "2222", "3333", "4444", "5555", "6666", "7777")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("제목")
                .setMessage("내용")
                .setIcon(R.mipmap.ic_launcher)
                .setNeutralButton("닫기", DialogListener1())
                .setPositiveButton("확인", DialogListener1())
                .setNegativeButton("취소", DialogListener1())
                .show()
        }

        button2.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("커스텀 다이얼로그 제목")
                .setIcon(R.mipmap.ic_launcher)
                .setView(layoutInflater.inflate(R.layout.dialog, null))
                .setPositiveButton("확인", DialogListener2())
                .setNegativeButton("취소", DialogListener2())
                .show()
        }

        button3.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, DatePickerListener(), year, month, day).show()
        }

        button4.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, TimePickerListener(), hour, minute, false).show()
        }

        button5.setOnClickListener {
            progress = ProgressDialog.show(this, "제목", "내용")

            val handler = Handler()

            val thread = Runnable { progress?.cancel() }

            handler.postDelayed(thread, 5000)
        }

        button6.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("리스트 다이얼로그 제목")
                .setNegativeButton("취소", ListListener())
                .setItems(data1, ListListener())
                .show()
        }

        button7.setOnClickListener {

            val list = ArrayList<HashMap<String, Any>>()

            for (i in data1.indices) {
                val map = HashMap<String, Any>()

                map.put("data1", data1[i])
                map.put("data2", data2[i])

                list.add(map)
            }

            val keys = arrayOf("data1", "data2")
            val ids = intArrayOf(R.id.list_textView1, R.id.list_textView2)

            val adapter = SimpleAdapter(this, list, R.layout.custom_dialog, keys, ids)


            AlertDialog.Builder(this)
                .setTitle("커스텀 리스트 다이얼로그 제목")
                .setAdapter(adapter, CustomListListener())
                .setNegativeButton("취소", null)
                .show()
        }
    }

    inner class DialogListener1: DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when(which) {
                DialogInterface.BUTTON_POSITIVE -> textView.text = "POSITIVE 버튼 클릭"
                DialogInterface.BUTTON_NEGATIVE -> textView.text = "NEGATIVE 버튼 클릭"
                DialogInterface.BUTTON_NEUTRAL -> textView.text = "NEUTRAL 버튼 클릭"
            }
        }
    }

    inner class DialogListener2: DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            val alert = dialog as AlertDialog
            val editText1: EditText? = alert.findViewById(R.id.editText1)
            val editText2: EditText? = alert.findViewById(R.id.editText2)

            textView.text = "${editText1?.text} + ${editText2?.text}"
        }
    }

    inner class DatePickerListener: DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            textView.text = "$year.$month.$dayOfMonth"
        }
    }

    inner class TimePickerListener: TimePickerDialog.OnTimeSetListener {
        override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
            textView.text = "$hourOfDay 시 $minute 분"
        }
    }

    inner class ListListener: DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            textView.text = "${which + 1}번째 선택"
        }
    }

    inner class CustomListListener: DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            textView.text = "${which + 1}번째 선택"
        }
    }
}