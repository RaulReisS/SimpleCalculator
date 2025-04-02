package br.com.raulreis.calculadora.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.calculadora.R
import br.com.raulreis.calculadora.model.HistoryItem

class HistoryAdapter (
    private val historyList: List<HistoryItem>,
    private val context: HistoryActivity
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemPosition: Int) {
            val item = historyList[itemPosition]
            with(itemView) {
                findViewById<TextView>(R.id.txv_id).text = item.id.toString()
                findViewById<TextView>(R.id.txv_valueA).text = if (isDecimal(item.valueA)) {
                    String.format("%.2f", item.valueA)
                }
                else item.valueA.toInt().toString()
                findViewById<TextView>(R.id.txv_valueB).text = if (isDecimal(item.valueB)) {
                    String.format("%.2f", item.valueB)
                }
                else item.valueB.toInt().toString()
                findViewById<TextView>(R.id.txv_operation).text = item.operation.toString()
                findViewById<TextView>(R.id.txv_result).text = if (isDecimal(item.result)) {
                    String.format("%.2f", item.result)
                }
                else item.result.toInt().toString()

                findViewById<TextView>(R.id.txv_date).text = item.date
            }

            itemView.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(context.getString(R.string.delete_title))
                    .setMessage("${item.valueA} ${item.operation} ${item.valueB} = ${item.result}")
                    .setPositiveButton(context.getString(R.string.yes)) {_, _ ->
                        context.deleteData(item.id, position)
                    }
                    .setNegativeButton(context.getString(R.string.no), null)
                    .setCancelable(true)
                    .create()
                    .show()
            }

        }

        private fun isDecimal(value: Double): Boolean {
            return value % 1 != 0.0
        }

    }
}