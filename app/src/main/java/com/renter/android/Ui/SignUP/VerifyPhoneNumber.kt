package com.renter.android.Ui.SignUP

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.renter.android.R

class VerifyPhoneNumber : AppCompatActivity() {

    private var CurrentUser_ID  = ""
    private var Code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone_number)


        try{
            CurrentUser_ID = intent.getStringExtra("CurrentUser_ID")!!
            Code = intent.getStringExtra("Code")!!
        }catch(e:Exception){

        }



    }
}