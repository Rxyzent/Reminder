package com.rxyzent.remainder.ui.main.completed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.adapter.ReminderListAdapter
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.core.utils.VerticalItemDecoration
import com.rxyzent.remainder.databinding.HomeFragmentBinding
import com.rxyzent.remainder.ui.main.payment.PaymentViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import kotlin.math.roundToInt

class CompletedFragment : DaggerFragment(){

    private val binding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory
    private val viewModel: CompletedViewModel by viewModels { vmProviderFactory }

    private val adapter = ReminderListAdapter()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(requireContext())

        val size=resources.getDimension(R.dimen._12db)
        val decoration= VerticalItemDecoration(size.roundToInt())
        binding.list.addItemDecoration(decoration)

        viewModel.loadData()
        viewModel.listLiveData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner){
           // findNavController().popBackStack()
        }
    }
}