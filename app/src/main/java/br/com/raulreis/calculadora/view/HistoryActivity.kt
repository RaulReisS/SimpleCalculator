package br.com.raulreis.calculadora.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.raulreis.calculadora.R
import br.com.raulreis.calculadora.databinding.ActivityHistoryBinding
import br.com.raulreis.calculadora.databinding.ActivityMainBinding
import br.com.raulreis.calculadora.model.HistoryItem
import br.com.raulreis.calculadora.presenter.HistoryPresenter

class HistoryActivity : AppCompatActivity() {


    private lateinit var binding: ActivityHistoryBinding
    private lateinit var  presenter : HistoryPresenter
    private lateinit var historyAdapter : HistoryAdapter
    private val historyList = mutableListOf<HistoryItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        presenter = HistoryPresenter(null, this@HistoryActivity)
        historyAdapter = HistoryAdapter(historyList, this@HistoryActivity)
        binding.recyclerHistory.adapter = historyAdapter
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
        fetchData()
        binding.btnBack.setOnClickListener {
            this@HistoryActivity.finish()
        }
        binding.btnClear.setOnClickListener {
            clearData()
        }

    }

    private fun loadin() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerHistory.visibility = View.GONE
    }


    private fun fetchData() {
        loadin()
        presenter.fetchData()
    }

    private fun clearData() {

        presenter.clearData()
        Toast.makeText(this@HistoryActivity, getString(R.string.cleared), Toast.LENGTH_SHORT).show()
        this.historyList.clear()
        historyAdapter.notifyDataSetChanged()
    }

    fun deleteData(id: Int, position: Int) {
        presenter.delteItem(id)
        historyList.removeAt(position)
        historyAdapter.notifyDataSetChanged()
    }

    fun itemDeleted() {
        Toast.makeText(this@HistoryActivity, getString(R.string.deleted), Toast.LENGTH_SHORT).show()
    }


    fun showHistory(response : List<HistoryItem>) {
        binding.progressBar.visibility = View.GONE
        binding.recyclerHistory.visibility = View.VISIBLE
        if (response.isEmpty()) {
            Toast.makeText(this@HistoryActivity, getString(R.string.empty_history), Toast.LENGTH_SHORT).show()
        }
        this.historyList.clear()
        this.historyList.addAll(response)
        historyAdapter.notifyDataSetChanged()

    }

    fun showError(message: String) {
        Toast.makeText(this@HistoryActivity, "Error: $message", Toast.LENGTH_SHORT).show()
    }
}