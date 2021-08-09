package com.tms.projectapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.tms.projectapp.databinding.ActivityMainBinding
import com.tms.projectapp.schedule.AddFragment
import com.tms.projectapp.schedule.ListProvider
import com.tms.projectapp.schedule.ScheduleFragment
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private val scheduleFragment = ScheduleFragment()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnSchedule.setOnClickListener{
            supportFragmentManager.beginTransaction().add(
                binding.frMain.id, scheduleFragment, FRAGMENT_TAG
            ).addToBackStack(null).commit()

            binding.btnSchedule.visibility = View.INVISIBLE
            binding.btnCall.visibility = View.INVISIBLE



        }

        binding.btnCall.setOnClickListener{
            binding.btnSchedule.visibility = View.INVISIBLE
            binding.btnCall.visibility = View.INVISIBLE
            makeCall("+375447273769")
        }

    }


    private fun makeCall(number: String): Boolean{
        return try{
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: $number"))
            startActivity(intent)
            true
        }catch (e: Exception){
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(applicationContext, "Phone number not found", duration )
            false
        }
    }


    companion object{
        private const val FRAGMENT_TAG = "FRAGMENT_TAG"
    }

//     override fun makeVisible(){
//        binding.btnCall.visibility = View.VISIBLE
//        binding.btnSchedule.visibility = View.VISIBLE
//    }
}