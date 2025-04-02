package br.com.raulreis.calculadora.data

import com.google.gson.annotations.SerializedName

data class CalculationRequest (
    @SerializedName("valorA") val valueA: Double,
    @SerializedName("valorB") val valueB: Double,
    @SerializedName("operacao") val operation: Char,
    @SerializedName("resultado") val result: Double,
    @SerializedName("dataCalculo") val date: String,
    @SerializedName("calculadora") val calculator: String = "AndroidApp"
)