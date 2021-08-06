package com.tms.projectapp.schedule

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tms.projectapp.MainActivity
import com.tms.projectapp.database.Data
import com.tms.projectapp.databinding.FragmentEditBinding
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

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

        val time = binding!!.editTime.text.toString()
        val scheduleTime = time.toLong()

        binding?.btnAdd?.setOnClickListener{
            viewModel.addToDataBase(
                binding!!.spin.selectedItem.toString(),
                binding!!.spinDay.selectedItem.toString(),
                binding!!.editWeek.text.toString(),
                scheduleTime
            )

            childFragmentManager.beginTransaction().remove(this)

        }

        val intent = Intent()
        intent.putExtra(NAME_LESSON,binding?.spin?.selectedItem.toString())
        intent.putExtra(TIME_LESSON,scheduleTime)
        intent.putExtra(WEEK_LESSON,binding?.editWeek.toString())
        intent.putExtra(DAY_LESSON,binding?.spinDay?.selectedItem.toString())


    }

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