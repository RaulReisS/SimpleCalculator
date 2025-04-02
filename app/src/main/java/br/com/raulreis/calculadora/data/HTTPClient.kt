package br.com.raulreis.calculadora.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HTTPClient {

    private const val BASE_URL_STRING = "https://intranet.pekus.com.br/" // No trailing slash here
    private const val API_KEY = "RAUL0304"

    private fun httpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("apikey", API_KEY)
                    .build()
                val request = original.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    fun retrofit(): Retrofit {
        val baseUrlWithSlash = "$BASE_URL_STRING/"
        return Retrofit.Builder()
            .baseUrl(baseUrlWithSlash)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient())
            .build()
    }
}