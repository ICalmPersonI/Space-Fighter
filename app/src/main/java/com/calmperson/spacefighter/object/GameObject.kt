package com.calmperson.spacefighter.`object`

import android.graphics.Bitmap
import android.graphics.Rect

abstract class GameObject(
    var detectCollision: Rect?,
    var bitmap: Bitmap?,
    var speed: Int,
    var x: Int,
    var y: Int,
    var maxX: Int,
    var maxY: Int
) {

    val minX: Int = 0
    val minY: Int = 0

    open fun update() {}

    open fun update(playerSpeed: Int) {}
}