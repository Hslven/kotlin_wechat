package com.example.test2dr

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.concurrent.thread


// 朋友界面
class MsgListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend1)
        val back:TextView = findViewById(R.id.error_dark_png)

        //把聊天的最后一句话放在页面
        val now_text:TextView = findViewById(R.id.now_text)
        val prefs = getSharedPreferences("data", MODE_PRIVATE)
        now_text.setText(prefs.getString("now_neirong","晚上一起吃饭？"))

        back.setOnClickListener(){
            //退出当前活动
            finish()
        }

        // 强制下线
        val go_out:Button = findViewById(R.id.go_out)
        go_out.setOnClickListener(){
//            val intent = Intent("com.example.broadcastbestpractice.FORCE_OFFLINE")
            val intent = Intent("go")
            sendBroadcast(intent)
        }

        // 跳转到天气列表
        val weather:Button = findViewById(R.id.weatherlook)
        weather.setOnClickListener() {
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
            SendRquestOkhttp()
        }

        //和朋友聊天
        val chat:TextView = findViewById(R.id.now_text)
        val chatone:TextView = findViewById(R.id.text0)
        val chattwo:ImageView = findViewById(R.id.img0)
        val it2 = Intent(this,ChatActivity::class.java)

        chat.setOnClickListener(){
            startActivity(it2)
            finish()
        }
        chatone.setOnClickListener(){
            startActivity(it2)
            finish()

        }
        chattwo.setOnClickListener(){
            startActivity(it2)
            finish()
        }
    }

    private fun SendRquestOkhttp()
    // 开启线程发起网络请求
    {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://www.yiketianqi.com/free/day?appid=95979945&appsecret=pg8gdx39&unescape=1&cityid=101280803")
                    .build()
                // execute发送请求返回数据
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    show(responseData)
                } else {
                    Toast.makeText(this, "no", Toast.LENGTH_LONG).show()


                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private  fun show(res:String) {
        try {
            val jsondata= JSONObject(res)
            val cityname=jsondata.getString("city")
            val date=jsondata.getString("date")
            val wea=jsondata.getString("wea")
            val week=jsondata.getString("week")
            val nowtem=jsondata.getString("tem")
            val tem_day=jsondata.getString("tem_day")
            val tem_night=jsondata.getString("tem_night")
            val win=jsondata.getString("win")

            val res="$date 城市:$cityname $week,天气如下,天气情况:$wea \n 现在温度:$nowtem 白天最高温度:$tem_day 晚上最高温度:$tem_night 风向:$win "
            showResponse(res)

        }catch (e:Exception)
        {e.printStackTrace()
        }
    }

    private fun showResponse(response: String) {
        val weather_text: TextView = findViewById(R.id.weather_text)

        runOnUiThread {
            //这里进行UI操作，将结果显示到界面上
            weather_text.text = response
        }
    }
}
