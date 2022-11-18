package com.rxyzent.remainder.ui.main.home

import android.content.Intent
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
import com.rxyzent.remainder.ui.details.DetailsActivity
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import kotlin.math.roundToInt

class HomeFragment:DaggerFragment() {

    private val binding by lazy {
        HomeFragmentBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory
    private val viewModel: HomeViewModel by viewModels { vmProviderFactory }

    private val adapter = ReminderListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
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

        adapter.onListItemClick = {
            val intent = Intent(activity,DetailsActivity::class.java)
            EventBus.getDefault().postSticky(it)
            intent.putExtra("flag","edit")
            startActivity(intent)
        }

        viewModel.loadAllData()
        viewModel.listLiveData.observe(viewLifecycleOwner){
            EventBus.getDefault().postSticky(it)
            adapter.setData(it)
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner){
            //findNavController().popBackStack()
        }

    }
}