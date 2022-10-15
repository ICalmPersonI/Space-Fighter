package com.calmperson.spacefighter.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.window.layout.WindowMetrics;
import androidx.window.layout.WindowMetricsCalculator
import com.calmperson.spacefighter.view.GameView


class GameActivity : AppCompatActivity() {

    private lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val windowMetrics: WindowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val height: Int = windowMetrics.bounds.height()
        val width: Int = windowMetrics.bounds.width()

        gameView = GameView(this, width, height)
        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

}