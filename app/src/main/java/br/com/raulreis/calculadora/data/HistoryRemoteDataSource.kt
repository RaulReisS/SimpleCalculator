package br.com.raulreis.calculadora.data


import br.com.raulreis.calculadora.model.HistoryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryRemoteDataSource {

    fun postCalculation(valueA: Double, valueB: Double, operation: Char, result: Double, date: String, callback: HistoryCallback) {

        HTTPClient.retrofit()
            .create(HistoryAPI::class.java)
            .postCalculation(
                request = CalculationRequest(valueA, valueB, operation, result, date)
            )
            .enqueue(object : Callback<Int> {


                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        val id = response.body()

                        if (id != null) {
                            callback.onPostSuccess(id)
                        }
                        else {
                            callback.onError("Erro ao salvar o cálculo")
                        }

                    }
                    else {
                        val error = response.errorBody()?.string()
                        callback.onError(error ?: "Erro desconhecido")
                    }
                    callback.onComplete()
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {
                    callback.onError(t.message ?: "Erro interno")
                    callback.onComplete()
                }

            })


    }

    fun getHistory(callback: HistoryCallback) {
        HTTPClient.retrofit()
            .create(HistoryAPI::class.java)
            .getHistory()
            .enqueue(object : Callback<List<HistoryItem>> {
                override fun onResponse(
                    call: Call<List<HistoryItem>>,
                    response: Response<List<HistoryItem>>,
                ) {
                    if (response.isSuccessful) {
                        val history = response.body()
                        callback.onGetSuccess(history ?: emptyList())
                    }
                    else {
                        val error = response.errorBody()?.toString()
                        callback.onError(error ?: "Erro desconhecido")
                    }
                    callback.onComplete()
                }

                override fun onFailure(call: Call<List<HistoryItem>>, t: Throwable) {
                    callback.onError(t.message ?: "Erro interno")
                    callback.onComplete()
                }

            })
    }

    fun deleteItem(id : Int, callback: HistoryCallback) {
        HTTPClient.retrofit()
            .create(HistoryAPI::class.java)
            .deleteHistory(id)
            .enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        callback.onDeleteSuccess(null)
                    }
                    else {
                        val error = response.errorBody()?.toString()
                        callback.onError("Erro desconhecido")
                    }
                    callback.onComplete()
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {

                    callback.onComplete()
                }
            })
    }

    fun getClean(callback: HistoryCallback) {
        HTTPClient.retrofit()
            .create(HistoryAPI::class.java)
            .clearData()
            .enqueue(object : Callback<Int> {
                override fun onResponse(call: Call<Int>, response: Response<Int>) {
                    if (response.isSuccessful) {
                        callback.onClearSuccess(null)
                    }
                    else {
                        val error = response.errorBody()?.toString()
                        callback.onError("Erro desconhecido")
                    }
                    callback.onComplete()
                }

                override fun onFailure(call: Call<Int>, t: Throwable) {

                    callback.onComplete()
                }
            })
    }
}