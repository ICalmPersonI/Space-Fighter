package com.calmperson.spacefighter.`object`

import android.content.Context
import android.graphics.BitmapFactory
import com.calmperson.spacefighter.R

class Boom(context: Context) : GameObject(
    null,
    BitmapFactory.decodeResource(context.resources, R.drawable.boom),
    0,
    -250,
    -250,
    0,
    0
)
