package com.renter.android.Ui.SignUP

import Reposotiry.RoomDB.UserDB
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.renter.android.R
import com.renter.android.Ui.MainActivity
import kotlinx.android.synthetic.main.activity_add_or_skip_image.*

class AddOrSkipImage : AppCompatActivity() {


    private var  CurrentUser_ID = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_skip_image)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        try {
            CurrentUser_ID = intent.getStringExtra("CurrentUser_ID")!!
        }catch (e:Exception){

        }



        p_skip.setOnClickListener {

            val room = Room.databaseBuilder(this, UserDB::class.java, "UserDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
            val db = room.userDoa()
            val user = db.GetUser(CurrentUser_ID) [0]

            user.ProfileImage = "Empty"

            db.UpdateUser(user)

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("CurrentUser_ID", CurrentUser_ID)
            startActivity(intent)

        }

        p_btn_add_image.setOnClickListener {

            val intent = Intent(this, ProfileImage::class.java)
            intent.putExtra("CurrentUser_ID", CurrentUser_ID)
            startActivity(intent)

        }


    }
}