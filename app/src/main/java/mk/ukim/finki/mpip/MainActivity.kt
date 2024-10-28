package mk.ukim.finki.mpip

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickPhotoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        val textView: TextView = findViewById(R.id.textView);
        val buttonExplicit: Button = findViewById(R.id.buttonExplicit);
        val buttonImplicit: Button = findViewById(R.id.buttonImplicit);
        val buttonSelectPhoto: Button = findViewById(R.id.buttonSelectPhoto);

        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    val message = data?.getStringExtra("result_message")
                    textView.text = message
                    print("Result message: $message")
                } else {
                    textView.text = ""
                }
            }

        pickPhotoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val selectedImageUri: Uri? = result.data?.data
                if (selectedImageUri != null) {
                    displayImage(selectedImageUri)
                }
            }
        }

        buttonExplicit.setOnClickListener {
            val intent = Intent(
                this,
                ExplicitActivity::class.java
            )
            resultLauncher.launch(intent)
        }

        buttonImplicit.setOnClickListener {
            val intent = Intent("mk.ukim.finki.mpip.IMPLICIT_ACTION")
            startActivity(intent)
        }


        buttonSelectPhoto.setOnClickListener {
            selectImageFromGallery()
        }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        pickPhotoLauncher.launch(intent)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun displayImage(imageUri: Uri) {
        val viewIntent = Intent(Intent.ACTION_VIEW).apply {
            data = imageUri
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        }
        if (viewIntent.resolveActivity(packageManager) != null) {
            startActivity(viewIntent)
        }
    }
}