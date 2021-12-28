package com.renter.android.Ui.SignUP

import Reposotiry.RetrofitPackage.NetworkingRetrofit
import Reposotiry.RetrofitPackage.RetrofitApi
import Reposotiry.RoomDB.UserDB
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
import android.view.View
import android.widget.Toast
import androidx.room.Room
import com.google.gson.Gson
import com.renter.android.Ui.MainActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_image.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.File

class ProfileImage : AppCompatActivity() {

    private var  CurrentUser_ID = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_image)


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        try {
            CurrentUser_ID = intent.getStringExtra("CurrentUser_ID")!!
        }catch(e:Exception){

        }

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

            profile_image_btn.visibility = View.INVISIBLE
            profile_image_btn.isEnabled = false
            profile_image_wait.visibility = View.VISIBLE

            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val uri = data.data
            val cursor = uri?.let { contentResolver.query(it, projection, null, null, null) }

            val index = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)!!
            cursor.moveToFirst()
            val image  = cursor.getString(index)
            Picasso.get().load(image).centerCrop().resize(420,420).into(profile_image_img)

            val imageNet = "https://renter-28-12-2021.herokuapp.com/profileimagerepositury/$CurrentUser_ID"

            val file = File(image)
            val requestBody = RequestBody.create(MediaType.parse(image), file)
            val multipartBody = MultipartBody.Part.createFormData( "Renter",CurrentUser_ID, requestBody)

            GlobalScope.launch {


                val response = NetworkingRetrofit()
                    .retrofit
                    .create(RetrofitApi::class.java)
                    .UploadProfileImage(multipartBody)

                when(response.isSuccessful){

                    true ->{
                        val json = JSONObject(Gson().toJson(response.body()))
                        when(json.getBoolean("Statue")){

                            true ->{

                                val room = Room.databaseBuilder(this@ProfileImage, UserDB::class.java, "UserDB")
                                    .allowMainThreadQueries()
                                    .fallbackToDestructiveMigration()
                                    .build()
                                val db = room.userDoa()

                                val user = db.GetUser(CurrentUser_ID)[0]
                                user.ProfileImage = imageNet

                                db.UpdateUser(user)

                                val intent = Intent(this@ProfileImage, MainActivity::class.java)
                                intent.putExtra("CurrentUser_ID", CurrentUser_ID)
                                startActivity(intent)
                            }

                            false -> {
                                profile_image_btn.visibility = View.INVISIBLE
                                profile_image_btn.isEnabled = false
                                profile_image_wait.visibility = View.VISIBLE
                                Toast.makeText(this@ProfileImage, "Issue in our server, try later", Toast.LENGTH_LONG).show()

                            }

                        }

                    }

                    false ->{
                        profile_image_btn.visibility = View.INVISIBLE
                        profile_image_btn.isEnabled = false
                        profile_image_wait.visibility = View.VISIBLE
                        Toast.makeText(this@ProfileImage, "Issue connection", Toast.LENGTH_LONG).show()

                    }

                }
            }

        }

    }

}