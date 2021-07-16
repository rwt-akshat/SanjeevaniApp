package com.rawat.sanjeevaniapp.controller

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rawat.sanjeevaniapp.activity.MainActivity
import com.rawat.sanjeevaniapp.activity.SlotsActivity
import com.rawat.sanjeevaniapp.model.MainObject
import com.rawat.sanjeevaniapp.model.Session
import com.rawat.sanjeevaniapp.service.DataService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MainController {
    private val BASE_URL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/"
    private var retrofit: Retrofit? = null

    private fun getRetrofitInstance(): Retrofit? {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(7,TimeUnit.SECONDS)

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient.build())
                .build()
        }
        return retrofit
    }

    fun getDataByPinCode(context:Context, pincode:String,date: String ):LiveData<MainObject>{
        val liveSessionData = MutableLiveData<MainObject>()

        val service:DataService  = getRetrofitInstance()!!.create(DataService::class.java)
        val call:Call<MainObject> = service.getData(pincode,date)
        call.enqueue(object:Callback<MainObject>{

            override fun onResponse(call: Call<MainObject>, response: Response<MainObject>) {
                val t = response.body()
                liveSessionData.postValue(response.body())
            }
            override fun onFailure(call: Call<MainObject>, t: Throwable) {
                Log.e("Error",t.message.toString())
                Toast.makeText(context,"Cannot connect to the server. Please try again later",Toast.LENGTH_LONG).show()
                (context as SlotsActivity).finish()
            }

        })
        return liveSessionData
        
    }
}