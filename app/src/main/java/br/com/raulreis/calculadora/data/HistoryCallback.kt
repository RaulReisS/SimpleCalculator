package br.com.raulreis.calculadora.data

import br.com.raulreis.calculadora.model.HistoryItem

interface HistoryCallback {

    fun onPostSuccess(response: Int?)

    fun onGetSuccess(response: List<HistoryItem>?)

    fun onDeleteSuccess(response: Int?)

    fun onClearSuccess(response: Int?)

    fun onError(message: String)

    fun onComplete()
}