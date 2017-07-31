package com.om.klappybird

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.ViewGroup


class PipeView(context: Context) : ViewGroup(context) {
  var painter: Paint
  val xSide = 100f
  val ySide = 10f
  val sideLength = 300f

  init {
    setWillNotDraw(false)

    painter = Paint(Paint.ANTI_ALIAS_FLAG)
    painter.color = Color.RED
    painter.strokeWidth = 10f
    painter.style = Paint.Style.FILL
  }

  override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
  }


  override fun onDraw(canvas: Canvas?) {
    super.onDraw(canvas)
    canvas?.drawRect(0f, 0f, 50f, 600f, painter)
  }
}