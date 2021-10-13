package br.com.fiap.imcapplication

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {

        private lateinit var retrofit: Retrofit
        private val baseUrl = "https://www.celsofurtado.pro.br:8443/imc/"

        private fun getRetrofitInstance() : Retrofit {

            var httpClient = OkHttpClient.Builder()

            if (!::retrofit.isInitialized) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            }

            return retrofit
        }

        fun <S> createService(serviceClass: Class<S>) : S {
            return getRetrofitInstance().create(serviceClass)
        }

    }

}