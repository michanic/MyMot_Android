package ru.michanic.mymot.Kotlin.Utils

import android.util.Log
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import ru.michanic.mymot.Kotlin.Models.*
import ru.michanic.mymot.Kotlin.MyMotApplication

class DataManager {
    private val realm: Realm

    init {
        Realm.init(MyMotApplication.appContext)
        val configuration = RealmConfiguration.Builder()
            .schemaVersion(1) // меняем на +1 при каждом изменении свойств в БД
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(configuration)

        realm = Realm.getDefaultInstance()
    }

    fun assignCategories() {
        val categoriesHashMap = HashMap<String, Category>()
        val categories = realm.where(
            Category::class.java
        ).findAll()
        for (category in categories) {
            categoriesHashMap[category.id.toString()] = category
        }
        val manufacturersHashMap = HashMap<String, Manufacturer>()
        val manufacturers = realm.where(Manufacturer::class.java).findAll()
        for (manufacturer in manufacturers) {
            manufacturersHashMap[manufacturer.id.toString()] = manufacturer
        }
        realm.beginTransaction()
        for (model in realm.where<Model>(
            Model::class.java
        ).findAll()) {
            val category = categoriesHashMap[model.class_id.toString()]
            val manufacturer = manufacturersHashMap[model.m_id.toString()]
            model.category = category
            model.manufacturer = manufacturer
        }
        realm.commitTransaction()
        realm.beginTransaction()
        for (category in categories) {
            val modelsResults = realm.where(
                Model::class.java
            ).equalTo("class_id", category.id).findAll()
            val modelsList = RealmList<Model>()
            modelsList.addAll(modelsResults.subList(0, modelsResults.size))
            category.models = modelsList
        }
        realm.commitTransaction()
    }

    fun getModelById(id: Int): Model? {
        return realm.where(Model::class.java).equalTo("id", id).findFirst()
    }

    val favouriteModelIDs: List<Int>
        get() {
            val models = realm.copyFromRealm(
                realm.where(
                    Model::class.java
                ).equalTo("isFavourite", true).findAll()
            )
            val modelIds: MutableList<Int> = ArrayList()
            for (model in models) {
                modelIds.add(model.id)
            }
            return modelIds
        }
    val favouriteModels: List<Model>
        get() = realm.copyFromRealm(
            realm.where(
                Model::class.java
            ).equalTo("isFavourite", true).findAll().sort("sort")
        )

    fun setModelFavourite(model: Model, favourite: Boolean) {
        realm.beginTransaction()
        model.isFavourite = favourite
        realm.commitTransaction()
    }

    fun searchModelsByName(searchText: String?): List<Model> {
        return realm.copyFromRealm(
            realm.where(
                Model::class.java
            ).contains("name", searchText, Case.INSENSITIVE).findAll().sort("sort")
        )
    }

    fun getCategoryById(id: Int): Category? {
        return realm.where(Category::class.java).equalTo("id", id).findFirst()
    }

    fun getCategories(notEmpty: Boolean): List<Category> {
        return if (notEmpty) {
            realm.copyFromRealm(
                realm.where(
                    Category::class.java
                ).isNotEmpty("models").findAll()
            )
        } else {
            realm.copyFromRealm(
                realm.where(
                    Category::class.java
                ).findAll()
            )
        }
    }

    fun getManufacturerById(id: Int): Manufacturer? {
        return realm.where(Manufacturer::class.java).equalTo("id", id).findFirst()
    }

    fun getManufacturers(notEmpty: Boolean): List<Manufacturer> {
        return if (notEmpty) {
            realm.copyFromRealm(
                realm.where(Manufacturer::class.java).isNotEmpty("models").findAll()
            )
        } else {
            realm.copyFromRealm(
                realm.where(Manufacturer::class.java).findAll()
            )
        }
    }

    fun getManufacturerModels(manufacturer: Manufacturer?, category: Category): List<Model> {
        val models = realm.where(
            Model::class.java
        ).equalTo("m_id", manufacturer?.id).equalTo("class_id", category.id).findAll().sort("sort")
        return realm.copyFromRealm(models)
    }

    fun getManufacturerModelsOfVolume(manufacturer: Manufacturer, volume: Volume): List<Model> {
        val models = realm.where(
            Model::class.java
        ).equalTo("m_id", manufacturer.id).equalTo("volume_id", volume.id).findAll()
            .sort("volume_value")
        //Log.e("getManufacturerModels", String.valueOf(models.size()));
        return realm.copyFromRealm(models)
    }

    fun saveAdverts(adverts: Collection<Advert?>?) {
        realm.beginTransaction()
        realm.insertOrUpdate(adverts)
        realm.commitTransaction()
    }

    fun getAdvertById(id: String?): Advert? {
        return realm.where(Advert::class.java).equalTo("id", id).findFirst()
    }

    fun setAdvertFavourite(advertId: String?, favourite: Boolean) {
        val advert = getAdvertById(advertId)
        realm.beginTransaction()
        advert?.isFavourite = favourite
        realm.commitTransaction()
    }

    fun setAdvertActive(advertId: String?, active: Boolean) {
        val advert = getAdvertById(advertId)
        realm.beginTransaction()
        advert?.isActive = active
        realm.commitTransaction()
    }

    val favouriteAdverts: List<Advert>
        get() {
            val adverts =
                realm.where(Advert::class.java).equalTo("isFavourite", true).findAll().sort("id")
            return realm.copyFromRealm(adverts)
        }

    fun cleanAdverts() {
        realm.beginTransaction()
        realm.where(Advert::class.java).equalTo("isFavourite", false).findAll().deleteAllFromRealm()
        realm.commitTransaction()
        Log.e("cleanAdverts", "cleanAdverts")
    }

    fun getRegionById(regionId: Int): Location? {
        return realm.where(Location::class.java).equalTo("id", regionId).findFirst()
    }

    val regions: List<Location>
        get() = getRegionCities(0)

    fun getRegionCities(regionId: Int): List<Location> {
        val regions = realm.where(
            Location::class.java
        ).equalTo("region_id", regionId).findAll().sort("sort")
        return realm.copyFromRealm(regions)
    }

    fun getRegionCitiesCount(regionId: Int): Int {
        val regions = realm.where(
            Location::class.java
        ).equalTo("region_id", regionId).findAll().sort("sort")
        return regions.size
    }

    val volumes: List<Volume>
        get() = realm.copyFromRealm(realm.where(Volume::class.java).findAll().sort("sort"))

    fun getVolumeById(id: Int): Volume? {
        return realm.where(Volume::class.java).equalTo("id", id).findFirst()
    }
}