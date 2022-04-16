package com.yjh.yjhbroadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.widget.Button
import android.widget.Toast

/*
* BroadcastReceiver的基本用法
* 1. 动态注册接收广播：监听系统时间变化：TimeChangeReceiver
* 2. 静态注册接收广播：监听开机：BootCompleteReceiver。主要是写在AndroidManifest配置文件里
* 3. 发送自定义广播：MyBroadcastReceiver。发送有序广播：AnotherBroadcastReceiver
* */
class MainActivity : AppCompatActivity() {

    private lateinit var timeChangeReceiver: TimeChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //3. 发送自定义广播
        val button:Button = findViewById(R.id.sendBtn)
        button.setOnClickListener {
            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
            intent.setPackage(packageName)
//            sendBroadcast(intent) //发送标准广播
            sendOrderedBroadcast(intent, null) //发送有序广播
        }

        //1. 动态注册接收广播
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")//时间变化时，系统发出的广播值为android.intent.action.TIME_TICK
        timeChangeReceiver = TimeChangeReceiver()
        registerReceiver(timeChangeReceiver, intentFilter)
    }

    inner class TimeChangeReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Toast.makeText(context, "Time has changed", Toast.LENGTH_SHORT).show()
        }

    }

    //动态注册的BroadcastReceiver一定要取消注册
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }
}