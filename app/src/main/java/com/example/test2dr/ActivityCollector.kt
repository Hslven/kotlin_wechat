package com.example.test2dr

import android.app.Activity

// 这是一个 kotlin class

    object ActivityCollector {
        private val activities=ArrayList<Activity>()

        /* 用于向ArrayList中添加Activity，创建活动时 */
        fun addActivity(actity:Activity){
            activities.add(actity)
        }


        /* 用于从ArrayList中移除Activity */
        fun removeActivity(actity: Activity){
            activities.remove(actity)
        }

        /* 用于将ArrayList中存储的Activity全部销毁 */
        fun finishAll(){
            for (activity in activities){
                if (!activity.isFinishing){
                    activity.finish()
                }
            }
            activities.clear()
        }
    }

