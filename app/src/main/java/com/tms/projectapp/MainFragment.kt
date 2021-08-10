package com.tms.projectapp

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.ActivityNavigator
import androidx.navigation.findNavController
import com.tms.projectapp.databinding.FragmentScheduleBinding
import com.tms.projectapp.databinding.MainFragmentBinding
import java.lang.Exception

class MainFragment: Fragment() {

    private var binding: MainFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnSchedule?.setOnClickListener{
            it.findNavController()
                .navigate(MainFragmentDirections.actionFragmentMainToFragmentSchedule())
        }

        binding?.btnCall?.setOnClickListener {
            makeCall("+375296321679")
        }

    }


    private fun makeCall(number: String): Boolean{
        return try{
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel: $number"))
            startActivity(intent)
            true
        }catch (e: Exception){
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context, "Phone number not found", duration).show()
            false
        }
    }


}