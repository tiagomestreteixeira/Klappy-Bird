package com.om.klappybird

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

  lateinit var pipeDream: PipeDream
  val timer = Timer()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    mainContentView.post({
      pipeDream = PipeDream(this, mainContentView.height)

      mainContentView.addView(pipeDream)

      mainContentView.setOnTouchListener { view, motionEvent ->
        val action = motionEvent.action

        if (action == MotionEvent.ACTION_UP) {
          pipeDream.endJump()
        }
        if (action == MotionEvent.ACTION_DOWN) {
          pipeDream.startJump()
        }

        true
      }

      val doAsynchronousTask = object : TimerTask() {
        override fun run() {
          runOnUiThread { pipeDream.loop() }
        }
      }

      timer.schedule(doAsynchronousTask, 0, 20)
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    timer.cancel()
  }
}
