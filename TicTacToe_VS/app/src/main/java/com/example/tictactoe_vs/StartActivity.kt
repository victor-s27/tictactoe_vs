package com.example.tictactoe_vs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val context: Context = this
        val startButton = findViewById<View>(R.id.button_start) as Button
        startButton.setOnClickListener {
            val intent = Intent(context, ChoiceActivity::class.java)
            startActivity(intent)
        }
        val buttonCredits: Button = findViewById(R.id.button_credits)
        buttonCredits.setOnClickListener{
            showCredits()
        }
    }

    private fun showCredits() {
        AlertDialog.Builder(this)
            .setMessage("Tic Tac Toe game by Viktors Stepičevs")
            .setPositiveButton("CONTINUE") { _, _ -> }.show()
    }
}