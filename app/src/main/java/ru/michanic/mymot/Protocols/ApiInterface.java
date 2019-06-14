package ru.michanic.mymot.Protocols;

import com.google.gson.Gson;

import io.realm.RealmList;
import io.realm.RealmObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.michanic.mymot.Models.JsonResult;

public interface ApiInterface {

    @POST("config.php?type=word_exceptions")
    Call<Gson> loadExteptedWords();

    @POST("config.php?type=about")
    Call<JsonResult> loadAboutText();

    @POST("config.php?type=agreement")
    Call<JsonResult> loadAgreementText();


    @POST("catalog.php?type=only_regions")
    Call<JsonResult> loadRegions();

    @POST("catalog.php?type=cities")
    Call<JsonResult> loadRegionCities(@Query("id") int regionId);

    @POST("catalog.php?type=classes")
    Call<JsonResult> loadClasses();

    @POST("catalog.php?type=models")
    Call<JsonResult> loadModels();

    @POST("catalog.php?type=model_details")
    Call<JsonResult> loadModelDetails(@Query("id") int modelId);

}
