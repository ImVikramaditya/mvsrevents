package com.college.mvsrevents

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.webkit.MimeTypeMap
import android.widget.*
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.OnProgressListener
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import java.util.*
lateinit var edttxt_eventname:EditText
lateinit var edttxt_eventdate:EditText
lateinit var edttxt_eventCoordinator:EditText
lateinit var btn_insert_image:Button
lateinit var btn_upload:Button
lateinit var img_insert_preview:ImageView
lateinit var mImageUri: Uri
lateinit var progressbar:ProgressBar
class upload_an_event : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST = 71
    // private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
//    private var storageReference: StorageReference? = null
    var storageReference: StorageReference = FirebaseStorage.getInstance().getReference("events")
    val databaseReference:DatabaseReference=
    FirebaseDatabase.getInstance().getReference("events")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_an_event)
        //initialising views
        edttxt_eventname=findViewById(R.id.edttext_eventname)
        edttxt_eventdate=findViewById(R.id.edttext_eventdate)
        edttxt_eventCoordinator=findViewById(R.id.edttext_eventcoordinator_student)
        btn_insert_image=findViewById(R.id.btn_insert_image)
        btn_upload=findViewById(R.id.btn_upload)
        img_insert_preview=findViewById(R.id.img_insert_preview)
        progressbar=findViewById(R.id.progress_bar)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        btn_insert_image.setOnClickListener {
            openFileChooser()
        }
        btn_upload.setOnClickListener {
            uploadEvents()
        }
    }
    fun openFileChooser(){
        val intent=Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent,PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE_REQUEST && resultCode== RESULT_OK
            && data!=null && data.data!=null){
            mImageUri= data.getData()!!
            Picasso.get().load(mImageUri).into(img_insert_preview)
        }
    }
    fun getfileExtension(uri:Uri): String? {
        val contentResolver=contentResolver
        val mime:MimeTypeMap= MimeTypeMap.getSingleton()
        return mime.getMimeTypeFromExtension(contentResolver.getType(uri))
    }
    fun uploadEvents(){
        if(mImageUri != null){
            val ref = storageReference?.child("events/" +
                    UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(mImageUri!!)
            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> {
                        task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        addUploadRecordToDb(downloadUri.toString())
                    } else {
                        // Handle failures
                    }
                }?.addOnFailureListener{
                }
        }else{
            Toast.makeText(this, "Please Upload an Image",
                Toast.LENGTH_SHORT).show()
        }
        /* if(mImageUri!=null && edttxt_eventname.text!=null &&
       edttxt_eventdate.text!=null && edttxt_eventCoordinator.text!=null){
        val
       newref:DatabaseReference=reference.child(edttxt_eventname.text.toString())
        val eventnew=EventsHelperClass(edttxt_eventname.text.toString(),
       edttxt_eventdate.text.toString(),
        edttxt_eventCoordinator.text.toString()," ")
        newref.setValue(eventnew)
        val
       databaseReference=FirebaseStorage.getInstance().getReference(eventnew.eventname)
        val
       imageref=databaseReference.child(edttxt_eventname.text.toString()+"Poster."+getfile
       Extension(
        mImageUri))

       imageref.putFile(mImageUri).addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot>(){
        val handler=Handler()
        handler.postDelayed(Runnable(){
        kotlin.run {
        progressbar.progress = 0
        }
        },5000)
        val uploadid: String? =reference.push().getKey()
        if (uploadid != null) {
        newref.child(uploadid).setValue(it.downloadUri)
        }
        }).addOnFailureListener(OnFailureListener(){
        Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
        }).addOnProgressListener(OnProgressListener<UploadTask.TaskSnapshot>()
       {
        val progress:Double=(100.0 * it.bytesTransferred /it.totalByteCount
       )
        progressbar.setProgress(progress.toInt())
        })
        }
        else{
        Toast.makeText(this,"fill all the fields in the
       form",Toast.LENGTH_SHORT).show()
        }*/
    }
    private fun addUploadRecordToDb(uri: String){
        val db:DatabaseReference
                =FirebaseDatabase.getInstance().getReference("events")
        val data = uri
        //data["imageUrl"] = uri
        val newevent = EventsHelperClass(edttxt_eventname.text.toString(),
            edttxt_eventdate.text.toString(),
            edttxt_eventCoordinator.text.toString(),data)
        val newref:DatabaseReference=db.child(edttxt_eventname.text.toString())
        newref.setValue(newevent)
    }
}
