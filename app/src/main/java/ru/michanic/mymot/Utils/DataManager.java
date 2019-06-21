package ru.michanic.mymot.Utils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.MyMotApplication;

public class DataManager {

    private Realm realm;

    public DataManager() {
        Realm.init(MyMotApplication.appContext);
        realm = Realm.getDefaultInstance();
    }

    public Category getCategoryById(int id) {
        return realm.where(Category.class).equalTo("id", id).findFirst();
    }

    public List<Category> getCategories() {
        return realm.copyFromRealm(realm.where(Category.class).findAll());
    }



    public Manufacturer getManufacturerById(int id) {
        return realm.where(Manufacturer.class).equalTo("id", id).findFirst();
    }

    public List<Manufacturer> getManufacturers() {
        return realm.copyFromRealm(realm.where(Manufacturer.class).findAll());
    }

}
