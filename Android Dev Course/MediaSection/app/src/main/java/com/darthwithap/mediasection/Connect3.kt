package com.darthwithap.mediasection

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.get

class Connect3 : AppCompatActivity() {
    //-1: yellow, 1: red
    private var activePlayer = 1
    private var count = 0
    private var status = IntArray(9) {0}
    private var gameActive = true
    private var row = IntArray(3) {0}
    private var col = IntArray(3) {0}
    private var diagonalLeft = 0
    private var diagonalRight = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect_3)

        val playAgainButton = findViewById<Button>(R.id.play_again_button)
        val videoButton = findViewById<Button>(R.id.video_button)

        videoButton.setOnClickListener {
            initialize()
            startActivity(Intent(this, Video::class.java))
        }

        initialize()
        playAgainButton.setOnClickListener {
            initialize()
        }

    }

    private fun initialize() {
        diagonalRight = 0
        diagonalLeft = 0
        count = 0
        status = IntArray(9) {0}
        row = IntArray(3) {0}
        col = IntArray(3) {0}
        activePlayer = 1
        gameActive = true
        val grid = findViewById<GridLayout>(R.id.grid_layout)
        val winnerTitle = findViewById<TextView>(R.id.winner_title)
        winnerTitle.visibility = View.INVISIBLE
        val videoView = findViewById<TextView>(R.id.video_button)
        videoView.visibility = View.INVISIBLE

        for (i in (0 until grid.childCount)) {
            val counter = grid[i] as ImageView
            counter.setImageDrawable(null)
        }
    }

    fun dropCoin(view: View) {
        val counter = view as ImageView
        val tag = counter.tag.toString().toInt()

        if (status[tag] == 0 && gameActive) {
            count++
            status[tag] = activePlayer

            row[tag/3] += activePlayer
            col[tag%3] += activePlayer
            updateDiagonals(tag)

            counter.translationY = -1500F
            activePlayer = if (activePlayer == 1) {
                counter.setImageResource(R.drawable.yellow_coin)
                -1
            } else {
                counter.setImageResource(R.drawable.red_coin)
                1
            }
            counter.animate().translationYBy(1500F).duration = 400

            if (checkResult(tag/3,tag%3) || count == 9) {
                gameActive = false
                val winnerTitle = findViewById<TextView>(R.id.winner_title)
                Toast.makeText(this, "Game Over.", Toast.LENGTH_SHORT).show()
                if (count == 9) winnerTitle.text = "ITS A DRAW!"
                else {
                    if (activePlayer == 1) winnerTitle.text = "RED WINS!"
                    else if (activePlayer == -1) winnerTitle.text= "YELLOW WINS!"
                }
                val videoButton = findViewById<Button>(R.id.video_button)
                winnerTitle.visibility = View.VISIBLE
                videoButton.visibility = View.VISIBLE

            }
        }
        else if (!gameActive) Toast.makeText(this, "Game is over.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this, "Invalid Move!", Toast.LENGTH_SHORT).show()

    }

    private fun checkResult(r: Int, c: Int) =
        row[r] == 3 || row[r] == -3 || col[c] == 3 || col[c] == -3 || (diagonalLeft == 3) || (diagonalLeft == -3) || (diagonalRight == 3) || (diagonalRight == -3)

    private fun updateDiagonals(tag: Int) {
        if (tag%4 == 0) diagonalLeft += activePlayer
        if (tag%2 == 0 && 0<tag && tag<8) diagonalRight += activePlayer
    }

//    fun showAToast(message: String?) {
//        if (toast !=null )
//        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
//        toast.show()
//    }

}