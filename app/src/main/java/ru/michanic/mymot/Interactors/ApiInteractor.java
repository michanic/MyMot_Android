package ru.michanic.mymot.Interactors;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.michanic.mymot.Models.JsonResult;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.ApiInterface;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.Protocols.LoadingInterface;
import ru.michanic.mymot.Protocols.NoConnectionRepeatInterface;

public class ApiInteractor {

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(Const.API_URL)
            .build();
    private ApiInterface apiInterface = retrofit.create(ApiInterface.class);


    public void loadData(final LoadingInterface loadingInterface) {


        loadExteptedWords(new LoadingInterface() {
            @Override
            public void onLoaded() {

                loadingInterface.onLoaded();
            }
        });

        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //loadingInterface.onLoaded();


            }
        }, 2000);*/

    }

    private void loadExteptedWords(final LoadingInterface loadingInterface) {

        Log.e("response", "loadExteptedWords");

        //Log.e("response", response.toString());
        //Log.e("response", t.toString());

        apiInterface.loadExteptedWords().enqueue(new Callback<Gson>() {
            @Override
            public void onResponse(Call<Gson> call, Response<Gson> response) {
                Log.e("response", response.toString());
            }

            @Override
            public void onFailure(Call<Gson> call, Throwable t) {
                Log.e("response", t.toString());
            }
        });

    }

    private void loadAboutText(final LoadingInterface loadingInterface) {

    }


    private void loadRegions(final LoadingInterface loadingInterface) {

        apiInterface.loadRegions().enqueue(new Callback<JsonResult>() {
            @Override
            public void onResponse(Call<JsonResult> call, Response<JsonResult> response) {

            }

            @Override
            public void onFailure(Call<JsonResult> call, Throwable t) {

            }
        });
    }

    private void loadClasses(final LoadingInterface loadingInterface) {

    }

    private void loadModels(final LoadingInterface loadingInterface) {

    }

}
