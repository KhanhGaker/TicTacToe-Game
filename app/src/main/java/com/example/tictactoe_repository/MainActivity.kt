package com.example.tictactoe_repository

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tictactoe_repository.customView.TicTacToe
import com.example.tictactoe_repository.customView.TicTacToeView

@SuppressLint("CutPasteId")
class MainActivity : AppCompatActivity(), TicTacToe.TicTacToeListener,
    TicTacToeView.SquarePressedListener {
    lateinit var ticTacToe: TicTacToe
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ticTacToe = TicTacToe()
        ticTacToe.setTicTacToeListener(this)
        findViewById<TicTacToeView>(R.id.ticTacToeView).squarePressListener = this

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            ticTacToe.resetGame()
            resetGameUi()
            findViewById<Button>(R.id.resetButton).visibility = View.GONE
        }

    }

    override fun onSquarePressed(i: Int, j: Int) {
        ticTacToe.moveAt(i, j)
    }

    override fun movedAt(x: Int, y: Int, z: Int) {
        if (z == TicTacToe.BoardState.MOVE_X)
            findViewById<TicTacToeView>(R.id.ticTacToeView).drawXAtPosition(x, y)
        else
            findViewById<TicTacToeView>(R.id.ticTacToeView).drawOAtPosition(x, y)
    }

    @SuppressLint("SetTextI18n")
    override fun gameEndsWithATie() {
        findViewById<TextView>(R.id.information).visibility = View.VISIBLE
        findViewById<TextView>(R.id.information).text = "Game ends in a draw"
        findViewById<Button>(R.id.resetButton).visibility = View.VISIBLE
        findViewById<TicTacToeView>(R.id.ticTacToeView).isEnabled = false
    }

    private fun resetGameUi() {
        findViewById<TicTacToeView>(R.id.ticTacToeView).reset()
        findViewById<TicTacToeView>(R.id.ticTacToeView).isEnabled = true
        findViewById<TextView>(R.id.information).visibility = View.GONE
        findViewById<Button>(R.id.resetButton).visibility = View.GONE
    }

    @SuppressLint("SetTextI18n")
    override fun gameWonBy(
        boardPlayer: TicTacToe.BoardPlayer,
        winCoords: Array<TicTacToe.SquareCoordinates>
    ) {
        findViewById<TextView>(R.id.information).visibility = View.VISIBLE
        findViewById<TextView>(R.id.information).text = "Winner is ${if (boardPlayer.move == TicTacToe.BoardState.MOVE_X) "X" else "O"}"
        findViewById<TicTacToeView>(R.id.ticTacToeView).animateWin(
            winCoords[0].i,
            winCoords[0].j,
            winCoords[2].i,
            winCoords[2].j
        )
        findViewById<TicTacToeView>(R.id.ticTacToeView).isEnabled = false
        findViewById<Button>(R.id.resetButton).visibility = View.VISIBLE
    }
}