package com.pab.modul9_retrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("lifestyle/")
    fun getNews(): Call<NewsResponse>
}