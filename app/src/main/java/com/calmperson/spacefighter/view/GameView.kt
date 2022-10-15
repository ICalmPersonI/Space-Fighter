package com.calmperson.spacefighter.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.calmperson.spacefighter.`object`.*
import java.lang.Thread.sleep

class GameView(context: Context, screenX: Int, screenY: Int) : SurfaceView(context), Runnable {

    @Volatile
    var playing = false

    private lateinit var canvas: Canvas
    private lateinit var gameThread: Thread

    private var paint = Paint()
    private var surfaceHolder: SurfaceHolder = holder

    private val ENEMY_COUNT = 1
    private val FRIEND_COUNT = 1
    private val STARS_NUMBER = 100

    private var player: Player = Player(context, screenX, screenY)
    private val boom: Boom = Boom(context)
    private val stars: Array<Star> = Array(STARS_NUMBER) { Star(screenX, screenY) }
    private val enemies: Array<Enemy> = Array(ENEMY_COUNT) { Enemy(context, screenX, screenY) }
    private val friends: Array<Friend> = Array(FRIEND_COUNT) { Friend(context, screenX, screenY) }

    private var isGameOver = false
    private var score = 0
    private var countMisses = 0
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE)
    private val highScore: Array<Int> = Array(4) { i -> sharedPreferences.getInt("score${i + 1}", 0) }

    override fun run() {
        while(playing) {
            update()
            draw()
            control()
        }
    }

    private fun update() {

        boom.x = -250
        boom.y = -250

        player.update()

        stars.forEach {
            it.update(player.speed)
        }

        enemies.forEach {
            if (it.isMissed) {
                it.isMissed = false
                countMisses++
            }
            it.update(player.speed)
            if (Rect.intersects(player.detectCollision!!, it.detectCollision!!)) {
                it.isDestroyed = true
                boom.x = it.x
                boom.y = it.y
                it.x = -200
                score++
            }
        }

        friends.forEach {
            it.update(player.speed)
            if (Rect.intersects(player.detectCollision!!, it.detectCollision!!)) {
                isGameOver = true
                boom.x = it.x
                boom.y = it.y
                it.x = -200
            }
        }

        if (countMisses == 3) {
            isGameOver = true
        }
    }

    private fun draw() {
        if (surfaceHolder.surface.isValid) {

            canvas = surfaceHolder.lockCanvas()
            canvas.drawColor(Color.BLACK)

            paint.color = Color.WHITE

            stars.forEach {
                paint.strokeWidth = it.getStarWidth()
                canvas.drawPoint(it.x.toFloat(), it.y.toFloat(), paint)
            }
            enemies.forEach {
                canvas.drawBitmap(it.bitmap!!, it.x.toFloat(), it.y.toFloat(), paint)
            }
            friends.forEach {
                canvas.drawBitmap(it.bitmap!!, it.x.toFloat(), it.y.toFloat(), paint)
            }

            canvas.drawBitmap(boom.bitmap!!, boom.x.toFloat(), boom.y.toFloat(), paint)
            canvas.drawBitmap(player.bitmap!!, player.x.toFloat(), player.y.toFloat(), paint)

            paint.textSize = 30f
            canvas.drawText("Score: $score", 100f, 50f, paint)

            if (isGameOver) {
                paint.textSize = 150f
                paint.textAlign = Paint.Align.CENTER

                val yPos: Float = (canvas.height / 2) - ((paint.descent() + paint.ascent()) / 2)
                canvas.drawText("Game Over", (canvas.width / 2).toFloat(), yPos, paint)
                playing = false

                for (i in highScore.indices) {
                    if (highScore[i] < score) {
                        highScore[i] = score
                        break
                    }
                }

                sharedPreferences.edit().apply {
                    highScore.forEachIndexed { i, s ->
                        putInt("score${i + 1}", s)
                    }
                    apply()
                }
            }

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    private fun control() {
        sleep(17L)
    }

    fun pause() {
        playing = false
        try {
            gameThread.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun resume() {
        playing = true
        gameThread = Thread(this).also { it.start() }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> { player.stopBoosting() }
            MotionEvent.ACTION_DOWN -> { player.setBoosting() }
        }
        return true
    }
}