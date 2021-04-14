package com.example.tictactoe_vs

import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class AIMainActivity :AppCompatActivity() {

    private lateinit var namePlayer1 : TextView
    private lateinit var namePlayer2 : TextView
    private lateinit var positions : Array<Array<Button>>
    private lateinit var nameOfPlayer1 : String
    private lateinit var nameOfPlayer2 : String
    private var turnOfPlayer1 : Boolean = true
    private var moveCount : Int = 1
    private var pointsOfPlayer1 : Int = 0
    private var pointsOfPlayer2 : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        namePlayer1 = findViewById(R.id.text_view_player_1)
        namePlayer2 = findViewById(R.id.text_view_player_2)
        positions = Array(3) { row -> Array(3) { column -> getButton(row, column)}}
        enterNameOfPlayer1()
        showWarning()
        val buttonResetBoard : Button = findViewById(R.id.button_resetBoard)
        val buttonResetScore : Button = findViewById(R.id.button_resetScore)
        buttonResetBoard.setOnClickListener {
            showBoardCleaner()
        }
        buttonResetScore.setOnClickListener {
            showBoardUpdater()
        }
    }


    private fun getButton(row: Int, column: Int): Button {
        val button: Button =
            findViewById(resources.getIdentifier("button_$row$column", "id", packageName))
        button.setOnClickListener {
            onClick(button)
        }
        return button
    }
/*From my perspective the issue is that there are moments when the generated value of button placement is equal to the value of the clicked button.
"MoveCount" value is upped because there is only one player that makes moves.*/
    private fun moveAI() {
        val rR = Random().nextInt(2)
        val rC = Random().nextInt(2)
        for (i in positions.indices) {
            for (j in positions.indices) {
                if (positions[rR][rC].text.isNotBlank() || positions[rR][rC].text == "X") {
                    break
                } else if (positions[rR][rC].text.isBlank()) {
                    positions[rR][rC].text = "O"
                    break
                }
            }
        }
    }

    private fun onClick(button: Button) {
        if(button.text.isNotBlank()) return
        button.text = "X"
        moveAI()
        moveCount += 2

        if(winningPositions()){
            if(turnOfPlayer1) win(1) else win(2)
        } else if(moveCount == 9){
            draw()
        } else{
            turnOfPlayer1 = !turnOfPlayer1
        }
    }
    /* winning positions
    * (0; 1; 2) top left -> top right
    * (3; 4; 5) middle left -> middle right
    * (6; 7; 8) bottom left -> bottom right
    * (0; 3; 6) top left -> bottom left
    * (1; 4; 7) top middle -> bottom middle
    * (2; 5; 8) top right -> bottom right
    * (2; 4; 6) top right -> bottom left
    * (0; 4; 8) top left -> bottom right
    * */
    private fun winningPositions(): Boolean {
        //checking whether the game has been won by either of two players via "side to side" combination
        for(i in positions.indices){
            if((positions[i][0].text.isNotBlank()) && (positions[i][0].text == positions[i][1].text) && (positions[i][0].text == positions[i][2].text))
                return true
        }
        //checking whether the game has been won by either of two players via "top to bottom" combination
        for(i in positions.indices){
            if((positions[0][i].text.isNotBlank()) && (positions[0][i].text == positions[1][i].text) && (positions[0][i].text == positions[2][i].text))
                return true
        }
        //checking whether the game has been won by either of two players via diagonal combinations
        if((positions[0][2].text.isNotBlank()) && (positions[0][2].text == positions[1][1].text) && (positions[0][2].text == positions[2][0].text))
            return true

        if((positions[0][0].text.isNotBlank()) && (positions[0][0].text == positions[1][1].text) && (positions[0][0].text == positions[2][2].text))
            return true

        return false
    }

    private fun win(winner: Int){
        if(winner == 1) {
            pointsOfPlayer1++
            Toast.makeText(this, "$nameOfPlayer1 has won the game!", Toast.LENGTH_LONG).show()
        } else if(winner == 2) {
            pointsOfPlayer2++
            Toast.makeText(this, "$nameOfPlayer2 has won the game!", Toast.LENGTH_LONG).show()
        }
        updateScoreboard()
        clearGrid()
    }

    private fun draw(){
        Toast.makeText(this, "Game has ended as a draw!", Toast.LENGTH_LONG).show()
        clearGrid()
    }

    private fun clearGrid() {
        moveCount = 0
        turnOfPlayer1 = true
        for (i in positions.indices){
            for(j in positions.indices){
                positions[i][j].text = " "
            }
        }
    }

    private fun showBoardCleaner() {
        AlertDialog.Builder(this)
            .setMessage("Do You really want to clear the game board?")
            .setPositiveButton("OK") { _, _ -> clearGrid()
                Toast.makeText(this, "The game has been restarted!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("CANCEL") { _, _ -> }.show()
    }

    private fun updateScoreboard() {
        namePlayer1.text = "$nameOfPlayer1: $pointsOfPlayer1"
        namePlayer2.text = "$nameOfPlayer2: $pointsOfPlayer2"
    }

    private fun showBoardUpdater() {
        AlertDialog.Builder(this)
            .setTitle("This will lead to finishing your game early!")
            .setMessage("Do You really want to clear the players score?")
            .setPositiveButton("OK") { _, _ ->
                pointsOfPlayer1 = 0
                pointsOfPlayer2 = 0
                clearGrid()
                updateScoreboard()
                Toast.makeText(this, "The Scoreboard has been updated!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("CANCEL") { _, _ -> }.show()
    }

    private fun enterNameOfPlayer1() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Enter the name for first (crosses) player!")
        val input = EditText(this)
        input.hint = "Enter Name"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)
        builder.setPositiveButton("OK") { _, _ ->
            nameOfPlayer1 = input.text.toString()
            namePlayer1.text = "$nameOfPlayer1: $pointsOfPlayer1"
            nameOfPlayer2 = "AI"
            namePlayer2.text = "$nameOfPlayer2: $pointsOfPlayer2"
        }
        builder.setNegativeButton("DEFAULT") { _, _ -> }.show()
        nameOfPlayer1 = "Player 1"
        namePlayer1.text = "$nameOfPlayer1: $pointsOfPlayer1"
        nameOfPlayer2 = "AI"
        namePlayer2.text = "$nameOfPlayer2: $pointsOfPlayer2"
    }
    private fun showWarning() {
        AlertDialog.Builder(this)
                .setTitle("WARNING")
                .setMessage("There is a technical issue with PvC game mode which I briefly described in comments")
                .setPositiveButton("CONTINUE") { _, _ -> }.show()
    }

}