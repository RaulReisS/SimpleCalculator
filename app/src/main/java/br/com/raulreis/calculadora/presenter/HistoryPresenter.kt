package br.com.raulreis.calculadora.presenter


import android.util.Log
import br.com.raulreis.calculadora.data.HistoryCallback
import br.com.raulreis.calculadora.data.HistoryRemoteDataSource
import br.com.raulreis.calculadora.model.HistoryItem
import br.com.raulreis.calculadora.view.HistoryActivity
import br.com.raulreis.calculadora.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryPresenter(
    private val viewMain: MainActivity?,
    private val viewHistory: HistoryActivity?,
    private val dataSource: HistoryRemoteDataSource = HistoryRemoteDataSource(),
) : HistoryCallback {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun saveCalculation(
        valueA: Double,
        valueB: Double,
        operation: Char,
        result: Double,
        date: String,
    ) {
    dataSource.postCalculation(valueA, valueB, operation, result, date, this@HistoryPresenter)
    }

    fun fetchData() {
        dataSource.getHistory(this@HistoryPresenter)
    }

    fun delteItem(id: Int) {
        dataSource.deleteItem(id, this@HistoryPresenter)
    }

    fun clearData() {
        dataSource.getClean(this@HistoryPresenter)
    }

    override fun onPostSuccess(response: Int?) {
        viewMain?.CalculationSaved(response ?: -1)
    }

    override fun onGetSuccess(response: List<HistoryItem>?) {

        viewHistory?.showHistory(response ?: emptyList())
    }

    override fun onDeleteSuccess(response: Int?) {
        viewHistory?.itemDeleted()
    }

    override fun onClearSuccess(response: Int?) {
        // Não necessário neste contexto
    }

    override fun onError(message: String) {
        viewHistory?.showError(message)
    }

    override fun onComplete() {
        // Não necessário neste contexto
    }
}