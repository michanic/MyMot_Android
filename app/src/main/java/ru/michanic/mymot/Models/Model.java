package ru.michanic.mymot.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Model extends RealmObject {

    @PrimaryKey
    private int id;

    private String code;
    private boolean favourite;
    private int first_year;
    private int last_year;
    private String name;
    private String preview_picture;
    private int sort;

    // связи
    private Category category;
    private Manufacturer manufacturer;

}
