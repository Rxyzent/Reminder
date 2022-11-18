package com.rxyzent.remainder.ui.details.detail

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.getSystemService
import androidx.fragment.app.viewModels
import com.rxyzent.remainder.R
import com.rxyzent.remainder.core.helper.MyBroadcastReciver
import com.rxyzent.remainder.core.helper.ViewModelProviderFactory
import com.rxyzent.remainder.core.model.db.ListData
import com.rxyzent.remainder.core.service.MyService
import com.rxyzent.remainder.databinding.DetailFragmentBinding
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class DetailFragment: DaggerFragment() {

    private val binding by lazy {
        DetailFragmentBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var vmProviderFactory: ViewModelProviderFactory
    private val viewModel: DetailViewModel by viewModels { vmProviderFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.back.setOnClickListener {
            activity?.finish()
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

        val currentDate = simpleDateFormat.format(System.currentTimeMillis())
        val currentTime = simpleTimeFormat.format(System.currentTimeMillis())


        binding.date.text = currentDate
        binding.time.text = currentTime


        val calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]

        val date = Calendar.getInstance()

        date.timeInMillis = System.currentTimeMillis()


        val timePickerDialog = TimePickerDialog(requireContext(),
            { view, hourOfDay, minute -> binding.time.text = String.format("%02d:%02d",hourOfDay,minute)
                date.set(Calendar.HOUR_OF_DAY,hourOfDay)
                date.set(Calendar.MINUTE,minute)
            },hour,minute,true)

        val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            binding.date.text = String.format("%02d.%02d.%d",dayOfMonth,monthOfYear,year)
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

            if(title == ""){
                Toast.makeText(requireContext(), "Please fill in the title", Toast.LENGTH_SHORT).show()
            }else if(description == ""){
                Toast.makeText(requireContext(), "Please fill the description", Toast.LENGTH_SHORT).show()
            }else if(date.timeInMillis <= System.currentTimeMillis()){
                Toast.makeText(requireContext(), "Please select a future time", Toast.LENGTH_SHORT).show()
            }else{
            
            viewModel.addData(ListData(title,description,date.timeInMillis,type,false))

            canExactAlarmsBeScheduled()
            //val myIntent = Intent(activity, MyService::class.java)
            val myIntent = Intent(activity, MyBroadcastReciver::class.java)
            myIntent.putExtra("title",title)
            myIntent.putExtra("description",description)
            myIntent.putExtra("type",type)
            val alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
            //val pendingIntent = PendingIntent.getService(activity, 0, myIntent, 0)
            val pendingIntent = PendingIntent.getBroadcast(activity,0,myIntent,0)

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, date.timeInMillis,
                (1000 * 60 * 10).toLong(),
                pendingIntent)

            activity?.finish()
            }
        }

    }

    private fun canExactAlarmsBeScheduled(): Boolean {
        val alarmManager = activity?.getSystemService(ALARM_SERVICE) as AlarmManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true // below API it can always be scheduled
        }
    }
}