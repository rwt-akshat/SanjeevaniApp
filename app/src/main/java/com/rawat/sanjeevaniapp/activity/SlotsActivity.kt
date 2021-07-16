package com.rawat.sanjeevaniapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.rawat.sanjeevaniapp.R
import com.rawat.sanjeevaniapp.adapter.RecyclerAdapter
import com.rawat.sanjeevaniapp.controller.MainController
import com.rawat.sanjeevaniapp.databinding.ActivitySlotsBinding
import com.rawat.sanjeevaniapp.databinding.SampleBinding
import com.rawat.sanjeevaniapp.model.Session

class SlotsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var emptyCardView: CardView
    private lateinit var goBackBtn:Button
    private lateinit var progressBar: CardView
    private lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivitySlotsBinding = DataBindingUtil.setContentView(this,R.layout.activity_slots)
        recyclerView = binding.recyclerView
        emptyCardView = binding.emptyCard
        goBackBtn = binding.goBackBtn
        progressBar = binding.progressBar
        textView = binding.textView1

        recyclerView.setHasFixedSize(true)

        val pinCode = intent.getStringExtra("pinCode")
        val date = intent.getStringExtra("date")
        val age = intent.getIntExtra("age",0)
        val dose = intent.getIntExtra("dose",0)

        loadRecyclerView(pinCode!!,date!!,age,dose)

        goBackBtn.setOnClickListener{
            finish()
        }
    }
    private fun loadRecyclerView(pinCode:String,date:String,age:Int,dose:Int){
        Log.e("AGE AND DOSE","${age} ${dose}")
        val arrayList:ArrayList<Session> = ArrayList()
        val mainController = MainController()
        mainController.getDataByPinCode(this,pinCode,date).observe(this, Observer {

            if(it.sessions.isEmpty()){
                emptyCardView.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }else{

                textView.text = "Available slots in ${it.sessions[0].district_name}, ${ it.sessions[0].state_name}"

                for (i in it.sessions){
                    if(i.min_age_limit == age){
                        if(dose == 1 && i.available_capacity_dose1 !=0){
                            arrayList.add(i)
                        }else if(dose == 2 && i.available_capacity_dose2 != 0){
                            arrayList.add(i)
                        }

                    }
                }
                if(arrayList.isEmpty()){
                    emptyCardView.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                }else{
                    progressBar.visibility = View.GONE
                    recyclerAdapter = RecyclerAdapter(this,arrayList,dose)
                    recyclerView.adapter = recyclerAdapter
                }

            }
        })
        arrayList.clear()
    }
}