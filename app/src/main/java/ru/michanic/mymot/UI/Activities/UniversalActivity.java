package ru.michanic.mymot.UI.Activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.NoConnectionRepeatInterface;

public class UniversalActivity extends AppCompatActivity {

    protected void showNoConnectionDialog(final NoConnectionRepeatInterface noConnectionRepeatInterface) {
        showDialog("Ошибка", "Отсутствует соединение с сервером", noConnectionRepeatInterface);
    }

    protected void showDialog(String title, String message, final NoConnectionRepeatInterface noConnectionRepeatInterface) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MyMotApplication.appContext);
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
