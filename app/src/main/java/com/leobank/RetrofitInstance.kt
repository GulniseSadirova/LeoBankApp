package com.leobank

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


    class RetrofitInstance {
        companion object {
            var token = ""
            private var retrofit: Retrofit? = null

            fun getInstance(): Retrofit {
                if (retrofit == null) {
                    val client = OkHttpClient.Builder()
                        .build()
                    retrofit = Retrofit.Builder()
                        .baseUrl("")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()
                }
                return retrofit!!
            }
        }
    }
