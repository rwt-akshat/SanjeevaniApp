package com.rawat.sanjeevaniapp.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.rawat.sanjeevaniapp.R
import com.rawat.sanjeevaniapp.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var pinCodeEditTextInputLayout: TextInputLayout
    private lateinit var calendar: Calendar
    private lateinit var datePicker:TextInputLayout
    private lateinit var pinCodeBtn:Button
    private lateinit var radioAgeGrp:RadioGroup
    private lateinit var radioDoseGrp:RadioGroup
    private lateinit var radioAgeButton1:RadioButton
    private lateinit var radioAgeButton2:RadioButton
    private lateinit var radioDoseButton1:RadioButton
    private lateinit var radioDoseButton2:RadioButton
    private lateinit var statsText:TextView
    private lateinit var welcomeCard:CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        datePicker =  binding.dateTextField
        pinCodeEditTextInputLayout = binding.textField

        pinCodeBtn = binding.pinCodeBtn
        radioAgeGrp = binding.radioGroup
        radioDoseGrp = binding.radioGroup2
        radioAgeButton1 = binding.radioAgeButton1
        radioAgeButton2 = binding.radioAgeButton2
        radioDoseButton1 = binding.radioDoseButton1
        radioDoseButton2 = binding.radioDoseButton2
        statsText = binding.statisticText
        welcomeCard = binding.welcomeCard

        val name = getSharedPreferences("NameSharedPref", Context.MODE_PRIVATE).getString("name","")
        statsText.text = "Hey! ${name}, Want to see the statistics of vaccination? Click Here."

        welcomeCard.setOnClickListener {
            val uri: Uri =
                Uri.parse("https://dashboard.cowin.gov.in/")

            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        calendar = Calendar.getInstance()

        loadCalendar()
        validation()

    }



    private fun validation(){

        pinCodeBtn.setOnClickListener {

            val pinCode = pinCodeEditTextInputLayout.editText!!.text.toString()
            val date = datePicker.editText!!.text.toString()
            var age=0
            var dose = 0

                if(radioAgeButton1.isChecked){
                    age = 18
                }else if(radioAgeButton2.isChecked){
                    age = 45
                }


                if(radioDoseButton1.isChecked){
                    dose = 1
                }else if(radioDoseButton2.isChecked){
                    dose = 2

            }

           // pinCodeEditTextInputLayout.error = null
            //datePicker

            when {
                pinCode.isEmpty() -> {
                    pinCodeEditTextInputLayout.error = "Please enter pincode before searching"
                    datePicker.error=null
                }
                pinCode.length <6 -> {
                    pinCodeEditTextInputLayout.error = "Pincode should be of length 6"
                    datePicker.error=null
                }
                date.isEmpty() ->{
                    datePicker.error = "Please enter date"
                    pinCodeEditTextInputLayout.error=null
                }
                else -> {
                    pinCodeEditTextInputLayout.error=null
                    datePicker.error=null
                    val i = Intent(this, SlotsActivity::class.java)
                    i.putExtra("pinCode",pinCode)
                    i.putExtra("date", date)
                    i.putExtra("age", age)
                    i.putExtra("dose", dose)
                    startActivity(i)
                }
            }
        }
    }



    private fun loadCalendar() {


        val date = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->


            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd-MM-yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)

           datePicker.editText!!.setText(sdf.format(calendar.time))
        }

        datePicker.editText!!.setOnClickListener {
            val datePicker = DatePickerDialog(
                this,
                date,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker.show()

        }
    }
}