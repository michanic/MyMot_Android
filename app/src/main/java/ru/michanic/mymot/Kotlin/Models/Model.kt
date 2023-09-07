package ru.michanic.mymot.Kotlin.Models

import android.util.Log
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.Locale

open class Model : RealmObject() {
    @PrimaryKey
    var id = 0
    private var code: String? = null
    var class_id = 0
    var m_id = 0
    var isFavourite = false
    private var first_year = 0
    private var last_year = 0
    var name: String? = null
    var preview_picture: String? = null
    var sort = 0
    var volume: String? = null
    private var volume_value = 0f
    var volume_id = 0

    var cylynders_count: Int = 0
    var cylynders_placement_type: Int = 0
    var cooling: Int = 0
    var power: String? = null
    var drive_type: Int = 0
    var seat_height: String? = null
    var wet_weight: String? = null


    var category: Category? = null
    var manufacturer: Manufacturer? = null
    fun setVolume_value(volume_value: Float) {
        this.volume_value = volume_value
    }

    val years: String
        get() = first_year.toString() + " - " + if (last_year == 0) "настоящее время" else last_year
    val avitoSearchName: String
        get() = manufacturer?.name?.lowercase(Locale.getDefault()) + "+" + name?.replace(
            " ", "+"
        )?.lowercase(
            Locale.getDefault()
        )

    val autoruSearchName: String
        get() = manufacturer?.code + "/" + code + "/"

    fun minMaxPower(): Pair<Double?, Double?> {
        val powerString = power ?: ""
        var powers: MutableList<Double> = mutableListOf()
        powerString.split("#").forEach {
            val powerString = it.split("|").first().replace(',', '.')
            if (powerString.isNotEmpty()) {
                powers.add(powerString.toDouble())
            } else {
                println("NEED POWER VALUE: $name: $powerString")
            }
        }
        return Pair(powers.minOrNull(), powers.maxOrNull())
    }

    fun minMaxSeatHeight(): Pair<Double?, Double?> {
        val seatHeightString = seat_height ?: ""
        var seatHeight: MutableList<Double> = mutableListOf()
        seatHeightString.split("#").forEach {
            val seatHeightString = it.split("|").first().replace(',', '.')
            if (seatHeightString.isNotEmpty()) {
                seatHeight.add(seatHeightString.toDouble())
            } else {
                println("NEED SEAT HEIGHT VALUE: $name: $seatHeightString")
            }
        }
        return Pair(seatHeight.minOrNull(), seatHeight.maxOrNull())
    }

    fun minMaxWetWeight(): Pair<Double?, Double?> {
        val wetWeightString = wet_weight ?: ""
        var wetWeight: MutableList<Double> = mutableListOf()
        wetWeightString.split("#").forEach {
            val seatHeightString = it.split("|").first().replace(',', '.')
            if (seatHeightString.isNotEmpty()) {
                wetWeight.add(seatHeightString.toDouble())
            } else {
                println("NEED WET WEIGHT VALUE: $name: $wetWeightString")
            }
        }
        return Pair(wetWeight.minOrNull(), wetWeight.maxOrNull())
    }


    fun havePower(min: Int, max: Int): Boolean {
        val powerString = power ?: ""
        powerString.split("#").forEach { // разбиваем на строки варианты мощностей
            val powerString = it.split("|").first()
                .replace(',', '.') // разбиваем на подстроки и берем само значение
            if (powerString.isNotEmpty()) { // проверяем чтобы было не пустое, иначе выводим в лог, чтобы было видно где забыли добавить
                val powerValue = powerString.toDouble()
                if (powerValue >= min.toDouble() && powerValue <= max.toDouble()) { // проверяем чтобы оказалось в нужном диапазоне
                    return true
                }
            } else {
                println("NEED POWER VALUE: $name: $powerString")
            }
        }
        return false
    }

    fun haveSeatHeight(min: Int, max: Int): Boolean {
        val seatHeightString = seat_height ?: ""
        seatHeightString.split("#").forEach {
            val seatHeightString = it.split("|").first().replace(',', '.')
            if (seatHeightString.isNotEmpty()) {
                val seatHeightValue = seatHeightString.toDouble()
                if (seatHeightValue >= min.toDouble() && seatHeightValue <= max.toDouble()) {
                    Log.i("seatHeightValue", "$min + $max")
                    return true
                }
            } else {
                println("NEED SEAT HEIGHT VALUE: $name: $seatHeightString")
            }
        }
        return false
    }

    fun haveWeight(min: Int, max: Int): Boolean {
        val weightString = wet_weight ?: ""
        weightString.split("#").forEach() {
            val weightString = it.split("|").first().replace(',', '.')
            if (weightString.isNotEmpty()) {
                val weightValue = weightString.toDouble()
                if (weightValue >= min.toDouble() && weightValue <= max.toDouble()) {
                    Log.i("weightValue", "$min + $max")
                    return true
                }
            } else {
                println("NEED WET WEIGHT VALUE: $name: $weightString")
            }
        }
        return false
    }
}