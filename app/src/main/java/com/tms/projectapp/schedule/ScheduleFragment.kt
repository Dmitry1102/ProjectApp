package com.tms.projectapp.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tms.projectapp.database.Data
import com.tms.projectapp.databinding.FragmentScheduleBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ScheduleFragment: Fragment(){

    private var binding: FragmentScheduleBinding? = null
    private val viewModel:ScheduleViewModel by viewModel()

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


        binding?.btnEdit?.setOnClickListener {
            it.findNavController()
                .navigate(ScheduleFragmentDirections.actionFragmentScheduleToFragmentAdd())
        }


        binding?.rvSchedule?.layoutManager =
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val scheduleAdapter = ScheduleAdapter{data -> binding?.btnDelete?.setOnClickListener {
            click(data)
        }}
        binding?.rvSchedule?.adapter= scheduleAdapter
        viewModel.liveData.observe(this.viewLifecycleOwner){
            scheduleAdapter.submitList(it)
        }





        binding?.btnBack?.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun click(data: Data) {
        viewModel.deleteFromDataBase(data)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


    companion object{
        const val REPLACE_ADD = "REPLACE_ADD"
    }


}