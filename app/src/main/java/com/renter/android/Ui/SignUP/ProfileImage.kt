package com.renter.android.Ui.SignUP

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.renter.android.R
import android.provider.MediaStore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_image.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_image)


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        profile_image_img.setOnClickListener {

            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){

                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){

                    UploadImageFromExternalStorage()
                }

            }

        }


    }

    private fun UploadImageFromExternalStorage() {

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 12)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 12 && resultCode ==Activity.RESULT_OK && data!!.data != null){

            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val uri = data.data
            val cursor = uri?.let { contentResolver.query(it, projection, null, null, null) }

            val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)!!
            cursor.moveToFirst()
            val image  = cursor.getString(index)
            Picasso.get().load(image).centerCrop().resize(420,420).into(profile_image_img)

            GlobalScope.launch {


            }

        }

    }

}