package com.leobank

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ItemApi {
    @GET("products/{id}")
    suspend fun getSingleProduct(
        @Path("id") id: Int
    ): Response<ItemDetail>
}