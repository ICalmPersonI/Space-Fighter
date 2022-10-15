package com.calmperson.spacefighter.`object`

import kotlin.random.Random

class Star(screenX: Int, screenY: Int) : GameObject(
    null,
    null,
    (0..10).random(),
    (0..screenX).random(),
    (0..screenY).random(),
    screenX,
    screenY
) {

    override fun update(playerSpeed: Int) {
        x -= playerSpeed
        x -= speed

        if (x < 0) {
            x = maxX
            y = (0..maxY).random()
            speed = (0..15).random()
        }
    }

    fun getStarWidth(): Float {
        val minX = 1.0f
        val maxX = 4.0f
        return Random.nextFloat() * (maxX - minX) + minX
    }
}