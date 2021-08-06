package com.tms.projectapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.tms.projectapp.databinding.ActivityMainBinding
import com.tms.projectapp.schedule.AddFragment
import com.tms.projectapp.schedule.ScheduleFragment


class MainActivity : AppCompatActivity() {
//
    private val scheduleFragment = ScheduleFragment()
    private val addFragment = AddFragment()


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

        }

        binding.btnCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_CALL, Uri.parse(MOTHER_PHONE))
            startActivity(intent)
        }




    }

   fun openAddFragment(){
       supportFragmentManager.beginTransaction().add(
           scheduleFragment.id,addFragment, ADD_FRAGMENT_TAG
       ).addToBackStack(null).commit()
   }


    companion object{
        private const val FRAGMENT_TAG = "FRAGMENT_TAG"
        private const val ADD_FRAGMENT_TAG = "ADD_FRAGMENT_TAG"
        private const val MOTHER_PHONE = "+76880098876"
    }
}