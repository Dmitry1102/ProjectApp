package com.tms.projectapp.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tms.projectapp.MainActivity
import com.tms.projectapp.ScheduleAdapter
import com.tms.projectapp.database.Data
import com.tms.projectapp.databinding.FragmentScheduleBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ScheduleFragment: Fragment() {

    private var binding: FragmentScheduleBinding? = null
    private val viewModel:ScheduleViewModel by viewModel()

    init{
        binding?.rvSchedule?.visibility = View.VISIBLE
        binding?.btnEdit?.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnEdit?.setOnClickListener{
            binding?.rvSchedule?.visibility = View.INVISIBLE
            binding?.btnEdit?.visibility = View.INVISIBLE

            val mainActivity = (activity as MainActivity)
            mainActivity.openAddFragment()

            val scheduleAdapter = ScheduleAdapter{click(it)}
            binding?.rvSchedule?.adapter= scheduleAdapter


            viewModel.liveData.observe(this.viewLifecycleOwner){
                scheduleAdapter.submitList(it)
            }


        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun click(data: Data) {
        viewModel.deleteFromDataBase(data)
    }




}