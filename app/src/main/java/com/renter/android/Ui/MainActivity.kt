package com.renter.android.Ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.renter.android.R
import com.renter.android.Ui.Fragments.Add
import com.renter.android.Ui.Fragments.Main
import com.renter.android.Ui.Fragments.Profile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportFragmentManager.beginTransaction().replace(R.id.fragments_container, Main()).commit()

        bottom_nav.setOnNavigationItemSelectedListener {

            var selected:Fragment? = null

            when(it.itemId){

                R.id.nav_add ->{
                    selected = Add()

                }

                R.id.nav_main ->{
                    selected = Main()

                }

                R.id.nav_profile ->{
                    selected = Profile()

                }

            }

            if (selected != null) {
                supportFragmentManager.beginTransaction().replace(R.id.fragments_container, selected).commit()
            }


            return@setOnNavigationItemSelectedListener true
        }

    }
}