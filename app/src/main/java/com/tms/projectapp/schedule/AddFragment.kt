package com.tms.projectapp.schedule

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tms.projectapp.MainActivity
import com.tms.projectapp.R
import com.tms.projectapp.database.Data
import com.tms.projectapp.databinding.FragmentEditBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class AddFragment: Fragment() {

    private var binding: FragmentEditBinding? = null
    private val viewModel: ScheduleViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            binding?.btnAdd?.setOnClickListener{
                    viewModel.addToDataBase(
                        binding!!.spin.selectedItem.toString(),
                        binding!!.spinDay.selectedItem.toString(),
                        binding!!.editWeek.text.toString(),
                        binding!!.editTime.text.toString()

                    )
                it.findNavController().popBackStack()
                }

        }catch (e: Exception){
            val text = "Input definitions: ${e}"
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context,text, duration).show()
        }


        val intent = Intent()
        intent.putExtra(NAME_LESSON,binding?.spin?.selectedItem.toString())
        intent.putExtra(TIME_LESSON,binding!!.editTime.text.toString())
        intent.putExtra(WEEK_LESSON,binding?.editWeek.toString())
        intent.putExtra(DAY_LESSON,binding?.spinDay?.selectedItem.toString())
   }

    private fun makeLong(text: String): Long = text.replace(":","").toLong()


    companion object{
        const val NAME_LESSON = "NAME_LESSON"
        const val TIME_LESSON = "TIME_LESSON"
        const val WEEK_LESSON = "WEEK_LESSON"
        const val DAY_LESSON = "DAY_LESSON"

    }

    override fun onDestroy() {
        super.onDestroy()
        binding =null
    }



}