package com.sential.discordbubbles

import android.graphics.*
import android.graphics.Bitmap
import android.graphics.PorterDuffXfermode
import android.graphics.BlurMaskFilter
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth

fun Bitmap.addBackground(): Bitmap {
    val newBitmap = Bitmap.createBitmap(width, height, config)
    val canvas = Canvas(newBitmap)
    canvas.drawColor(Color.BLACK)
    val rect = Rect(0, 0, width, height)
    canvas.drawBitmap(this, rect, rect, null)
    return newBitmap
}

fun Bitmap.makeCircular(): Bitmap {
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val paint = Paint()
    val rect = Rect(0, 0, width, height)

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)

    paint.color = Color.WHITE
    paint.style = Paint.Style.FILL
    canvas.drawCircle(output.width.toFloat() / 2, output.height.toFloat() / 2, output.width.toFloat() / 2, paint)

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(this, rect, rect, paint)

    return output
}

fun Bitmap.addShadow(): Bitmap {
    val bmOut = Bitmap.createBitmap(width + 10, height + 20, Bitmap.Config.ARGB_8888)

    val centerX = (bmOut.width / 2 - width / 2).toFloat()
    val centerY = (bmOut.height / 2 - height / 2).toFloat()

    val canvas = Canvas(bmOut)
    canvas.drawColor(0, PorterDuff.Mode.CLEAR)
    val ptBlur = Paint()
    ptBlur.maskFilter = BlurMaskFilter(6f, BlurMaskFilter.Blur.NORMAL)
    val offsetXY = IntArray(2)
    val bmAlpha = extractAlpha(ptBlur, offsetXY)
    val ptAlphaColor = Paint()
    ptAlphaColor.color = Color.argb(80, 0, 0, 0)
    canvas.drawBitmap(bmAlpha, centerX + offsetXY[0], centerY  + offsetXY[1] + 4f, ptAlphaColor)
    bmAlpha.recycle()
    canvas.drawBitmap(this, centerX, centerY,null)
    return bmOut
}

fun Bitmap.scaleToSize(size: Int): Bitmap {
    return Bitmap.createScaledBitmap(this, size, size, true);
}