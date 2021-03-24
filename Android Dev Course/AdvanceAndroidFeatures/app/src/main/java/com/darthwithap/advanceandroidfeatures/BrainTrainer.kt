package com.darthwithap.advanceandroidfeatures

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import androidx.core.view.get
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView
import kotlin.math.max

class BrainTrainer : AppCompatActivity() {

    var a = 0
    var b = 0
    var ans = 0
    var active = false
    var games = 0
    var wins = 0
    var gameTime = 30
    var correct = 0
    lateinit var timer : CountDownTimer
    lateinit var verdict : TextView
    lateinit var score : TextView
    lateinit var timeRemaining : TextView
    lateinit var question : TextView
    lateinit var gridLayout: GridLayout
    lateinit var playAgainButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brain_trainer)

        gridLayout = findViewById(R.id.grid_layout)
        val goButton = findViewById<TextView>(R.id.text_go)
        val imgDownloadingButton = findViewById<Button>(R.id.btn_img_downloading_app)
        val brainTrainerTextView = findViewById<TextView>(R.id.text_brain_trainer)
        val gameLinearLayout = findViewById<LinearLayout>(R.id.game_linear_layout)
        playAgainButton = findViewById(R.id.btn_play_again)
        timeRemaining = findViewById(R.id.text_time_remaining)
        score = findViewById(R.id.text_score)
        question = findViewById(R.id.text_question)
        verdict = findViewById(R.id.text_verdict)
        val weatherButton = findViewById<Button>(R.id.btn_weather_app)

        weatherButton.setOnClickListener {
            startActivity(Intent(this, Weather::class.java))
        }

        imgDownloadingButton.setOnClickListener {
            startActivity(Intent(this, DownloadingFromWeb::class.java))
        }

        goButton.setOnClickListener {
            goButton.visibility = View.INVISIBLE
            brainTrainerTextView.visibility = View.INVISIBLE
            gameLinearLayout.visibility = View.VISIBLE
            startGame()
        }

        playAgainButton.setOnClickListener {
            playAgain()
        }
    }

     fun answer(view: View) {
        val counter = view as TextView
        val tag = counter.tag.toString().toInt()
        if (tag == correct) {
            verdict.text = "Correct!"
            wins ++
        }
        else {
            verdict.text = "Wrong :("
        }
        games ++

        score.text = "$wins/$games"
        generateQuestion()
    }

    private fun startGame() {
        generateQuestion()
        timer = object : CountDownTimer((1000 * gameTime).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining.text = "${(millisUntilFinished)/1000} s"
            }
            override fun onFinish() {
                stopGame()
                verdict.text = "Game Over!"
                playAgainButton.visibility = View.VISIBLE
            }
        }.start()
    }

    private fun generateQuestion() {
        a = (1..50).random()
        b = (1..50).random()
        question.text = "$a + $b"
        ans = a + b
        correct = (0..3).random()

        for (i in (0 until gridLayout.childCount)) {
            val option = this.gridLayout[i] as TextView
            if (i == correct) option.text = "$ans"
            else {
                var wrongAns = (max(a, b)..(ans+10)).random()
                while (wrongAns == ans) wrongAns = (max(a, b)..(ans+10)).random()
                option.text = "$wrongAns"
            }
        }
    }

    private fun stopGame() {
        gridLayout.isClickable = false
        for (i in (0 until gridLayout.childCount)) {
            val t = gridLayout[i] as TextView
            t.setBackgroundColor(Color.GRAY)
            t.isClickable = false
        }
        timer.cancel()
    }

    private fun playAgain() {
        playAgainButton.visibility = View.INVISIBLE
        wins = 0
        games = 0
        correct = 0
        verdict.text = "Verdict"
        score.text = "0/0"
        for (i in (0 until gridLayout.childCount)) {
            val t = gridLayout[i] as TextView
            t.isClickable = true
            when (t.tag.toString().toInt()) {
                (0) -> t.setBackgroundColor(Color.parseColor("#FF4081"))
                (1) -> t.setBackgroundColor(Color.parseColor("#B2FF59"))
                (2) -> t.setBackgroundColor(Color.parseColor("#18FFFF"))
                (3) -> t.setBackgroundColor(Color.parseColor("#FFFF00"))
            }
        }
        startGame()
    }
}