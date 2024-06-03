package com.example.test2dr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*

class Loadingactivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        val main = Intent(this,MainActivity::class.java)
        var timer= Timer()
        class MyTimerTask():TimerTask(){
            override fun run() {
                startActivity(main)
                finish()
            }
        }
        timer.schedule(MyTimerTask(),1000)
    }
}