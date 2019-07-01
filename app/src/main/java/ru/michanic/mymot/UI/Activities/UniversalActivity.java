package ru.michanic.mymot.UI.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;

import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.NoConnectionRepeatInterface;
import ru.michanic.mymot.Utils.TypefaceSpan;

public class UniversalActivity extends AppCompatActivity {

    boolean rootActivity = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!rootActivity) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void setNavigationTitle(String title) {
        SpannableString s = new SpannableString(" " + title + " ");
        s.setSpan(new TypefaceSpan(this, "Progress.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
    }

    public void showNoConnectionDialog(final NoConnectionRepeatInterface noConnectionRepeatInterface) {
        showDialog("Ошибка", "Отсутствует соединение с сервером", noConnectionRepeatInterface);
    }

    protected void showDialog(String title, String message, final NoConnectionRepeatInterface noConnectionRepeatInterface) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(UniversalActivity.this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setPositiveButton("Повторить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                noConnectionRepeatInterface.repeatPressed();
            }
        });
        /*alertDialogBuilder.setNegativeButton("Повторить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });*/
        AlertDialog alertDialog = (AlertDialog) alertDialogBuilder.create();
        alertDialog.show();
    }

}
