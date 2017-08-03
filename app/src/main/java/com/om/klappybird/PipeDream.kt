package com.om.klappybird

import android.content.Context
import android.graphics.*
import android.view.View
import java.util.*

class PipeDream(context: Context, screenHeight: Int) : View(context) {

  val painter: Paint
  val strokeWidth = 10f
  val paintColor = Color.RED

  val pipes: MutableList<Rect>

  val screenHeight: Int

  val bird: Rect

  val distanceX = 300
  val distanceY = 0
  val pipeWidth = 320

  var pipeHeight = 300
  var pipeWidthPadding = 0
  var pipeDistanceXpadding = 0

  val birdRelativePosition = 50
  val birdDistanceX = 50
  val birdWidth = 100

  var positionY = 20
  var velocityY = 0
  var gravity = 3
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

    pipes = ArrayList<Rect>()

    this.screenHeight = screenHeight

    bird = Rect(birdDistanceX, screenHeight / 2, birdWidth,
        (screenHeight / 2) + birdRelativePosition)

    for (i in 0..300) {
      pipeHeight = Random().nextInt(400 - 100) + 100

      if (i % 2 == 0) {
        pipes.add(Rect(distanceX + pipeDistanceXpadding, distanceY, pipeWidth + pipeWidthPadding,
            pipeHeight))
      } else {
        pipes.add(
            Rect(distanceX + pipeDistanceXpadding, screenHeight - pipeHeight,
                pipeWidth + pipeWidthPadding, screenHeight))
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

      if (bird.intersect(it)) {
        canvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY)
        (context as MainActivity).stopGameLoop()
      }
    }
  }

  fun loop() {
    updateBird()
    render()
  }

  fun render() {
    if (bird.bottom <= screenHeight) {
      bird.bottom += 7
      bird.top += 7
    }

    if (!onGround) {
      bird.bottom -= positionY
      bird.top -= positionY
    }

    pipes.forEach {
      it.right -= 5
      it.left -= 5
    }

    invalidate()
  }

  fun updateBird() {
    velocityY += gravity
    positionY += velocityY

    if (positionY > 40) {
      positionY = 40
      velocityY = 35
      onGround = true
    }
  }

  fun startJump() {
    if (onGround) {
      velocityY = -6
      onGround = false
    }
  }

  fun endJump() {
    if (velocityY < -3) {
      velocityY = -3
    }
  }
}