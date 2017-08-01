package com.om.klappybird

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import timber.log.Timber
import java.util.*

class PipeDream(context: Context) : View(context) {
  val painter: Paint
  val strokeWidth = 10f
  val paintColor = Color.RED

  val pipes: MutableList<Rect>

  val bird: Rect

  val distanceX = 300
  val distanceY = 0
  val pipeWidth = 320

  var pipeHeight = 300
  var pipeWidthPadding = 0
  var pipeDistanceXpadding = 0

  val screenDimensions: DisplayMetrics

  val birdRelativePosition = 50
  val birdDistanceX = 50
  val birdWidth = 100

  var positionX = 100.0
  var positionY = 175.0
  var velocityX = 4.0
  var velocityY = 0.0
  var gravity = 0.5
  var onGround = false

  /**
   * Equivalent of a property initializer for all properties that are mentioned within
   */
  init {
    setWillNotDraw(false)

    painter = Paint(Paint.ANTI_ALIAS_FLAG)
    painter.color = paintColor
    painter.strokeWidth = strokeWidth
    painter.style = Paint.Style.FILL

    screenDimensions = getScreenDimens()

    pipes = ArrayList<Rect>()

    bird = Rect(birdDistanceX, (screenDimensions.heightPixels / 3), birdWidth,
        (screenDimensions.heightPixels / 3) + birdRelativePosition)

    for (i in 0..100) {
      pipeHeight = Random().nextInt(500 - 300) + 300

      if (i % 2 == 0) {
        pipes.add(Rect(distanceX + pipeDistanceXpadding, distanceY, pipeWidth + pipeWidthPadding,
            pipeHeight))
      } else {
        pipes.add(
            Rect(distanceX + pipeDistanceXpadding, screenDimensions.heightPixels - pipeHeight,
                pipeWidth + pipeWidthPadding, screenDimensions.heightPixels))
      }

      pipeDistanceXpadding += 100
      pipeWidthPadding += 100
    }
  }

  override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

    canvas?.drawRect(bird, painter)

    pipes.forEach {
      canvas?.drawRect(it, painter)
    }
  }

  fun getScreenDimens(): DisplayMetrics {
    val wm = context.getSystemService(WINDOW_SERVICE) as WindowManager
    val displayMetrics = DisplayMetrics()
    wm.defaultDisplay.getMetrics(displayMetrics)

    return displayMetrics
  }

  fun loop() {
    updateBird()
//    render()
    Timber.d("We're looping")
  }

  fun render() {
    bird.bottom += 10
    bird.top += 10
    invalidate()
    Timber.d("Rendering")
  }

  fun updateBird() {
    Timber.d("Updating")
    velocityY += gravity
    positionY += velocityY
    positionX += velocityX

    if (positionY > 175.0) {
      positionY = 175.0
      velocityY = 0.0
      onGround = true
    }

    if (positionX < 10 || positionX > 190)
      velocityX *= -1
  }

  fun startJump() {
    if (onGround) {
      velocityY = -12.0
      onGround = false
    }
  }

  fun endJump() {
    if (velocityY < -6.0) {
      velocityY = -6.0
    }
  }
}