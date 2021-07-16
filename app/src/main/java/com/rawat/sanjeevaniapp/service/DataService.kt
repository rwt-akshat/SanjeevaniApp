package com.rawat.sanjeevaniapp.service

import com.rawat.sanjeevaniapp.model.MainObject
import com.rawat.sanjeevaniapp.model.Session
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {

    @GET("findByPin")
    fun getData(@Query("pincode") pinCode: String, @Query("date") date: String): Call<MainObject>


}