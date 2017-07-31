package com.om.klappybird

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  lateinit var pipes: ArrayList<PipeView>

  val distanceX = 100f
  val distanceY = 50f
  val width = 250f
  val height = 600f
  var multiplier = 1.0f

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    for (i in 1..4) {
//      val pipe1 = PipeView(this, distanceX, distanceY, width, height)
      val pipe2 = PipeView(this, distanceX, distanceY, width, height)

      Log.d("Values", "Multiplier: " + distanceX * multiplier)
      multiplier += 20
//      mainContentView.addView(pipe1)
      mainContentView.addView(pipe2)
    }
  }
}
