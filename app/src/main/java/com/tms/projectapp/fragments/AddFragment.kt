package com.tms.projectapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.JobIntentService
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.tms.projectapp.databinding.FragmentEditBinding
import com.tms.projectapp.viewModel.ScheduleViewModel
import com.tms.projectapp.widget.UpdateWidgetService
import com.tms.projectapp.widget.UpdateWidgetService.Companion.DAY_LESSON
import com.tms.projectapp.widget.UpdateWidgetService.Companion.NAME_LESSON
import com.tms.projectapp.widget.UpdateWidgetService.Companion.TIME_LESSON
import com.tms.projectapp.widget.UpdateWidgetService.Companion.UPDATE_ID
import com.tms.projectapp.widget.UpdateWidgetService.Companion.WEEK_LESSON
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
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

    @KoinApiExtension
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        try {
            binding?.btnAdd?.setOnClickListener{

                it.findNavController().popBackStack()

                val intent = Intent(context,UpdateWidgetService::class.java)
                intent.action = UpdateWidgetService.DATA_SYNC

                intent.putExtra(NAME_LESSON,binding?.spin?.selectedItem.toString())
                intent.putExtra(DAY_LESSON,binding?.spinDay?.selectedItem.toString())
                intent.putExtra(TIME_LESSON, binding!!.editTime.text.toString())
                intent.putExtra(WEEK_LESSON,binding!!.editWeek.text.toString())

                JobIntentService.enqueueWork(requireContext(),UpdateWidgetService::class.java,
                    UPDATE_ID,intent)

                addToBase()
            }

        }catch (e: Exception){
            val text = "Input definitions: ${e}"
            val duration = Toast.LENGTH_SHORT
            Toast.makeText(context,text, duration).show()
        }



   }

    private fun addToBase(){
        viewModel.addToDataBase(
            binding!!.spin.selectedItem.toString(),
            binding!!.spinDay.selectedItem.toString(),
            binding!!.editWeek.text.toString(),
            binding!!.editTime.text.toString()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding =null
    }



}