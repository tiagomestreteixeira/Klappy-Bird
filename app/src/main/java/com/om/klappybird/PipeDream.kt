package com.om.klappybird

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.widget.RelativeLayout
import timber.log.Timber
import java.security.SecureRandom
import java.util.*

class PipeDream(context: Context, mainContentView: RelativeLayout) : View(context) {

  val painter: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    strokeWidth = 10f
    style = Paint.Style.FILL
  }

  var pipes: MutableList<Rect> = ArrayList()

  val screenHeight: Int = mainContentView.height

  var createBottomPipe = false
  var lastTime: Long = 0
  var elapsedTime: Long = 0

  val bird: Rect

  val pipeTopMargin = 0
  val pipeLeftMargin = 500
  var pipeWidthPadding = 0
  var pipeDistanceXpadding = 0
  val pipeWidth = 560

  val birdLeftMargin = 50
  val birdHeight = 50
  val birdWidth = 100

  var velocityY = 0
  var gravity = 1

  init {
    bird = Rect(birdLeftMargin, screenHeight / 3, birdWidth,
        (screenHeight / 3) + birdHeight)
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

    val iter = pipes.iterator()

    for (pipe in iter) {
      pipe.right -= 5
      pipe.left -= 5

      //Slammed into pipe, hit the ground or the ceiling
      if (bird.intersect(
          pipe) or ((bird.top + birdHeight) == screenHeight) or (bird.bottom < 0)) {
        (context as MainActivity).stopGameLoop()
        break
      }

      //Flew over pipe successfully
      if (bird.left == pipe.left) {
        (context as MainActivity).incrementScore()
      }

      if (pipe.left < -60) {
        iter.remove()
        Timber.d("Pipe removed")
      }
    }

    val now = System.currentTimeMillis()
    elapsedTime += now.minus(lastTime)
    lastTime = now

    if (elapsedTime > 300) {
      addPipe()
      elapsedTime = 0
    }

    invalidate()
  }

  fun updateBird(y: Int) {
    bird.top = y
    bird.bottom = bird.top + birdHeight
  }

  fun addPipe() {
    if (createBottomPipe)
      pipes.add(createBottomPipe(randomPipeHeight()))
    else
      pipes.add(createTopPipe(randomPipeHeight()))

    createBottomPipe = !createBottomPipe

    pipeDistanceXpadding += 150
    pipeWidthPadding += 150
  }

  fun createTopPipe(pipeHeight: Int) = Rect(pipeLeftMargin + pipeDistanceXpadding, pipeTopMargin,
      pipeWidth + pipeWidthPadding,
      pipeHeight)

  fun createBottomPipe(pipeHeight: Int) = Rect(pipeLeftMargin + pipeDistanceXpadding,
      screenHeight - pipeHeight,
      pipeWidth + pipeWidthPadding, screenHeight)

  fun randomPipeHeight() = SecureRandom().nextInt(500 - 100) + 100

  fun startJump() {
    velocityY = -15
  }
}