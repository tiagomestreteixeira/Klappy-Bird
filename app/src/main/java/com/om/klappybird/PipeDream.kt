package com.om.klappybird

import android.app.Activity
import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.ViewGroup
import android.view.WindowManager
import java.util.concurrent.ThreadLocalRandom
import kotlin.properties.Delegates

data class Pipe(val distanceX: Float, val distanceY: Float, val width: Float, val height: Float)

//data class Bird(val distanceX: Float, val distanceY: Float, val width: Float, val height: Float)

class ScreenDimens(val width: Int, val height: Int) {
  operator fun component1() = width
  operator fun component2() = height
}

class PipeDream(context: Context) : ViewGroup(context) {
  var painter: Paint by Delegates.notNull()
  val strokeWidth = 10f
  val paintColor = Color.RED

  var pipes: MutableList<Pipe> by Delegates.notNull()

  var bird: Pipe by Delegates.notNull()

  var distanceX = 20f
  var distanceY = 0f
  var pipeWidth = 50f
  var pipeHeight = 300f
  var pipeWidthPadding = 0
  var pipeDistanceXpadding = 0

  var screenWidth = 0
  var screenHeight = 0

  var birdRelativePosition = 50f
  var birdDistanceX = 50f
  var birdWidth = 100f

  init {
    setWillNotDraw(false)

    painter = Paint(Paint.ANTI_ALIAS_FLAG)
    painter.color = paintColor
    painter.strokeWidth = strokeWidth
    painter.style = Paint.Style.FILL

    screenWidth = getScreenDimens().component1()
    screenWidth = getScreenDimens().component2()

    pipes = ArrayList<Pipe>()

    bird = Pipe(birdDistanceX, (screenHeight / 3).toFloat(), birdWidth,
        (screenHeight / 3).toFloat() + birdRelativePosition)

    for (i in 0..100) {
      pipeHeight = ThreadLocalRandom.current().nextInt(300, 500 + 1).toFloat()

      if (i % 2 == 0) {
        pipes.add(Pipe(distanceX + pipeDistanceXpadding, distanceY, pipeWidth + pipeWidthPadding,
            pipeHeight))
      } else {
        pipes.add(
            Pipe(distanceX + pipeDistanceXpadding, screenHeight.toFloat() - pipeHeight,
                pipeWidth + pipeWidthPadding, screenHeight.toFloat()))
      }

      pipeDistanceXpadding += 100
      pipeWidthPadding += 100
    }
  }

  override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

    canvas?.drawRect(bird.distanceX, bird.distanceY, bird.width, bird.height, painter)

    pipes.forEach {
      canvas?.drawRect(it.distanceX, it.distanceY, it.width,
          it.height, painter)
    }
  }

  fun getScreenDimens(): ScreenDimens {
    val wm = (context as Activity).getSystemService(WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(displayMetrics)

    return ScreenDimens(displayMetrics.heightPixels, displayMetrics.widthPixels)
  }
}