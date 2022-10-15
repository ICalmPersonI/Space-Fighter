package com.calmperson.spacefighter.`object`

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.calmperson.spacefighter.R

class Friend(context: Context, screenX: Int, screenY: Int) : GameObject(
    null,
    BitmapFactory.decodeResource(context.resources, R.drawable.friend),
    (0..6).random() + 10,
    screenX,
    0,
    screenX,
    screenY
) {

    init {
        super.y = (0..maxY).random() + bitmap!!.height
        super.detectCollision = Rect(x, y, bitmap!!.width, bitmap!!.height)
    }

    override fun update(playerSpeed: Int) {
        x -= playerSpeed
        x -= speed

        if (x < minX - bitmap!!.width) {
            speed = (0..10).random() + 10
            x = maxX
            y = (0..maxY).random() - bitmap!!.height
        }

        detectCollision!!.left = x
        detectCollision!!.top = y
        detectCollision!!.right = x + bitmap!!.width
        detectCollision!!.bottom = y + bitmap!!.height
    }

}