package ru.michanic.mymot.UI.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ru.michanic.mymot.Extensions.Font;
import ru.michanic.mymot.Interactors.ApiInteractor;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.LoadingInterface;
import ru.michanic.mymot.R;

public class LoadingActivity extends UniversalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getSupportActionBar().hide();

        MyMotApplication.setAppContext(getApplicationContext());

        TextView bigTitle = (TextView) findViewById(R.id.bigTitle);
        bigTitle.setTypeface(Font.progress);
        bigTitle.setText("Загрузка");

        TextView subtitle = (TextView) findViewById(R.id.subtitle);
        subtitle.setText("Синхронизация каталога");

        ApiInteractor apiInteractor = new ApiInteractor();
        apiInteractor.loadData(new LoadingInterface() {
            @Override
            public void onLoaded() {

                Intent mainActivity = new Intent(MyMotApplication.appContext, MainActivity.class);
                mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainActivity);

            }
        });
    }

    private void loadCities() {
    }
}
