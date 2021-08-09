package com.tms.projectapp.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tms.projectapp.MainActivity
import com.tms.projectapp.ScheduleAdapter
import com.tms.projectapp.database.Data
import com.tms.projectapp.databinding.FragmentScheduleBinding
import org.koin.android.viewmodel.ext.android.viewModel

class ScheduleFragment: Fragment(), ListProvider {

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

        binding?.btnEdit?.setOnClickListener{
            binding?.btnEdit?.visibility = View.INVISIBLE
            binding?.btnBack?.visibility = View.INVISIBLE
            binding?.rvSchedule?.visibility = View.INVISIBLE
            val addFragment = AddFragment()

            binding?.fragmentContainerView?.id?.let { it1 ->
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(it1, addFragment,REPLACE_ADD )
                    ?.addToBackStack(null)
                    ?.commit()
            }
        }

        binding?.rvSchedule?.layoutManager =
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

        val scheduleAdapter = ScheduleAdapter{click(it)}
        binding?.rvSchedule?.adapter= scheduleAdapter
        viewModel.liveData.observe(this.viewLifecycleOwner){
            scheduleAdapter.submitList(it)
        }

        binding?.btnBack?.setOnClickListener {
            (activity as? ListProvider)?.makeVisible()
            val fragmentBack =  activity?.supportFragmentManager
            fragmentBack?.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    fun click(data: Data) {
        viewModel.deleteFromDataBase(data)
    }

    companion object{
        const val REPLACE_ADD = "REPLACE_ADD"
    }

    override fun makeVisible() {
        binding!!.rvSchedule.visibility = View.VISIBLE
        binding!!.btnEdit.visibility = View.VISIBLE
        binding!!.btnBack.visibility = View.VISIBLE
    }

}