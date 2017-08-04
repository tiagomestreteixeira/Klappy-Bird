package com.om.klappybird

import android.content.Context
import android.graphics.*
import android.view.View
import timber.log.Timber
import java.util.*

class PipeDream(context: Context, screenHeight: Int) : View(context) {

  val painter: Paint
  val strokeWidth = 10f

  var pipes: MutableList<Rect>
  var remainingPipes: MutableList<Rect>

  val drawingTimer: Timer

  val screenHeight: Int

  var createBottomPipe = false

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

    drawingTimer = Timer()
    pipes = ArrayList()
    remainingPipes = ArrayList()

    this.screenHeight = screenHeight

    bird = Rect(birdLeftMargin, screenHeight / 3, birdWidth,
        (screenHeight / 3) + birdHeight)

    for (i in 0..10) {
      addPipe()
    }
  }

  override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

    painter.color = Color.GREEN
    canvas?.drawRect(bird, painter)

    painter.color = Color.RED
    remainingPipes.forEach {
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

    val iter = pipes.iterator()

    for (pipe in iter) {
      pipe.right -= 5
      pipe.left -= 5

      if (pipe.left < 0) {
        iter.remove()
        addPipeToRemaining()
      }
    }

    remainingPipes.addAll(pipes)

    Timber.d("Pipes array size ${pipes.size}")

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

    pipeDistanceXpadding += 100
    pipeWidthPadding += 100
  }

  fun addPipeToRemaining() {
    if (createBottomPipe)
      remainingPipes.add(createBottomPipe(randomPipeHeight()))
    else
      remainingPipes.add(createTopPipe(randomPipeHeight()))

    createBottomPipe = !createBottomPipe

    pipeDistanceXpadding += 100
    pipeWidthPadding += 100
  }

  fun createTopPipe(pipeHeight: Int) = Rect(pipeLeftMargin + pipeDistanceXpadding, pipeTopMargin,
      pipeWidth + pipeWidthPadding,
      pipeHeight)

  fun createBottomPipe(pipeHeight: Int) = Rect(pipeLeftMargin + pipeDistanceXpadding,
      screenHeight - pipeHeight,
      pipeWidth + pipeWidthPadding, screenHeight)

  fun randomPipeHeight() = Random().nextInt(200 - 100) + 100

  fun startJump() {
    velocityY = -15
  }
}