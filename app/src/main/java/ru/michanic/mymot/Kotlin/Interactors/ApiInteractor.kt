package ru.michanic.mymot.Kotlin.Interactors

import android.util.Log
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.michanic.mymot.Kotlin.Models.*
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.Protocols.ApiInterface
import ru.michanic.mymot.Kotlin.Protocols.Const
import ru.michanic.mymot.Kotlin.Utils.DataManager

class ApiInteractor {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Const.API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiInterface = retrofit.create(ApiInterface::class.java)
    private val realm: Realm
    private val dataManager = DataManager()

    init {
        Realm.init(MyMotApplication.appContext)
        realm = Realm.getDefaultInstance()
    }

    fun loadData(onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        dataManager.cleanAdverts()
        loadExteptedWords({
            loadAboutText({
                loadRegions({
                    loadVolumes({
                        loadClasses({
                            loadModels({
                                dataManager.assignCategories()
                                onSuccess()
                            }, {
                                onFail()
                            })
                        }, {
                            onFail()
                        })
                    }, {
                        onFail()
                    })
                }, {
                    onFail()
                })
            }, {
                onFail()
            })
        }, {
            onFail()
        })
    }


    private fun loadExteptedWords(onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        Log.e("loadData", "loadExteptedWords")
        apiInterface.loadExteptedWords()?.enqueue(object : Callback<List<String?>?> {
            override fun onResponse(
                call: Call<List<String?>?>,
                response: Response<List<String?>?>,
            ) {
                MyMotApplication.configStorage?.exteptedWords =
                    response.body()?.filterNotNull() ?: emptyList()
                onSuccess()
                Log.e("loadData", "words loaded")
            }

            override fun onFailure(call: Call<List<String?>?>, t: Throwable) {
                onFail()
                Log.e("loadData", t.toString())
            }
        })
    }

    private fun loadAboutText(onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        Log.e("loadData", "loadAboutText")
        apiInterface.loadAboutText()?.enqueue(object : Callback<AppPageText?> {
            override fun onResponse(call: Call<AppPageText?>, response: Response<AppPageText?>) {
                MyMotApplication.configStorage?.aboutText = response.body()?.text ?: ""
                onSuccess()
                Log.e("loadData", "about loaded")
            }

            override fun onFailure(call: Call<AppPageText?>, t: Throwable) {
                onFail()
                Log.e("response", t.toString())
            }
        })
    }

    private fun loadRegions(onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        Log.e("loadData", "loadRegions")
        apiInterface.loadRegions()?.enqueue(object : Callback<List<Location?>?> {
            override fun onResponse(
                call: Call<List<Location?>?>,
                response: Response<List<Location?>?>,
            ) {
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(response.body())
                realm.commitTransaction()
                onSuccess()
                Log.e("loadData", "regions loaded")
            }

            override fun onFailure(call: Call<List<Location?>?>, t: Throwable) {
                onFail()
                Log.e("response", t.toString())
            }
        })
    }

    fun loadRegionCities(region: Location, onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        apiInterface.loadRegionCities(region.id)?.enqueue(object : Callback<List<Location?>?> {
            override fun onResponse(
                call: Call<List<Location?>?>,
                response: Response<List<Location?>?>,
            ) {
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(response.body())
                realm.commitTransaction()
                onSuccess()
            }

            override fun onFailure(call: Call<List<Location?>?>, t: Throwable) {
                onFail()
                Log.e("response", t.toString())
            }
        })
    }

    private fun loadVolumes(onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        Log.e("loadData", "loadVolumes")
        apiInterface.loadVolumes()?.enqueue(object : Callback<List<Volume?>?> {
            override fun onResponse(
                call: Call<List<Volume?>?>,
                response: Response<List<Volume?>?>,
            ) {
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(response.body())
                realm.commitTransaction()
                onSuccess()
                Log.e("loadData", "volumes loaded")
            }

            override fun onFailure(call: Call<List<Volume?>?>, t: Throwable) {
                onFail()
                Log.e("response", t.toString())
            }
        })
    }

    private fun loadClasses(onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        Log.e("loadData", "loadClasses")
        apiInterface.loadClasses()?.enqueue(object : Callback<List<Category?>?> {
            override fun onResponse(
                call: Call<List<Category?>?>,
                response: Response<List<Category?>?>,
            ) {
                realm.beginTransaction()
                realm.copyToRealmOrUpdate(response.body())
                realm.commitTransaction()
                onSuccess()
                Log.e("loadData", "classes loaded")
            }

            override fun onFailure(call: Call<List<Category?>?>, t: Throwable) {
                onFail()
                Log.e("response", t.toString())
            }
        })
    }

    private fun loadModels(onSuccess: (() -> Unit), onFail: (() -> Unit)) {
        Log.e("loadData", "loadModels")
        apiInterface.loadModels()?.enqueue(object : Callback<List<Manufacturer?>?> {
            override fun onResponse(
                call: Call<List<Manufacturer?>?>,
                response: Response<List<Manufacturer?>?>,
            ) {
                val favoriteModels = dataManager.favouriteModelIDs
                val volumes = dataManager.volumes
                realm.beginTransaction()
                for (manufacturer in response.body() ?: emptyList()) {
                    Log.e("manufacturer", manufacturer?.name)
                    for (model in manufacturer?.models ?: continue) {
                        val volumeText = model.volume
                        var volumeVal = 0f
                        try {
                            if (volumeText != null) {
                                volumeVal = volumeText.replace("от ", "").toFloat()
                            }
                        } catch (nfe: NumberFormatException) {
                            println("NumberFormatException: " + nfe.message)
                        }
                        model.setVolume_value(volumeVal)
                        model.volume = "$volumeText куб.см."
                        for (volume in volumes) {
                            if (volume.min.toFloat() <= volumeVal && volumeVal <= volume.max.toFloat()) {
                                model.volume_id = volume.id
                                break
                            }
                        }
                        model.isFavourite = favoriteModels.contains(model.id)
                        model.manufacturer = manufacturer
                        realm.copyToRealmOrUpdate(model)
                    }
                }
                realm.commitTransaction()
                onSuccess()
                Log.e("loadData", "models loaded")
            }

            override fun onFailure(call: Call<List<Manufacturer?>?>, t: Throwable) {
                onFail()
                Log.e("response", t.toString())
            }
        })
    }

    fun loadModelDetails(modelId: Int, onSuccess: ((ModelDetails?) -> Unit), onFail: (() -> Unit)) {

        apiInterface.loadModelDetails(modelId)?.enqueue(object : Callback<ModelDetails?> {
            override fun onResponse(call: Call<ModelDetails?>, response: Response<ModelDetails?>) {
                onSuccess(response.body())
            }

            override fun onFailure(call: Call<ModelDetails?>, t: Throwable) {
                onFail()
                Log.e("response", t.toString())
            }
        })
    }

    fun loadAgreementText(result: ((String?) -> Unit)) {
        apiInterface.loadAgreementText()?.enqueue(object : Callback<AppPageText?> {
            override fun onResponse(call: Call<AppPageText?>, response: Response<AppPageText?>) {
                result(response.body()?.text)
            }

            override fun onFailure(call: Call<AppPageText?>, t: Throwable) {
                result(null)
            }
        })
    }
}