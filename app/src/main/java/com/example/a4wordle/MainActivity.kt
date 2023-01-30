package com.example.a4wordle

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import com.example.a4wordle.FourLetterWordList
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class MainActivity : AppCompatActivity() {
    lateinit var wordToGuess : String
    lateinit var guess1 : TextView
    lateinit var guess2 : TextView
    lateinit var guess3 : TextView
    lateinit var wordText : TextView
    lateinit var submitButton : Button
    lateinit var resetButton : Button
    lateinit var guessInput : EditText
    lateinit var viewKonfetti : KonfettiView
    var guesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        guess1 = findViewById<TextView>(R.id.guess1)
        guess2 = findViewById<TextView>(R.id.guess2)
        guess3 = findViewById<TextView>(R.id.guess3)
        wordText = findViewById<TextView>(R.id.wordToGuess)
        submitButton = findViewById<Button>(R.id.submitButton)
        resetButton = findViewById<Button>(R.id.resetButton)
        guessInput = findViewById<EditText>(R.id.guessInput)
        viewKonfetti = findViewById<KonfettiView>(R.id.konfetti)

        resetGame()

        submitButton.setOnClickListener {
            var userGuess = guessInput.text.toString()
            var checkedGuess = checkGuess(userGuess.uppercase())

            if(guesses == 0){
                guess1.text = checkedGuess
                guess1.visibility = View.VISIBLE
                if (userGuess.uppercase() == wordToGuess){
                    endGame(true)
                }
                guesses++
            }
            else if(guesses == 1){
                guess2.text = checkedGuess
                guess2.visibility = View.VISIBLE
                if (userGuess.uppercase() == wordToGuess){
                    endGame(true)
                }
                guesses++
            }
            else{
                guess3.text = checkedGuess
                guess3.visibility = View.VISIBLE
                if (userGuess.uppercase() == wordToGuess){
                    endGame(true)
                }
                else{
                    endGame(false)
                }
            }
        }

        resetButton.setOnClickListener{
            resetGame()
        }
    }
    private fun endGame(win: Boolean) {
        submitButton.visibility = View.INVISIBLE
        guessInput.visibility = View.INVISIBLE
        wordText.visibility = View.VISIBLE
        if(win){
            Toast.makeText(this, "Correct!", Toast.LENGTH_LONG).show()
            viewKonfetti.build()
                .addColors(Color.RED, Color.GREEN, Color.YELLOW, Color.MAGENTA, Color.CYAN)
                .setDirection(0.0,359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(1000L)
                .addShapes(Shape.Circle)
                .addSizes(Size(10), Size(12), Size(14))
                .setPosition(-50f, viewKonfetti.width + 50f, -50f, viewKonfetti.height + 50f)
                .streamFor(500, 2000L)
        }
        else{
            Toast.makeText(this, "Out of Guesses :(", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkGuess(guess: String) : SpannableString {
        val result = SpannableString(guess)

        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result.setSpan(ForegroundColorSpan(Color.GREEN), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else if (guess[i] in wordToGuess) {
                result.setSpan(ForegroundColorSpan(Color.YELLOW), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else {
                result.setSpan(ForegroundColorSpan(Color.RED), i, i+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return result
    }

    /**
     * Resets Game State
     */
    private fun resetGame() {
        guess1.visibility = View.INVISIBLE
        guess2.visibility = View.INVISIBLE
        guess3.visibility = View.INVISIBLE
        wordText.visibility = View.INVISIBLE
        submitButton.visibility = View.VISIBLE
        resetButton.visibility = View.VISIBLE
        guessInput.visibility = View.VISIBLE
        guesses = 0
        wordToGuess = FourLetterWordList.getRandomFourLetterWord()
        wordText.text = wordToGuess
    }
}