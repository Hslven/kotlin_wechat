package com.example.test2dr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

    class ChatActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_chat)

            val send:Button = findViewById(R.id.send)
            val replay:TextView = findViewById(R.id.replay)
            val text:TextView = findViewById(R.id.input_text)
            val back_now:ImageView = findViewById(R.id.back_now)

            // 建立数据库
            val prefs = getSharedPreferences("data", MODE_PRIVATE)
            val editor = prefs.edit()

            // 进入保留内容(只保留一次)
            replay.setText(prefs.getString("neirong","")+ "\n")

            // 然后清空
            editor.putString("neirong","")
            editor.apply()

            send.setOnClickListener(){
                // 存入最后一句话
                val textEdit = text.text.toString()
                editor.putString("now_neirong",textEdit)
                editor.apply()

                // 给文本框写入输入的内容
                replay.setText(prefs.getString("neirong","")+ textEdit + "\n")

                // 保留文本
                editor.putString("neirong",replay.text.toString())
                editor.apply()

                // 发送后清空
                text.setText("")
            }

            // 退出活动
            back_now.setOnClickListener(){
                val it2 = Intent(this,MsgListActivity::class.java)
                startActivity(it2)
                finish()
            }
        }


    }