package com.example.tictactoe_vs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class ChoiceActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice)

        val context: Context = this
        val choosePlayerButton = findViewById<View>(R.id.button_choose_player2) as Button
        choosePlayerButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }
        val chooseAIButton: Button = findViewById(R.id.button_choose_ai)
        chooseAIButton.setOnClickListener {
            val intent = Intent(context, AIMainActivity::class.java)
            startActivity(intent)
        }
    }

}

