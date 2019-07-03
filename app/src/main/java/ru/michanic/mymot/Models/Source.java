package ru.michanic.mymot.Models;

import ru.michanic.mymot.Enums.SourceType;

public class Source {

    private SourceType type;

    public String region = "rossiya";
    public Model model;
    public Integer pMin;
    public Integer pMax;
    public Integer page;

    public Source(SourceType type) {
        this.type = type;
    }

    public String getFeedPath() {
        String urlPage = "";

        switch (type) {
            case AVITO:
                if (page != null) {
                    urlPage = "?p=" + String.valueOf(page);
                }
            return type.domain() + region + "/mototsikly_i_mototehnika/mototsikly" + urlPage;

            case AUTO_RU:
                if (page != null) {
                    urlPage = "?page_num_offers=" + String.valueOf(page);
                }
                return type.domain() + region + "/motorcycle/all/" + urlPage;
        }
        return null;
    }

    public String getSearchPath() {


        return null;
    }

}
