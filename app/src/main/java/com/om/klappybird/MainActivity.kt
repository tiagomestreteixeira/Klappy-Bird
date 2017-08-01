package com.om.klappybird

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

  lateinit var pipeDream: PipeDream

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    pipeDream = PipeDream(this)

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

    Handler().postDelayed(
        { pipeDream.loop() }, 300)
  }
}
