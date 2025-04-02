package br.com.raulreis.calculadora.data

import br.com.raulreis.calculadora.model.HistoryItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface HistoryAPI {

    @Headers(
        "Accept: text/plain",
        "Content-Type: application/json"
    )

    @POST("/calcapi/api/Calculadora")
    fun postCalculation(
        @Body request: CalculationRequest
    ): Call<Int>

    @GET("/calcapi/api/Calculadora")
    fun getHistory(
    ) : Call<List<HistoryItem>>

    @DELETE("/calcapi/api/Calculadora")
    fun deleteHistory(
        @Query("id") id: Int
    ) : Call<Int>


    @GET("/calcapi/api/Calculadora/LimpaContas")
    fun clearData(
    ) : Call<Int>
}