package ru.michanic.mymot.Models;

import android.util.Log;

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

    public Source(SourceType type, int priceFrom, int priceFor, Location region) {
        this.type = type;
        this.pMin = priceFrom;
        this.pMax = priceFor;

        Log.e("priceFrom", String.valueOf(priceFrom));
        Log.e("priceFor", String.valueOf(priceFor));

        if (region != null) {
            if (type == SourceType.AVITO) {
                this.region = region.getAvito();
            } else if (type == SourceType.AVITO) {
                this.region = region.getAutoru();
            }
        }
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

    public void updateTypeAndRegion(SourceType type, Location region) {
        this.type = type;
        if (region != null) {
            if (type == SourceType.AVITO) {
                this.region = region.getAvito();
            } else if (type == SourceType.AVITO) {
                this.region = region.getAutoru();
            }
        }
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPage() {
        return page;
    }

    public void incrementPage() {
        this.page += 1;
    }

    public String getFeedPath() {

        switch (type) {
            case AVITO:
                String avitoRequest = "";
                if (pMin != null) {
                    if (pMin > 0) {
                        avitoRequest += "&pmin=" + pMin;
                    }
                }
                if (pMax != null) {
                    if (pMax > 0) {
                        avitoRequest += "&pmax=" + pMax;
                    }
                }
                if (page != null) {
                    avitoRequest += "&p=" + page;
                }
                if (avitoRequest.length() > 0) {
                    avitoRequest = "?" + avitoRequest.substring(1);
                }

            return type.domain() + region + "/mototsikly_i_mototehnika/mototsikly" + avitoRequest;

            case AUTO_RU:
                String autoRuRequest = "";
                if (pMin != null) {
                    if (pMin > 0) {
                        autoRuRequest += "&price_from=" + pMin;
                    }
                }
                if (pMax != null) {
                    if (pMax > 0) {
                        autoRuRequest += "&price_to=" + pMax;
                    }
                }
                if (page != null) {
                    autoRuRequest += "&page_num_offers=" + page;
                }
                if (autoRuRequest.length() > 0) {
                    autoRuRequest = "?" + autoRuRequest.substring(1);
                }
                return type.domain() + region + "/motorcycle/all/" + autoRuRequest;
        }
        return null;
    }

    public String getSearchPath() {

        switch (type) {
            case AVITO:
                String avitoPath = type.domain() + region + "/mototsikly_i_mototehnika/mototsikly";
                String avitoRequest = "?bt=1";
                if (pMin != null) {
                    if (pMin > 0) {
                        avitoRequest += "&pmin=" + pMin;
                    }
                }
                if (pMax != null) {
                    if (pMax > 0) {
                        avitoRequest += "&pmax=" + pMax;
                    }
                }
                if (model != null) {
                    avitoRequest += "&q=" + model;
                }
                if (page != null) {
                    avitoRequest += "&p=" + page;
                }
                return avitoPath + avitoRequest;

            case AUTO_RU:
                String autoRuPath = type.domain() + region + "/motorcycle/" + model + "all/";
                String autoRuRequest = "";
                if (pMin != null) {
                    if (pMin > 0) {
                        autoRuRequest += "&price_from=" + pMin;
                    }
                }
                if (pMax != null) {
                    if (pMax > 0) {
                        autoRuRequest += "&price_to=" + pMax;
                    }
                }
                /*if (model != null) {
                    autoRuRequest += "&mark-model-nameplate=" + model;
                }*/
                if (page != null) {
                    autoRuRequest += "&page_num_offers=" + page;
                }
                if (autoRuRequest.length() > 0) {
                    autoRuRequest = "?" + autoRuRequest.substring(1);
                }
                return autoRuPath + autoRuRequest;
        }


        return null;
    }

}
