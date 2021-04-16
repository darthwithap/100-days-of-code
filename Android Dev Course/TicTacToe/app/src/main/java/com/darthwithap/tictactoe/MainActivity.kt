package com.darthwithap.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var activePlayer = 1
    private var count = 0
    private var status = IntArray(9) { 0 }
    private var gameActive = true
    private var row = IntArray(3) { 0 }
    private var col = IntArray(3) { 0 }
    private var diagonalRight = 0
    private var diagonalLeft = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
        btn0.setOnClickListener(this)
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
        btn5.setOnClickListener(this)
        btn6.setOnClickListener(this)
        btn7.setOnClickListener(this)
        btn8.setOnClickListener(this)
        btnReset.setOnClickListener {
            initialize()
        }
    }

    private fun initialize() {
        diagonalLeft = 0
        diagonalRight = 0
        count = 0
        status = IntArray(9) { 0 }
    }

    private fun tileClick(view: View?) {
        val counter = view as Button
        val tag = counter.tag.toString().toInt()

        if (status[tag] == 0 && gameActive) {
            count++
            status[tag] = activePlayer

            row[tag / 3] += activePlayer
            col[tag % 3] += activePlayer
            updateDiagonals(tag)

            activePlayer = -activePlayer

            if (checkResult(tag/3, tag%3) || count == 9) {
                gameActive = false
                Toast.makeText(this, "Game Over.", Toast.LENGTH_SHORT).show()
                if (count == 9) tvTitle.text = "ITS A DRAW!"
                else {
                    when(activePlayer) {
                        (1) -> tvTitle.text = "PLAYER X WINS!"
                        (-1) -> tvTitle.text = "PLAYER O WINS!"
                    }
                }
            }
            else if (!gameActive) Toast.makeText(this, "Game is over.", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "Invalid Move.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateDiagonals(tag: Int) {
        if (tag % 4 == 0) diagonalLeft += activePlayer
        if (tag % 2 == 0 && 0 < tag && tag < 8) diagonalRight += activePlayer
    }

    private fun checkResult(r: Int, c: Int) =
            row[r] == 3 || row[r] == -3 || col[c] == 3 || col[c] == -3
                    || diagonalLeft == 3 || diagonalLeft == -3 ||
                    diagonalRight == 3 || diagonalRight == -3


    override fun onClick(p0: View?) {
        tileClick(p0)
    }
}