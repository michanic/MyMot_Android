package ru.michanic.mymot.Kotlin.Protocols

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query
import ru.michanic.mymot.Kotlin.Models.*

interface ApiInterface {
    @POST("config.php?type=word_exceptions")
    fun loadExteptedWords(): Call<List<String?>?>?

    @POST("config.php?type=about")
    fun loadAboutText(): Call<AppPageText?>?

    @POST("config.php?type=agreement")
    fun loadAgreementText(): Call<AppPageText?>?

    @POST("catalog1.php?type=only_regions")
    fun loadRegions(): Call<List<Location?>?>?

    @POST("catalog1.php?type=cities")
    fun loadRegionCities(@Query("id") regionId: Int): Call<List<Location?>?>?

    @POST("catalog1.php?type=volumes")
    fun loadVolumes(): Call<List<Volume?>?>?

    @POST("catalog1.php?type=classes")
    fun loadClasses(): Call<List<Category?>?>?

    @POST("catalog1.php?type=models")
    fun loadModels(): Call<List<Manufacturer?>?>?

    @POST("catalog1.php?type=model_details")
    fun loadModelDetails(@Query("id") modelId: Int): Call<ModelDetails?>?

    @POST("catalog1.php?type=property_enums")
    fun loadPropertyEnums(@Query("id") modelId: Int): Call<Model>
}