package com.renter.android.Ui.SignUP

import Reposotiry.RoomDB.UserDB
import Reposotiry.RoomDB.UserEntity
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.room.Room
import com.renter.android.R
import kotlinx.android.synthetic.main.activity_full_name.*
import java.util.*

class FullName : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_name)


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        full_name_first_name_filled.doOnTextChanged { text, start, before, count ->
            full_name_btn.isEnabled = text!!.isNotEmpty() && full_name_last_name_filled.text!!.isNotEmpty()
        }

        full_name_last_name_filled.doOnTextChanged { text, start, before, count ->
            full_name_btn.isEnabled = text!!.isNotEmpty() && full_name_first_name_filled.text!!.isNotEmpty()
        }

        full_name_btn.setOnClickListener {

            val currentUser_ID = UUID.randomUUID().toString()

            val userEntity = UserEntity(
                currentUser_ID,
                full_name_first_name_filled.text.toString(),
                full_name_last_name_filled.text.toString(),
                "",
                "",
                ""
                )

            val userDB = Room.databaseBuilder(this, UserDB::class.java, "UserDB")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
            val db = userDB.userDoa()

            db.InsertUser(userEntity)

            val sh = getSharedPreferences("CurrentUser_ID", Context.MODE_PRIVATE).edit()
            sh.putString("CurrentUser_ID",currentUser_ID)
            sh.apply()

            val intent = Intent(this, PhoneNumber::class.java)
            intent.putExtra("CurrentUser_ID", currentUser_ID)
            startActivity(intent)
        }

    }
}