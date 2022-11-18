package com.rxyzent.remainder.ui.details.edit

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.helper.MyBroadcastReciver
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.databinding.DetailFragmentBinding
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EditFragment :DaggerFragment(){

    private val binding by lazy {
        DetailFragmentBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory
    private val viewModel: EditViewModel by viewModels { vmProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    fun onReciveEvent(event: ListData){
        val stickyEvent = EventBus.getDefault().removeStickyEvent(ListData::class.java)
        stickyEvent?.let {
            binding.title.text = it.title
            viewModel.setData(it)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = "Edit reminder"

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        var type = "todo"
        binding.payment.setOnClickListener {
            binding.paymentStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.todoStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            binding.birthdayStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            binding.categoryLine.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.payment))
            type = "payment"
        }
        binding.birthday.setOnClickListener {
            binding.paymentStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            binding.todoStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            binding.birthdayStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.categoryLine.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.birthday))
            type = "birthday"
        }
        binding.todo.setOnClickListener {
            binding.paymentStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            binding.todoStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.birthdayStr.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            binding.categoryLine.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.todo))
            type = "todo"
        }
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        val simpleTimeFormat = SimpleDateFormat("HH:mm")


        val calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        val date = Calendar.getInstance()

        viewModel.listLiveData.observe(viewLifecycleOwner){

            val dateText  = simpleDateFormat.format(it.date)
            val timeText = simpleTimeFormat.format(it.date)

            date.timeInMillis = it.date

            when (type) {
                "todo" -> {
                    binding.todo.performClick()
                }
                "payment" -> {
                    binding.payment.performClick()
                }
                else -> {
                    binding.birthday.performClick()
                }
            }
            binding.Name.setText(it.title)
            binding.description.setText(it.description)
            binding.date.text = dateText
            binding.time.text = timeText
        }

        val timePickerDialog = TimePickerDialog(requireContext(),
            { view, hourOfDay, minute -> binding.time.text = String.format("%d:%d",hourOfDay,minute)
                date.set(Calendar.HOUR_OF_DAY,hourOfDay)
                date.set(Calendar.MINUTE,minute)
            },hour,minute,true)

        val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            binding.date.text = String.format("%d.%d.%d",dayOfMonth,monthOfYear,year)
            date.set(year,monthOfYear,dayOfMonth)
        }, year, month, day)

        binding.time.setOnClickListener {
            timePickerDialog.show()
        }

        binding.date.setOnClickListener {
            datePickerDialog.show()
        }

        binding.saveBtn.setOnClickListener {
            val title = binding.Name.text.toString()
            val description = binding.description.text.toString()

            viewModel.listLiveData.observe(viewLifecycleOwner){
                it.title = title
                it.description = description
                it.date = date.timeInMillis
                it.type = type

                viewModel.update(it)
            }

            canExactAlarmsBeScheduled()
            //val myIntent = Intent(activity, MyService::class.java)
            val myIntent = Intent(activity, MyBroadcastReciver::class.java)
            myIntent.putExtra("title",title)
            myIntent.putExtra("description",description)
            myIntent.putExtra("type",type)
            val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            //val pendingIntent = PendingIntent.getService(activity, 0, myIntent, 0)
            val pendingIntent = PendingIntent.getBroadcast(activity,0,myIntent,0)

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.timeInMillis,
                (1000 * 60 * 10).toLong(),
                pendingIntent)

            activity?.finish()
        }

    }


    private fun canExactAlarmsBeScheduled(): Boolean {
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true // below API it can always be scheduled
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}