package com.example.test2dr

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register1.*

class register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)
        val back:ImageView = findViewById(R.id.error_png)
        register.setOnClickListener() {

            // 定义函数
            val user = ruser.text.toString()
            val pass = rpassword.text.toString()

            // 判断男女
            val sex_nan = sexButton1.isChecked()
            val sex_nav = sexButton2.isChecked()

            // 调用数据库--存入和取出
            val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
//          val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            val editor = prefs.edit() //存入


            // 如果已有相同用户名--注册失败
            // 1) 获取已存用户名
            val exist_user = prefs.getString("user", "")

            // 2) 进行判断
            if (user == exist_user) {
                Toast.makeText(this, "用户名已存在，无法注册！", Toast.LENGTH_LONG).show()
            } else {
                // 3) 注册成功--把各种数据存入数据库
                editor.putString("user", user)
                editor.putString("pass", pass)
                if (sex_nan) {
                    editor.putString("sex", "man")
                } else if (sex_nav) {
                    editor.putString("sex", "woman")
                } else {
                    editor.putString("sex", "hermaphrodite")
                }
                editor.apply() // 提交数据

                // 3.1)注册成功--跳转
                Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show()
                val it = Intent(this, MainActivity::class.java)
                startActivity(it)
            }


        }

        back.setOnClickListener(){
            finish()
        }
    }
}