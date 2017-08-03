package com.om.klappybird

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

  lateinit var pipeDream: PipeDream
  var gameStarted = false
  var timer = Timer()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    mainContentView.post({
      pipeDream = PipeDream(this, mainContentView.height)

      mainContentView.addView(pipeDream)

      mainContentView.setOnTouchListener { view, motionEvent ->
        val action = motionEvent.action

        if (action == MotionEvent.ACTION_DOWN) {
          if (!gameStarted) {
            gameStarted = true
            startGameLoop()
          } else {
            pipeDream.startJump()
          }
        }

        true
      }
    })
  }

  override fun onDestroy() {
    super.onDestroy()
    timer.cancel()
  }

  fun startGameLoop() {
    val doAsynchronousTask = object : TimerTask() {
      override fun run() {
        runOnUiThread {
          pipeDream.loop()
        }
      }
    }

    timer.schedule(doAsynchronousTask, 0, 20)
  }

  fun stopGameLoop() {
    timer.cancel()
    gameStarted = false
    mainContentView.isClickable = false

    AlertDialog.Builder(this)
        .setMessage("Welp! You lost. Restart?")
        .setPositiveButton("Yes",
            { dialog, which ->
              timer = Timer()
              pipeDream = PipeDream(this, mainContentView.height)
              mainContentView.removeAllViews()
              mainContentView.addView(pipeDream)
              mainContentView.isClickable = false
            }).setNegativeButton("No",
        { dialogInterface, i -> finish() }).setCancelable(false).show()
  }
}
