package com.example.unsplashtest.service

import com.google.gson.JsonArray
import com.google.gson.JsonElement

import retrofit2.http.*
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Call
import org.json.JSONArray
import retrofit2.Response

interface UnsplashServiceAPI {
    @GET("photos/")
    fun getLinks(@Query("client_id") clientID: String)  : Observable<List<JsonElement>>




}