package ru.michanic.mymot.Protocols;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.michanic.mymot.Models.AppPageText;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.JsonResult;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Manufacturer;

public interface ApiInterface {

    @POST("config.php?type=word_exceptions")
    Call<List<String>> loadExteptedWords();

    @POST("config.php?type=about")
    Call<AppPageText> loadAboutText();

    @POST("config.php?type=agreement")
    Call<AppPageText> loadAgreementText();


    @POST("catalog.php?type=only_regions")
    Call<List<Location>> loadRegions();

    @POST("catalog.php?type=cities")
    Call<JsonResult> loadRegionCities(@Query("id") int regionId);

    @POST("catalog.php?type=classes")
    Call<List<Category>> loadClasses();

    @POST("catalog.php?type=models")
    Call<List<Manufacturer>> loadModels();

    @POST("catalog.php?type=model_details")
    Call<JsonResult> loadModelDetails(@Query("id") int modelId);

}
