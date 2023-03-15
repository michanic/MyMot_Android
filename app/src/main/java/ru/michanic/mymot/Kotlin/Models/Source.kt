package ru.michanic.mymot.Kotlin.Models

import android.util.Log
import ru.michanic.mymot.Kotlin.Enums.SourceType

class Source {
    var type: SourceType
        private set
    private var region: String? = "rossiya"
    private var model: String? = null
    private var pMin: Int? = null
    private var pMax: Int? = null
    var page: Int? = null

    constructor(type: SourceType) {
        this.type = type
    }

    constructor(type: SourceType, priceFrom: Int, priceFor: Int, region: Location?) {
        this.type = type
        pMin = priceFrom
        pMax = priceFor
        Log.e("priceFrom", priceFrom.toString())
        Log.e("priceFor", priceFor.toString())
        if (region != null) {
            if (type == SourceType.AVITO) {
                this.region = region.avito
            } else if (type == SourceType.AVITO) {
                this.region = region.autoru
            }
        }
    }

    fun setRegion(region: String?) {
        this.region = region
    }

    fun setpMin(pMin: Int?) {
        this.pMin = pMin
    }

    fun setpMax(pMax: Int?) {
        this.pMax = pMax
    }

    fun updateTypeAndRegion(type: SourceType, region: Location?) {
        this.type = type
        if (region != null) {
            if (type == SourceType.AVITO) {
                this.region = region.avito
            } else if (type == SourceType.AVITO) {
                this.region = region.autoru
            }
        }
    }

    fun setModel(model: String?) {
        this.model = model
    }

    fun incrementPage() {
        page =+ 1
    }

    val feedPath: String?
        get() {
            return when (type) {
                SourceType.AVITO -> {
                    var avitoRequest = ""
                    if (pMin != null) {
                        if (pMin!! > 0) {
                            avitoRequest += "&pmin=$pMin"
                        }
                    }
                    if (pMax != null) {
                        if (pMax!! > 0) {
                            avitoRequest += "&pmax=$pMax"
                        }
                    }
                    if (page != null) {
                        avitoRequest += "&p=$page"
                    }
                    if (avitoRequest.length > 0) {
                        avitoRequest = "?" + avitoRequest.substring(1)
                    }
                    type.domain() + region + "/mototsikly_i_mototehnika/mototsikly" + avitoRequest
                }
                SourceType.AUTO_RU -> {
                    var autoRuRequest = ""
                    if (pMin != null) {
                        if (pMin!! > 0) {
                            autoRuRequest += "&price_from=$pMin"
                        }
                    }
                    if (pMax != null) {
                        if (pMax!! > 0) {
                            autoRuRequest += "&price_to=$pMax"
                        }
                    }
                    if (page != null) {
                        autoRuRequest += "&page_num_offers=$page"
                    }
                    if (autoRuRequest.length > 0) {
                        autoRuRequest = "?" + autoRuRequest.substring(1)
                    }
                    type.domain() + region + "/motorcycle/all/" + autoRuRequest
                }
            }
            return null
        }

    val searchPath: String?
        get() {
            return when (type) {
                SourceType.AVITO -> {
                    val avitoPath = type.domain() + region + "/mototsikly_i_mototehnika/mototsikly"
                    var avitoRequest = "?bt=1"
                    if (pMin != null) {
                        if (pMin!! > 0) {
                            avitoRequest += "&pmin=$pMin"
                        }
                    }
                    if (pMax != null) {
                        if (pMax!! > 0) {
                            avitoRequest += "&pmax=$pMax"
                        }
                    }
                    if (model != null) {
                        avitoRequest += "&q=$model"
                    }
                    if (page != null) {
                        avitoRequest += "&p=$page"
                    }
                    avitoPath + avitoRequest
                }
                SourceType.AUTO_RU -> {
                    val autoRuPath = type.domain() + region + "/motorcycle/" + model + "all/"
                    var autoRuRequest = ""
                    if (pMin != null) {
                        if (pMin!! > 0) {
                            autoRuRequest += "&price_from=$pMin"
                        }
                    }
                    if (pMax != null) {
                        if (pMax!! > 0) {
                            autoRuRequest += "&price_to=$pMax"
                        }
                    }

                    if (page != null) {
                        autoRuRequest += "&page_num_offers=$page"
                    }
                    if (autoRuRequest.length > 0) {
                        autoRuRequest = "?" + autoRuRequest.substring(1)
                    }
                    autoRuPath + autoRuRequest
                }
            }
            return null
        }
}