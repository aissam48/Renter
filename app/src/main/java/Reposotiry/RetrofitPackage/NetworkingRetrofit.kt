package Reposotiry.RetrofitPackage

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkingRetrofit {

    val okhttp = OkHttpClient.Builder().callTimeout(15000,TimeUnit.MILLISECONDS).build()
    val retrofit = Retrofit.Builder().baseUrl("")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okhttp).build()

}