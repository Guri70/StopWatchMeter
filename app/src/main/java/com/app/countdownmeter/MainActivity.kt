package com.app.countdownmeter

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.app.countdownmeter.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var handler: Handler? = null

    var MillisecondTime: Long = 0
    var StartTime: Long = 0
    var TimeBuff: Long = 0
    var UpdateTime = 0L

    var Seconds: Int = 0
    var Minutes: Int = 0
    var MilliSeconds: Int = 0

    var timeCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

        initListener()
    }

    private fun initListener() {

        binding.tvStart.setOnClickListener {
            StartTime = SystemClock.uptimeMillis()
            handler?.postDelayed(runnable, 0)
        }
        binding.tvPause.setOnClickListener {
            handler?.removeCallbacks(runnable)
        }
        binding.tvStop.setOnClickListener {
            handler?.removeCallbacks(runnable)
            binding.tvSec.text = "00"
            binding.tvMilSec.text = "00"
            binding.tvMin.text = "00"
            binding.tvHours.text = "00"
        }

        binding.ivPlus.setOnClickListener {
            timeCount += 1
            binding.tvTime.text = timeCount.toString()
            StartTime = SystemClock.uptimeMillis()
            handler?.postDelayed(runnable, 0)
        }

        binding.ivMinus.setOnClickListener {
            if (timeCount == 1)
                return@setOnClickListener
            timeCount -= 1
            binding.tvTime.text = timeCount.toString()
            StartTime = SystemClock.uptimeMillis()
            handler?.postDelayed(runnable, 0)
        }

        handler = Handler()

    }

    var runnable: Runnable = object : Runnable {

        override fun run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime

            UpdateTime = TimeBuff + MillisecondTime

            Seconds = (UpdateTime / 1000).toInt()

            Minutes = Seconds / 60

            Seconds = Seconds % 60 * timeCount

            MilliSeconds = (UpdateTime % 1000).toInt()


            if (Minutes.toString().length < 2) {
                binding.tvMin.text = "0" + Minutes.toString()
            } else {
                binding.tvMin.text = Minutes.toString()
            }
            if (Seconds.toString().length < 2) {
                binding.tvSec.text = "0" + Seconds.toString()
            } else {
                binding.tvSec.text = Seconds.toString()
            }

            binding.tvMilSec.text = MilliSeconds.toString()

            handler?.postDelayed(this, 0)
        }

    }

}