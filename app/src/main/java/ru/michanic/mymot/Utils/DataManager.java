package ru.michanic.mymot.Utils;

import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import ru.michanic.mymot.Models.Advert;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.MyMotApplication;

public class DataManager {

    private Realm realm;

    public DataManager() {
        Realm.init(MyMotApplication.appContext);
        realm = Realm.getDefaultInstance();
    }

    public void assignCategories() {

        HashMap<String, Category> categoriesHashMap = new HashMap<String, Category>();
        RealmResults<Category> categories = realm.where(Category.class).findAll();
        for (Category category : categories) {
            categoriesHashMap.put(String.valueOf(category.getId()), category);
        }

        HashMap<String, Manufacturer> manufacturersHashMap = new HashMap<String, Manufacturer>();
        RealmResults<Manufacturer> manufacturers = realm.where(Manufacturer.class).findAll();
        for (Manufacturer manufacturer : manufacturers) {
            manufacturersHashMap.put(String.valueOf(manufacturer.getId()), manufacturer);
        }


        realm.beginTransaction();
        for (Model model : realm.where(Model.class).findAll()) {
            Category category = categoriesHashMap.get(String.valueOf(model.getClass_id()));
            Manufacturer manufacturer = manufacturersHashMap.get(String.valueOf(model.getM_id()));
            model.setCategory(category);
            model.setManufacturer(manufacturer);
        }
        realm.commitTransaction();


        realm.beginTransaction();
        for (Category category : categories) {
            RealmResults<Model> modelsResults = realm.where(Model.class).equalTo("class_id", category.getId()).findAll();
            RealmList <Model> modelsList = new RealmList<Model>();
            modelsList.addAll(modelsResults.subList(0, modelsResults.size()));
            category.setModels(modelsList);
        }
        realm.commitTransaction();
    }



    public Model getModelById(int id) {
        return realm.where(Model.class).equalTo("id", id).findFirst();
    }

    public List<Integer> getFavouriteModels() {
        List<Model> models = realm.copyFromRealm(realm.where(Model.class).equalTo("favourite", true).findAll());
        List<Integer> modelIds = new ArrayList<Integer>();
        for (Model model : models) {
            modelIds.add(model.getId());
        }
        return modelIds;
    }

    public void setModelFavourite(Model model, boolean favourite) {
        realm.beginTransaction();
        model.setFavourite(favourite);
        realm.commitTransaction();
    }

    public List<Model> searchModelsByName(String searchText) {
        return realm.copyFromRealm(realm.where(Model.class).contains("name", searchText, Case.INSENSITIVE).findAll().sort("sort"));
    }


    public Category getCategoryById(int id) {
        return realm.where(Category.class).equalTo("id", id).findFirst();
    }

    public List<Category> getCategories(boolean notEmpty) {
        if (notEmpty) {
            return realm.copyFromRealm(realm.where(Category.class).isNotEmpty("models").findAll());
        } else {
            return realm.copyFromRealm(realm.where(Category.class).findAll());
        }
    }



    public Manufacturer getManufacturerById(int id) {
        return realm.where(Manufacturer.class).equalTo("id", id).findFirst();
    }

    public List<Manufacturer> getManufacturers(boolean notEmpty) {
        if (notEmpty) {
            return realm.copyFromRealm(realm.where(Manufacturer.class).isNotEmpty("models").findAll());
        } else {
            return realm.copyFromRealm(realm.where(Manufacturer.class).findAll());
        }
    }

    public List<Model> getManufacturerModels(Manufacturer manufacturer, Category category) {
        RealmResults<Model> models = realm.where(Model.class).equalTo("m_id", manufacturer.getId()).equalTo("class_id", category.getId()).findAll().sort("sort");
        //Log.e("getManufacturerModels", String.valueOf(models.size()));
        return realm.copyFromRealm(models);
    }


    public void saveAdverts(Collection<Advert> adverts) {
        realm.beginTransaction();
        realm.insertOrUpdate(adverts);
        realm.commitTransaction();
    }

    public Advert getAdvertById(String id) {
        return realm.where(Advert.class).equalTo("id", id).findFirst();
    }

    public void setAdvertFavourite(Advert advert, boolean favourite) {
        realm.beginTransaction();
        advert.setFavourite(favourite);
        realm.commitTransaction();
    }

    public List<Advert> getFavouriteAdverts() {
        RealmResults<Advert> adverts = realm.where(Advert.class)/*.equalTo("favourite", true)*/.findAll();
        return realm.copyFromRealm(adverts);
    }

    public void cleanAdverts() {
        realm.beginTransaction();
        realm.where(Advert.class).equalTo("favourite", false).findAll().deleteAllFromRealm();
        realm.commitTransaction();
        Log.e("cleanAdverts", "cleanAdverts");
    }

    public Location getRegionById(int regionId) {
        return realm.where(Location.class).equalTo("id", regionId).findFirst();
    }

    public List<Location> getRegions() {
        return getRegionCities(0);
    }

    public List<Location> getRegionCities(int regionId) {
        RealmResults<Location> regions = realm.where(Location.class).equalTo("region_id", regionId).findAll().sort("sort");
        return realm.copyFromRealm(regions);
    }

    public int getRegionCitiesCount(int regionId) {
        RealmResults<Location> regions = realm.where(Location.class).equalTo("region_id", regionId).findAll().sort("sort");
        return regions.size();
    }

}
