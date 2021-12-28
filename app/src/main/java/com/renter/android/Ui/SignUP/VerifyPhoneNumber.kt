package com.renter.android.Ui.SignUP

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.renter.android.R
import kotlinx.android.synthetic.main.activity_verify_phone_number.*

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

        verify_Code.setOnPinEnteredListener {

            verify_Code.setTextColor(ContextCompat.getColor(this, R.color.black))

            when(it.toString()){

                Code  ->{
                    val intent = Intent(this, Sex::class.java)
                    intent.putExtra("CurrentUser_ID", CurrentUser_ID)
                    startActivity(intent)
                }

                else -> {
                    verify_Code.setTextColor(ContextCompat.getColor(this, R.color.red))
                }

            }

        }

    }
}