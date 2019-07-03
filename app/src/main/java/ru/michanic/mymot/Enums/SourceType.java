package ru.michanic.mymot.Enums;

public enum SourceType {
    AVITO,
    AUTO_RU;

    public String domain() {
        switch (this) {
            case AVITO:
                return "https://www.avito.ru/";
            case AUTO_RU:
                return "https://auto.ru/";
        }
        return null;
    }

    public String mobileDomain() {
        switch (this) {
            case AVITO:
                return "https://m.avito.ru/";
            case AUTO_RU:
                return "https://m.auto.ru/";
        }
        return null;
    }

    public String itemSelector() {
        switch (this) {
            case AVITO:
                return ".js-catalog-item-enum.item-with-contact";
            case AUTO_RU:
                return ".listing-item.stat-publicapi_type_listing";
        }
        return null;
    }

}
