package com.calmperson.spacefighter.`object`

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Rect
import com.calmperson.spacefighter.R

class Player(context: Context, screenX: Int, screenY: Int) : GameObject(
    null,
    BitmapFactory.decodeResource(context.resources, R.drawable.player),
    1,
    75,
    50,
    0,
    0
) {

    init {
        super.maxY = screenY - super.bitmap!!.height
        super.detectCollision = Rect(x, y, bitmap!!.width, bitmap!!.height)
    }

    var boosting: Boolean = false

    private val GRAVITY: Int = -10
    private val MIN_SPEED = 1
    private val MAX_SPEED = 20

    fun setBoosting() {
        boosting = true
    }

    fun stopBoosting() {
        boosting = false
    }

    override fun update() {
        if (boosting) speed += 2
        else speed -= 5

        if (speed > MAX_SPEED) speed = MAX_SPEED
        if (speed < MIN_SPEED) speed = MIN_SPEED

        y -= speed + GRAVITY

        if (y < minY) y = minY
        if (y > maxY) y = maxY

        detectCollision!!.left = x
        detectCollision!!.top = y
        detectCollision!!.right = x + bitmap!!.width
        detectCollision!!.bottom = y + bitmap!!.height
    }

}