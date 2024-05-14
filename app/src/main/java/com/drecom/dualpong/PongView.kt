package com.drecom.dualpong

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.Log
import android.view.View
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PongView(context:Context): View(context) {
    private val handler = Handler()
    private val runnable = object : Runnable{
        override fun run() {
            moverPelota()
            handler.postDelayed(this,10)
        }
    }

    private var cordX:Float? = null
    private var cordY:Float?= null

    var posicionControlL = 0
    var posicionControlR = 0

    private var avanceX = 15f
    private var avanceY = 15f

    private var ancho = 0
    private var alto = 0

    private val radio = 35f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        ancho = canvas.width
        alto = canvas.height

        if(cordX == null && cordY == null){
            cordX = ancho / 2f
            cordY = alto / 2f
        }

        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.TRANSPARENT
        canvas.drawPaint(paint)
        paint.color = Color.WHITE
        canvas.drawCircle(cordX!!,cordY!!,radio,paint)
    }

    private fun moverPelota(){
        if(cordX != null){
            cordX = cordX!! + avanceX

            if( cordX!! - radio < 0 || cordX!! + radio > ancho){
                avanceX *= -1
                cordX = cordX!! + avanceX
            }
        }

        if( cordY != null){
            cordY = cordY!! + avanceY

            if( cordY!! - radio < 0 || cordY!! + radio > alto){
                if(cordY!! < alto/2f){
                    if(cordX!! * 100 > (posicionControlL-10) * ancho  && cordX!! * 100 < (posicionControlL+10) * ancho){
                        avanceY *= -1
                        cordY = cordY!! + avanceY

                        if ( avanceY > 0){
                            avanceX += 1f
                            avanceY += 1f
                        }else{
                            avanceX -= 1f
                            avanceY -= 1f
                        }
                    }
                    else{
                        stop()
                        avanceX = 15f
                        avanceY = 15f

                        cordX = ancho / 2f
                        cordY = alto / 2f
                    }
                }
                else{
                    if(cordX!! * 100 > (posicionControlR-10) * ancho  && cordX!! * 100 < (posicionControlR+10) * ancho){
                        avanceY *= -1
                        cordY = cordY!! + avanceY
                        if ( avanceY > 0){
                            avanceX += 2
                            avanceY += 2
                        }else{
                            avanceX -= 2
                            avanceY -= 2
                        }
                    }
                    else{
                        stop()
                        avanceX = 15f
                        avanceY = 15f

                        cordX = ancho / 2f
                        cordY = alto / 2f
                    }
                }

            }
        }
        invalidate()
    }

    fun start(){
        handler.post(runnable)
    }

    fun stop(){
        handler.removeCallbacks(runnable)
    }
}