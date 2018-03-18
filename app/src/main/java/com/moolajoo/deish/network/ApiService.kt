package com.moolajoo.deish.network

import com.moolajoo.deish.model.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

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



    @Multipart
    @POST ("Order")
    fun postOrder(
            @Header("Authorization") token : String,
            @Part("id") id: Int, @Part("date") date: String,
            @Part("customerId") customerId: Int, @Part("deliveryAddress") deliveryAddress: String,
            @Part("contact") contact: String, @Part("storeId") storeId: Int,
            @Part("orderItens") orderItens: ArrayList<OrderItem>,
            @Part("total") total: Double, @Part("status") status: String,
            @Part("lastUpdate") lastUpdate: String

    ) :Call<Order>


    @Multipart
    @POST ("Order")
    fun postSimpleOrder(
            @Header("Authorization") token : String,
            @Header("Content-Type") contentType : String,
            @Part("deliveryAddress") deliveryAddress: String,
            @Part("contact") contact: String, @Part("storeId") storeId: Int,
            @Part("orderItens") orderItens: ArrayList<OrderItem>,
            @Part("total") total: Double, @Part("status") status: String
    ) :Call<String>


    @POST ("Order")
    fun postOrderBody(
            @Header("Authorization") token : String,
            @Header("Content-Type") contentType : String,
            @Body order: RequestBody

    ) :Call<Order>


    @GET("Order/customer")
    fun getOrder(@Header("Authorization") token : String,
                 @Header("Content-Type") contentType : String): Observable<List<Order>>
}