package com.moolajoo.deish.network

import com.moolajoo.deish.util.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by joaopaulotargino on 2018-03-17.
 */
object ApiClient {


    fun create(): ApiService {

        val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        return retrofit.create(ApiService::class.java)
    }
}