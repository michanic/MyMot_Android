package ru.michanic.mymot.Models;

import ru.michanic.mymot.Enums.SourceType;

public class Source {

    private SourceType type;

    private String region = "rossiya";
    private String model;
    private Integer pMin;
    private Integer pMax;
    private Integer page;

    public Source(SourceType type) {
        this.type = type;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setpMin(Integer pMin) {
        this.pMin = pMin;
    }

    public void setpMax(Integer pMax) {
        this.pMax = pMax;
    }

    public SourceType getType() {
        return type;
    }

    public void setType(SourceType type) {
        this.type = type;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void incrementPage() {
        this.page += 1;
    }

    public String getFeedPath() {
        String urlPage = "";

        switch (type) {
            case AVITO:
                if (page != null) {
                    urlPage = "?p=" + page;
                }
            return type.domain() + region + "/mototsikly_i_mototehnika/mototsikly" + urlPage;

            case AUTO_RU:
                if (page != null) {
                    urlPage = "?page_num_offers=" + page;
                }
                return type.domain() + region + "/motorcycle/all/" + urlPage;
        }
        return null;
    }

    public String getSearchPath() {

        switch (type) {
            case AVITO:
                String avitoPath = type.domain() + region + "/mototsikly_i_mototehnika/mototsikly";
                String avitoRequest = "?bt=1";
                if (pMin != null) {
                    avitoRequest += "&pmin=" + pMin;
                }
                if (pMax != null) {
                    avitoRequest += "&pmax=" + pMax;
                }
                if (model != null) {
                    avitoRequest += "&q=" + model;
                }
                if (page != null) {
                    avitoRequest = "&p=" + page;
                }
                return avitoPath + avitoRequest;

            case AUTO_RU:
                String autoRuPath = type.domain() + region + "/motorcycle/all/";
                String autoRuRequest = "";
                if (pMin != null) {
                    autoRuRequest += "&price_from=" + pMin;
                }
                if (pMax != null) {
                    autoRuRequest += "&price_to=" + pMax;
                }
                if (model != null) {
                    autoRuRequest += "&mark-model-nameplate=" + model;
                }
                if (page != null) {
                    autoRuRequest = "&page_num_offers=" + page;
                }
                if (autoRuRequest.length() > 0) {
                    autoRuRequest = "?" + autoRuRequest.substring(1);
                }
                return autoRuPath + autoRuRequest;
        }


        return null;
    }

}
