package com.vannhat.recyclerslidetabdemo.utils

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView

class SeeThroughTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    private var paint: Paint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }
    private var maskBitmap: Bitmap? = null
    private var maskCanvas: Canvas? = null
    private var bgDrawable: Drawable? = null
    private var bgBitmap: Bitmap? = null
    private var bgCanvas: Canvas? = null

    init {
        super.setTextColor(Color.BLACK)
        super.setBackground(ColorDrawable(Color.TRANSPARENT))
    }

    override fun setBackground(background: Drawable?) {
        if (bgDrawable == background) return

        bgDrawable = background
        val w = width
        val h = height

        if (bgDrawable != null && w != 0 && h != 0) {
            bgDrawable!!.setBounds(0, 0, w, h)
        }
        requestLayout()
        invalidate()
    }

    override fun setBackgroundColor(color: Int) {
        background = ColorDrawable(color)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (w == 0 && h == 0) {
            clearBitmap()
            return
        }

        initBitmap(w, h)
        bgDrawable?.setBounds(0, 0, w, h)

    }

    private fun initBitmap(w: Int, h: Int) {
        bgBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bgCanvas = Canvas(bgBitmap!!)
        maskBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ALPHA_8)
        maskCanvas = Canvas(maskBitmap!!)
    }

    private fun clearBitmap() {
        maskBitmap = null
        bgBitmap = null
        bgCanvas = null
        maskCanvas = null
    }

    override fun onDraw(canvas: Canvas?) {
        if (isNothingToDraw()) return
        drawMask()
        drawBackground()
        bgBitmap?.let { canvas?.drawBitmap(it, 0f, 0f, null) }
    }

    private fun drawBackground() {
        clear(bgCanvas)
        bgCanvas?.let { bgDrawable?.draw(it) }
        maskBitmap?.let { bgCanvas?.drawBitmap(it, 0f, 0f, paint) }
    }

    private fun isNothingToDraw(): Boolean {
        return bgDrawable == null || width == 0 || height == 0
    }

    private fun drawMask() {
        clear(maskCanvas)
        super.onDraw(maskCanvas)
    }

    private fun clear(canvas: Canvas?) {
        canvas?.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR)
    }
}
