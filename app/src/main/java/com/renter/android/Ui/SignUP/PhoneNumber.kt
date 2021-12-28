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
import androidx.core.widget.doOnTextChanged
import androidx.room.Room
import com.google.gson.Gson
import com.renter.android.R
import kotlinx.android.synthetic.main.activity_phone_number.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject

class PhoneNumber : AppCompatActivity() {
    private var  CurrentUser_ID = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        try {
            CurrentUser_ID = intent.getStringExtra("CurrentUser_ID")!!
        }catch(e:Exception){

        }

        full_name_phone_number_filled.setText("+212-")

        full_name_phone_number_filled.doOnTextChanged { text, start, before, count ->

            phone_number_btn.isEnabled  =text!!.contains("+212-")&& text.length == 14

        }

        phone_number_btn.setOnClickListener {

            phone_number_btn.visibility = View.INVISIBLE
            phone_number_btn.isEnabled = false
            phone_number_wait.visibility = View.VISIBLE

            GlobalScope.launch {


                val response = NetworkingRetrofit().retrofit.
                create(RetrofitApi::class.java).
                CodeVerifyAtSignUp(full_name_phone_number_filled.text!!.toString())


                when(response.isSuccessful){
                    true->{
                        val res = JSONObject(Gson().toJson(response.body()))
                        val statue = res.getBoolean("Statue")
                        val message  =res.getString("Message")

                        when(statue){

                            true -> {

                                val room = Room.databaseBuilder(this@PhoneNumber, UserDB::class.java, "UserDB").allowMainThreadQueries().fallbackToDestructiveMigration().build()
                                val db = room.userDoa()

                                val user = db.GetUser(CurrentUser_ID) [0]
                                user.PhoneNumber = full_name_phone_number_filled.text!!.toString()

                                db.UpdateUser(user)

                                val intent = Intent(this@PhoneNumber, VerifyPhoneNumber::class.java)
                                intent.putExtra("Code", message)
                                intent.putExtra("CurrentUser_ID", CurrentUser_ID)
                                startActivity(intent)

                            }

                            false -> {
                                phone_number_btn.visibility = View.VISIBLE
                                phone_number_btn.isEnabled = true
                                phone_number_wait.visibility = View.GONE
                                Toast.makeText(this@PhoneNumber, "Issue in our server, try later", Toast.LENGTH_LONG).show()
                            }
                        }
                    }

                    false->{
                        phone_number_btn.visibility = View.VISIBLE
                        phone_number_btn.isEnabled = true
                        phone_number_wait.visibility = View.GONE
                        Toast.makeText(this@PhoneNumber, "Issue Connection", Toast.LENGTH_LONG).show()
                    }
                }

            }



        }


    }
}