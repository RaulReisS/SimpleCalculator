package br.com.raulreis.calculadora.model

import com.google.gson.annotations.SerializedName

data class HistoryItem(
    @SerializedName("id") val id: Int,
    @SerializedName("valorA") val valueA: Double,
    @SerializedName("valorB") val valueB: Double,
    @SerializedName("operacao") val operation: Char,
    @SerializedName("resultado") val result: Double,
    @SerializedName("dataCalculo") val date: String
)