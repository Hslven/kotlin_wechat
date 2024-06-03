package com.example.test2dr

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    // 广播--弹出对话框要求你下线
    lateinit var receiver:ForceOfflineReceiver
    override fun onResume(){
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("go")
        receiver = ForceOfflineReceiver()
        registerReceiver(receiver, intentFilter)

    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    // 弹出一个对话框
    inner class ForceOfflineReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            AlertDialog.Builder(context).apply {
                setTitle("Warning")
                setMessage("你的账号在另一处登入，如果不是你的操作请重新登陆并修改你的密码")
                setCancelable(false)  // 将对话框设置为不可取消
                // 点击按钮进行注销
                setPositiveButton("好的"){_,_->
                    ActivityCollector.finishAll() // 销毁所有的Activity
                    val it = Intent(context, Loadingactivity::class.java)
                    context.startActivities(arrayOf(it)) //重新启动加载页面

                    // 因为被人登录了，把账号保留，密码不保留，同时取消勾选框
                    // 这里我把true传入数据库，在登录页面进行修改
                    val editor = getSharedPreferences("data", MODE_PRIVATE).edit()
                    editor.putBoolean("cleanpass",true)
                    editor.apply()
                }

                show()

            }
        }
    }


    // 退出整个程序
    override fun onCreate(savedInstanceState: Bundle?) {
        // 存入活动
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        // 删除存入活动
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}
