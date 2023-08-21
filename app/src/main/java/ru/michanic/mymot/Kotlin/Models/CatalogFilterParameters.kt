package ru.michanic.mymot.Kotlin.Models

class CatalogFilterParameters(
    cylyndersCount: Int?,
    cylyndersPlacementType: Int?,
    coolingType: Int?,
    driveType: Int?,
    minPower: Int,
    maxPower: Int,
    minSeatHeight: Int,
    maxSeatHeight: Int,
    minWeight: Int,
    maxWeight: Int
) {

    val cylyndersCount: Int?
    val cylyndersPlacementType: Int?
    val coolingType: Int?
    val driveType: Int?

    val minPower: Int
    val maxPower: Int
    val minSeatHeight: Int
    val maxSeatHeight: Int
    val minWeight: Int
    val maxWeight: Int

    init {
        this.cylyndersCount = cylyndersCount
        this.cylyndersPlacementType = cylyndersPlacementType
        this.coolingType = coolingType
        this.driveType = driveType
        this.minPower = minPower
        this.maxPower = maxPower
        this.minSeatHeight = minSeatHeight
        this.maxSeatHeight = maxSeatHeight
        this.minWeight = minWeight
        this.maxWeight = maxWeight
    }


}