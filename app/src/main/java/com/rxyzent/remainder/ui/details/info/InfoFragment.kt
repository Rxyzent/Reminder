package com.rxyzent.remainder.ui.details.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.databinding.InfoFragmentBinding
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import javax.inject.Inject


class InfoFragment :DaggerFragment(){

    private val binding by lazy {
        InfoFragmentBinding.inflate(layoutInflater)
    }


    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory
    private val viewModel: InfoViewModel by viewModels { vmProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: ListData){

        val stickyEvent = EventBus.getDefault().removeStickyEvent(ListData::class.java)
        stickyEvent?.let {
            val simpleDateFormat = SimpleDateFormat("'['EEE']' dd.MM.yyyy '|' HH:mm ")
            val date = simpleDateFormat.format(it.date)

            binding.title.text = it.title
            binding.description.text = it.description
            binding.date.text = date

            if(stickyEvent.type == "todo"){
                binding.Icon.setBackgroundResource(R.drawable.ic_baseline_sticky_note_2_24)
            }else if(it.type == "payment"){
                binding.Icon.setBackgroundResource(R.drawable.ic_baseline_monetization_on_24)
            }else if(it.type == "birthday"){
                binding.Icon.setBackgroundResource(R.drawable.ic_baseline_card_giftcard_24)
            }

            binding.editBack.setOnClickListener { v->
                val action = InfoFragmentDirections.actionInfoFragmentToEditFragment()
                EventBus.getDefault().postSticky(it)
                findNavController().navigate(action)
            }
            binding.completeBack.setOnClickListener { v->
                it.isCompleted = true
                viewModel.updateData(it)
                activity?.finish()
            }
            binding.deleteBack.setOnClickListener { v->
                viewModel.deleteData(it)
                activity?.finish()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            activity?.finish()
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner){
            activity?.finish()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}