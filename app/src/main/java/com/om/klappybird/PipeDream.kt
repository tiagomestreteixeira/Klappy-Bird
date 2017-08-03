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

  val pipeTopMargin = 0
  val pipeLeftMargin = 500
  var pipeWidthPadding = 0
  var pipeDistanceXpadding = 0
  val pipeWidth = 520
  var pipeHeight = 300

  val birdLeftMargin = 50
  val birdHeight = 50
  val birdWidth = 100

  var velocityY = 0
  var gravity = 1

  /**
   * Equivalent of a property initializer for all properties that are mentioned within
   */
  init {
    setWillNotDraw(false)

    painter = Paint(Paint.ANTI_ALIAS_FLAG)
    painter.strokeWidth = strokeWidth
    painter.style = Paint.Style.FILL

    pipes = ArrayList<Rect>()

    this.screenHeight = screenHeight

    bird = Rect(birdLeftMargin, screenHeight / 3, birdWidth,
        (screenHeight / 3) + birdHeight)

    for (i in 0..300) {
      pipeHeight = Random().nextInt(400 - 100) + 100

      if (i % 2 == 0) {
        pipes.add(
            Rect(pipeLeftMargin + pipeDistanceXpadding, pipeTopMargin, pipeWidth + pipeWidthPadding,
                pipeHeight))
      } else {
        pipes.add(
            Rect(pipeLeftMargin + pipeDistanceXpadding, screenHeight - pipeHeight,
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

    painter.color = Color.GREEN
    canvas?.drawRect(bird, painter)

    painter.color = Color.RED
    pipes.forEach {
      canvas?.drawRect(it, painter)

      if (bird.intersect(it)) {
        canvas?.drawColor(Color.WHITE, PorterDuff.Mode.MULTIPLY)
        (context as MainActivity).stopGameLoop()
      }

      if (bird.left == it.left) {
        (context as MainActivity).incrementScore()
      }
    }
  }

  fun loop() {
    render()
  }

  fun render() {
    velocityY += gravity
    bird.top += velocityY

    if ((bird.top + birdHeight) > screenHeight) {
      updateBird(screenHeight - birdHeight)
    } else {
      updateBird(bird.top)
    }

    pipes.forEach {
      it.right -= 5
      it.left -= 5
    }

    invalidate()
  }

  fun updateBird(y: Int) {
    bird.top = y
    bird.bottom = bird.top + birdHeight
  }

  fun startJump() {
    velocityY = -15
  }
}