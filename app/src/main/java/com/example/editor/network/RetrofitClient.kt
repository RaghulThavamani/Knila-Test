package official.app1000green.a1000green.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.URI.create
import java.util.concurrent.TimeUnit


interface RetrofitClient {

    companion object{
        operator fun invoke() : ApiInterface {

            val okkHttpclient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            val BASEURL : String = "https://reqres.in/api/"

            return Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okkHttpclient)
                .build()
                .create(ApiInterface::class.java)
        }


    }


}


