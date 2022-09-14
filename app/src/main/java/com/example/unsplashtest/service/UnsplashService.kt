package com.example.unsplashtest.service

import com.google.gson.JsonElement
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UnsplashService {
    interface LinksListListener
    {
        fun onLinksReceived(list: List<JsonElement>)
    }




    var service : UnsplashServiceAPI? = null;
    lateinit var bearer : String
    private constructor()
    {
        createService()
    }

    companion object {

        private var instance = UnsplashService()
        @JvmField

        var unsplashServiceInstance: UnsplashService = instance

    }

    fun createService()
    {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()

        service = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(UnsplashServiceAPI::class.java)


        bearer = "896d4f52c589547b2134bd75ed48742db637fa51810b49b607e37e46ab2c0043"
    }
    fun getLinks(callback : LinksListListener){
        service!!.getLinks(bearer).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { callback.onLinksReceived(it) },
                {it.printStackTrace()}
            )
    }


}


