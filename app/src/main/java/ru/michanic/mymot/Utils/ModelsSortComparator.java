package ru.michanic.mymot.Utils;

import java.util.Comparator;

import ru.michanic.mymot.Kotlin.Models.Model;

public class ModelsSortComparator implements Comparator<Model> {
    @Override
    public int compare(Model m1, Model m2) {
        return m1.getSort() - m2.getSort();
    }
}
