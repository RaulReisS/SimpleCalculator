package br.com.raulreis.calculadora.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.raulreis.calculadora.R
import br.com.raulreis.calculadora.databinding.ActivityMainBinding
import br.com.raulreis.calculadora.presenter.HistoryPresenter
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.text.format

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: HistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter = HistoryPresenter(this@MainActivity, null)

        with(binding) {
            btnHistory.setOnClickListener {
                val intent = Intent(this@MainActivity, HistoryActivity::class.java)
                startActivity(intent)
            }

            btnPlus.setOnClickListener {
                txvOperation.text = "+"
            }

            btnMinus.setOnClickListener {
                txvOperation.text = "-"
            }

            btnMultiply.setOnClickListener {
                txvOperation.text = "x"
            }

            btnDivide.setOnClickListener {
                txvOperation.text = "÷"
            }

            btnCalculate.setOnClickListener {
                val valueAString = edtValueA.text.toString().replace(",",".").trim()
                val valueBString = edtValueB.text.toString().replace(",",".").trim()

                if (valueAString.isEmpty() || valueBString.isEmpty()) {
                    Toast.makeText(this@MainActivity, getString(R.string.empty_fields), Toast.LENGTH_SHORT).show()
                }
                else {
                    val valueA = valueAString.toDoubleOrNull()
                    val valueB = valueBString.toDoubleOrNull()

                    if (valueA == null || valueB == null) {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.invalid_value),
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                    else {
                        val result : Double = when (txvOperation.text.toString()) {
                            "+" -> valueA + valueB
                            "-" -> valueA - valueB
                            "x" -> valueA * valueB
                            "÷" -> {
                                if (valueB != 0.0) {
                                    valueA / valueB
                                } else {
                                    Toast.makeText(this@MainActivity, getString(R.string.divison_zero), Toast.LENGTH_SHORT).show()
                                    Double.NaN
                                }
                            }
                            else -> Double.NaN
                        }

                        txvResult.text = if (isDecimal(result)) {
                            result.toString()
                        } else {
                            result.toInt().toString()
                        }

                        savingCalculation(
                            valueA = valueA,
                            valueB = valueB,
                            operation = txvOperation.text.toString()[0],
                            result = result,
                            date = Date().toIsoString()
                        )


                    }
                }
            }

        }


    }

    private fun isDecimal(value: Double): Boolean {
        return value % 1 != 0.0
    }

   private fun Date.toIsoString(): String {
        return Instant.ofEpochMilli(this.time)
            .atOffset(ZoneOffset.UTC)
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"))
    }
    
    fun CalculationSaved(id: Int) {
        Toast.makeText(this@MainActivity, getString(R.string.data_stored, id), Toast.LENGTH_LONG).show()
    }
    
    private fun savingCalculation(valueA: Double, valueB: Double, operation: Char, result: Double, date: String) {
        Toast.makeText(this@MainActivity, getString(R.string.saving), Toast.LENGTH_SHORT).show()
        presenter.saveCalculation(valueA, valueB, operation, result, date)
    }


}