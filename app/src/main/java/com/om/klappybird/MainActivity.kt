package com.om.klappybird

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

  private var score: Int = 0
  private var highScore: Int = 0

  private val highScorePrefsKey = "HIGH_SCORE"

  lateinit var pipeDream: PipeDream
  private var gameStarted = false
  private var timer = Timer()

  private lateinit var prefs: SharedPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    prefs = PreferenceManager.getDefaultSharedPreferences(this)

    highScore = prefs.getInt(highScorePrefsKey, 0)
    highScoreTV.text = highScore.toString()

    mainContentView.post({
      pipeDream = PipeDream(this, mainContentView)

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

  private fun startGameLoop() {
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
    mainContentView.isEnabled = false

    AlertDialog.Builder(this)
        .setMessage("Welp! You lost. Restart?")
        .setPositiveButton("Yes",
            { dialog, which ->

              timer = Timer()
              pipeDream = PipeDream(this, mainContentView)
              mainContentView.removeAllViews()
              mainContentView.addView(pipeDream)
              scoreTV.text = "0"

              mainContentView.isEnabled = true

            }).setNegativeButton("No",
        { dialogInterface, i -> finish() }).setCancelable(false).show()
  }

  fun incrementScore() {
    score = Integer.parseInt(scoreTV.text.toString()) + 1

    scoreTV.text = score.toString()

    if (score > highScore) {
      highScore = score
      prefs.edit().putInt(highScorePrefsKey, highScore).apply()

      highScoreTV.text = Integer.parseInt(scoreTV.text.toString()).toString()
    }
  }
}
