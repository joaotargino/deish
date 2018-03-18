package com.moolajoo.deish.network

import com.moolajoo.deish.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by joaopaulotargino on 2018-03-17.
 */
interface ApiService {

    @GET("Store")
    fun getStores(): Observable<List<StoreResponse>>

    @GET("Store")
    fun getStoresList(): Observable<List<Store>>

    @GET("Store/{id}")
    fun getStoreDetail(@Path("id") sort: String): Observable<Store>


    @GET("Store/{id}/products")
    abstract fun getStoreProducts(@Path("id") id: String): Observable<List<Product>>

    //Customer/auth

    @FormUrlEncoded
    @POST("Customer/auth")
    fun login(@Field("email") username: String, @Field("password") password: String)
                                            : Observable<String>

    @POST("Customer/auth")
    fun postLogin(@Query("email") username: String,
                  @Query("password") password: String): Observable<String>


}