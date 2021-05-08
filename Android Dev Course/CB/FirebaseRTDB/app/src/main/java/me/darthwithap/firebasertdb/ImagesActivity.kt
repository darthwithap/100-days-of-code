package me.darthwithap.firebasertdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_images.*

private const val TAG = "ImagesActivity"

class ImagesActivity : AppCompatActivity(), ImageAdapter.OnItemClickListener {
    private var uploads = ArrayList<Upload>()
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var storage: FirebaseStorage
    private lateinit var databaseReference: DatabaseReference
    private lateinit var dbListener: ValueEventListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)

        imageAdapter = ImageAdapter(uploads)
        imageAdapter.setOnItemClickListener(this)
        rvImages.adapter = imageAdapter
        storage = FirebaseStorage.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads")

        dbListener = databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ImagesActivity, "${error.message}", Toast.LENGTH_SHORT).show()
                pbLoading.visibility = View.INVISIBLE
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                uploads.clear()
                snapshot.children.forEach {
                    val upload = it.getValue(Upload::class.java)
                    upload?.key = it.key
                    uploads.add(upload!!)
                }

                imageAdapter.notifyDataSetChanged()
                pbLoading.visibility = View.INVISIBLE
            }
        })
    }

    override fun onItemClick(pos: Int) {
        TODO("Not yet implemented")
    }

    override fun onDeleteClick(pos: Int) {
        TODO("Not yet implemented")
    }

    override fun onOtherClick(pos: Int) {
        TODO("Not yet implemented")
    }
}