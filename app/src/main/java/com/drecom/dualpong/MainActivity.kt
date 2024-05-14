package com.drecom.dualpong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.SeekBar
import com.drecom.dualpong.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pongView = PongView(this)
        binding.framePong.addView(pongView)

        GlobalScope.launch {
            delay(10000)
            runOnUiThread {
                pongView.start()
            }
        }

        binding.controlL.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action ){
                MotionEvent.ACTION_DOWN -> {
                    binding.controlL.thumb = getDrawable(R.drawable.l2)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    binding.controlL.thumb = getDrawable(R.drawable.l1)
                    false
                }
                else -> false
            }
        }

        binding.controlR.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action ){
                MotionEvent.ACTION_DOWN -> {
                    binding.controlR.thumb = getDrawable(R.drawable.r2)
                    false
                }
                MotionEvent.ACTION_UP -> {
                    binding.controlR.thumb = getDrawable(R.drawable.r1)
                    false
                }
                else -> false
            }
        }

        binding.controlL.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, posicion: Int, p2: Boolean) {
                pongView.posicionControlL = posicion
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        binding.controlR.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, posicion: Int, p2: Boolean) {
                pongView.posicionControlR = posicion
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

    }
}