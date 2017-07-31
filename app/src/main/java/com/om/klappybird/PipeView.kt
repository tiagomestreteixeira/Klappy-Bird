package com.om.klappybird

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.ViewGroup
import kotlin.properties.Delegates

class PipeView(context: Context) : ViewGroup(context) {
  var painter: Paint by Delegates.notNull()
  val strokeWidth = 10f
  val paintColor = Color.RED

  var width = 0.0f
  var height = 0.0f
  var distanceX = 0.0f
  var distanceY = 0.0f

  constructor(context: Context,distanceX: Float, distanceY: Float, width: Float,
      height: Float) : this(context) {
    setWillNotDraw(false)

    painter = Paint(Paint.ANTI_ALIAS_FLAG)
    painter.color = paintColor
    painter.strokeWidth = strokeWidth
    painter.style = Paint.Style.FILL

    this.distanceX = distanceX
    this.distanceY = distanceY
    this.width = width
    this.height = height
  }

  override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
  }

  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas?.drawRect(distanceX, distanceY, width, height, painter)
  }
}