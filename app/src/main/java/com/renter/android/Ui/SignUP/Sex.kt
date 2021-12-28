package com.renter.android.Ui.SignUP

import Reposotiry.RetrofitPackage.NetworkingRetrofit
import Reposotiry.RetrofitPackage.RetrofitApi
import Reposotiry.RoomDB.UserDB
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.renter.android.R
import com.renter.android.Ui.MainActivity
import kotlinx.android.synthetic.main.activity_sex.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class Sex : AppCompatActivity() {

    private var CurrentUser_ID  = ""
    private var sexyOfUser = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sex)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        try {
            CurrentUser_ID = intent.getStringExtra("CurrentUser_ID")!!
        }catch (e:Exception){

        }


        step6_man.setOnClickListener {
            step6_woman.background = ContextCompat.getDrawable(this, R.drawable.step6_befor)
            step6_man.background = ContextCompat.getDrawable(this, R.drawable.step6_after)
            sex_btn.isEnabled = true
            sexyOfUser = "Male"
        }
        step6_woman.setOnClickListener {
            step6_man.background = ContextCompat.getDrawable(this, R.drawable.step6_befor)
            step6_woman.background = ContextCompat.getDrawable(this, R.drawable.step6_after)
            sex_btn.isEnabled = true
            sexyOfUser = "Female"
        }


        sex_btn.setOnClickListener {

            sex_btn.visibility = View.INVISIBLE
            sex_btn.isEnabled = false
            sex_wait.visibility = View.VISIBLE

            GlobalScope.launch {

                val room = Room.databaseBuilder(this@Sex, UserDB::class.java, "UserDB")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build()
                val db = room.userDoa()

                val user = db.GetUser(CurrentUser_ID)[0]

                user.Sex = sexyOfUser
                user.ProfileImage = "Empty"


                val response = NetworkingRetrofit().retrofit
                    .create(RetrofitApi::class.java).SignUp(user)

                when(response.isSuccessful){

                    true->{

                        val json  = JSONObject(Gson().toJson(response.body()))
                        when(json.getBoolean("Statue")){

                            true->{
                                db.UpdateUser(user)
                                val intent = Intent(this@Sex, AddOrSkipImage::class.java)
                                intent.putExtra("CurrentUser_ID", CurrentUser_ID)
                                startActivity(intent)
                            }

                            false->{
                                sex_btn.visibility = View.VISIBLE
                                sex_btn.isEnabled = true
                                sex_wait.visibility = View.GONE
                                Toast.makeText(this@Sex, "Issue in our server, try later", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    false ->{

                        Toast.makeText(this@Sex, "Issue connection", Toast.LENGTH_LONG).show()
                        sex_btn.visibility = View.VISIBLE
                        sex_btn.isEnabled = true
                        sex_wait.visibility = View.GONE

                    }
                }
            }
        }
    }
}