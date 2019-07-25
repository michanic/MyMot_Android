package ru.michanic.mymot.Interactors;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.michanic.mymot.Models.AppPageText;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.ModelDetails;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.ApiInterface;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.Protocols.LoadingInterface;
import ru.michanic.mymot.Protocols.LoadingModelDetailsInterface;
import ru.michanic.mymot.Protocols.LoadingTextInterface;
import ru.michanic.mymot.Utils.DataManager;

public class ApiInteractor {

    private Gson gson = new GsonBuilder().create();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Const.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private ApiInterface apiInterface = retrofit.create(ApiInterface.class);
    private Realm realm;
    private DataManager dataManager = new DataManager();

    public ApiInteractor() {
        Realm.init(MyMotApplication.appContext);
        realm = Realm.getDefaultInstance();
    }

    public void loadData(final LoadingInterface loadingInterface) {

        dataManager.cleanAdverts();

        loadExteptedWords(new LoadingInterface() {

            @Override
            public void onLoaded() {
                loadAboutText(new LoadingInterface() {

                    @Override
                    public void onLoaded() {
                        loadRegions(new LoadingInterface() {

                            @Override
                            public void onLoaded() {
                                loadClasses(new LoadingInterface() {

                                    @Override
                                    public void onLoaded() {
                                        loadModels(new LoadingInterface() {

                                            @Override
                                            public void onLoaded() {
                                                dataManager.assignCategories();
                                                loadingInterface.onLoaded();
                                            }
                                            @Override
                                            public void onFailed() {
                                                loadingInterface.onFailed();
                                            }
                                        });

                                    }
                                    @Override
                                    public void onFailed() {
                                        loadingInterface.onFailed();
                                    }

                                });

                            }
                            @Override
                            public void onFailed() {
                                loadingInterface.onFailed();
                            }

                        });
                    }
                    @Override
                    public void onFailed() {
                        loadingInterface.onFailed();
                    }

                });
            }
            @Override
            public void onFailed() {
                loadingInterface.onFailed();
            }
        });

    }

    private void loadExteptedWords(final LoadingInterface loadingInterface) {
        Log.e("loadData", "loadExteptedWords");

        apiInterface.loadExteptedWords().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                MyMotApplication.getConfigStorage().exteptedWords = response.body();
                loadingInterface.onLoaded();
                Log.e("loadData", "words loaded");
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                loadingInterface.onFailed();
                Log.e("loadData", t.toString());
            }
        });

    }

    private void loadAboutText(final LoadingInterface loadingInterface) {
        Log.e("loadData", "loadAboutText");

        apiInterface.loadAboutText().enqueue(new Callback<AppPageText>() {
            @Override
            public void onResponse(Call<AppPageText> call, Response<AppPageText> response) {
                MyMotApplication.getConfigStorage().aboutText = response.body().getText();
                loadingInterface.onLoaded();
                Log.e("loadData", "about loaded");
            }

            @Override
            public void onFailure(Call<AppPageText> call, Throwable t) {
                loadingInterface.onFailed();
                Log.e("response", t.toString());
            }
        });

    }


    private void loadRegions(final LoadingInterface loadingInterface) {
        Log.e("loadData", "loadRegions");

        apiInterface.loadRegions().enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body());
                realm.commitTransaction();

                loadingInterface.onLoaded();
                Log.e("loadData", "regions loaded");
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                loadingInterface.onFailed();
                Log.e("response", t.toString());
            }
        });
    }

    public void loadRegionCities(final Location region, final LoadingInterface loadingInterface) {

        apiInterface.loadRegionCities(region.getId()).enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body());
                realm.commitTransaction();
                loadingInterface.onLoaded();
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                loadingInterface.onFailed();
                Log.e("response", t.toString());
            }
        });
    }


    private void loadClasses(final LoadingInterface loadingInterface) {
        Log.e("loadData", "loadClasses");

        apiInterface.loadClasses().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body());
                realm.commitTransaction();

                loadingInterface.onLoaded();
                Log.e("loadData", "classes loaded");
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                loadingInterface.onFailed();
                Log.e("response", t.toString());
            }
        });

    }

    private void loadModels(final LoadingInterface loadingInterface) {
        Log.e("loadData", "loadModels");

        apiInterface.loadModels().enqueue(new Callback<List<Manufacturer>>() {
            @Override
            public void onResponse(Call<List<Manufacturer>> call, Response<List<Manufacturer>> response) {

                List<Integer> favoriteModels = dataManager.getFavouriteModels();

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(response.body());
                for (Integer modelId: favoriteModels) {
                    Model model = dataManager.getModelById(modelId);
                    model.setFavourite(true);
                }
                realm.commitTransaction();

                loadingInterface.onLoaded();
                Log.e("loadData", "models loaded");
            }

            @Override
            public void onFailure(Call<List<Manufacturer>> call, Throwable t) {
                loadingInterface.onFailed();
                Log.e("response", t.toString());
            }
        });

    }

    public void loadModelDetails(int modelId, final LoadingModelDetailsInterface loadingInterface) {
        Log.e("loadData", "loadModelDetails");

        apiInterface.loadModelDetails(modelId).enqueue(new Callback<ModelDetails>() {
            @Override
            public void onResponse(Call<ModelDetails> call, Response<ModelDetails> response) {
                loadingInterface.onLoaded(response.body());
            }

            @Override
            public void onFailure(Call<ModelDetails> call, Throwable t) {
                loadingInterface.onFailed();
                Log.e("response", t.toString());
            }
        });
    }

    public void loadAgreementText(final LoadingTextInterface loadingTextInterface) {
        apiInterface.loadAgreementText().enqueue(new Callback<AppPageText>() {
            @Override
            public void onResponse(Call<AppPageText> call, Response<AppPageText> response) {
                loadingTextInterface.onLoaded(response.body().getText());
            }

            @Override
            public void onFailure(Call<AppPageText> call, Throwable t) {
                loadingTextInterface.onLoaded(t.toString());
            }
        });
    }




}
