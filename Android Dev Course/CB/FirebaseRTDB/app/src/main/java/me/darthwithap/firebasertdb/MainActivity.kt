package me.darthwithap.firebasertdb

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Error

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    companion object {
        private const val PICK_IMAGE_REQUEST_ID = 1
    }

    private var imageUri: Uri? = null
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storageReference = FirebaseStorage.getInstance().getReference("uploads")
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads")

        btnChooseImage.setOnClickListener {
            openFileChooser()
        }

        btnUpload.setOnClickListener {
            uploadImage()
        }

        tvShowUploads.setOnClickListener {
            startActivity(Intent(this, ImagesActivity::class.java))
        }
    }

    private fun uploadImage() {
        if (imageUri != null) {
            val imageReference = storageReference.child(
                "${System.currentTimeMillis()}.img"
            )

            btnUpload.isEnabled = false
            imageReference.putFile(imageUri!!)
                .addOnSuccessListener {
                    Handler(Looper.getMainLooper()).postDelayed({
                        pbHorizontal.progress = 0
                        Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()
                        var downloadUrl = ""
                        imageReference.downloadUrl.addOnSuccessListener {
                            downloadUrl = it.toString()
                        }
                        val upload = Upload(
                            etImageName.text.toString().trim(),
                            downloadUrl
                        )
                        databaseReference.child(databaseReference.push().key!!).setValue(upload)
                        btnUpload.isEnabled = true
                    }, 200)
                }
                .addOnFailureListener {
                    Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    btnUpload.isEnabled = true
                }
                .addOnProgressListener {
                    pbHorizontal.progress = (100 * it.bytesTransferred / it.totalByteCount).toInt()
                }

        } else Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
    }

    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST_ID && resultCode == Activity.RESULT_OK
            && data?.data != null
        ) {
            imageUri = data.data
            Picasso.get().load(imageUri).into(ivImage)
        }
    }


}