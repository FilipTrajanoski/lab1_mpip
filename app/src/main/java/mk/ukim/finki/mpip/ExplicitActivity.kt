package mk.ukim.finki.mpip

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ExplicitActivity : AppCompatActivity() {
    private lateinit var editTextResult: EditText
    private lateinit var buttonOk: Button
    private lateinit var buttonCancel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_explicit)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        editTextResult = findViewById(R.id.editTextResult)
        buttonOk = findViewById(R.id.buttonOk)
        buttonCancel = findViewById(R.id.buttonCancel)

        buttonOk.setOnClickListener {
            val resultText = editTextResult.text.toString()
            val resultIntent = Intent().apply {
                putExtra("result_message", resultText)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        buttonCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}