package com.om.klappybird

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.ViewGroup
import kotlin.properties.Delegates

class PipeDream(context: Context) : ViewGroup(context) {
  var painter: Paint by Delegates.notNull()
  val strokeWidth = 10f
  val paintColor = Color.RED

  var pipes: ArrayList<Pipe> by Delegates.notNull()

  var distanceX = 20
  var distanceY = 50
  var rectWidth = 50
  var rectHeight = 600

  var widthPadding = 0
  var distancePadding = 0

  init {
    setWillNotDraw(false)

    painter = Paint(Paint.ANTI_ALIAS_FLAG)
    painter.color = paintColor
    painter.strokeWidth = strokeWidth
    painter.style = Paint.Style.FILL

    pipes = ArrayList<Pipe>()

    for (i in 0..5) {
      pipes.add(Pipe(distanceX + distancePadding, distanceY, rectWidth + widthPadding, rectHeight))
      distancePadding += 100
      widthPadding += 100
    }
  }

  override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)

    pipes.forEach {
      canvas?.drawRect(it.distanceX.toFloat(), it.distanceY.toFloat(), it.width.toFloat(),
          it.height.toFloat(), painter)
    }
  }
}