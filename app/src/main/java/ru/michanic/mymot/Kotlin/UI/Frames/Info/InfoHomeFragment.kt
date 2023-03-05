package ru.michanic.mymot.UI.Frames.Info;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.R;
import ru.michanic.mymot.Kotlin.UI.Activities.TextActivity;

public class InfoHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_info_home, null);

        TextView aboutText = (TextView) rootView.findViewById(R.id.aboutText);
        Spanned htmlText = Html.fromHtml(MyMotApplication.getConfigStorage().aboutText);
        aboutText.setText(htmlText);

        FrameLayout agreementView = (FrameLayout) rootView.findViewById(R.id.agreementView);
        agreementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent textActivity = new Intent(getActivity(), TextActivity.class);
                textActivity.putExtra("title", "Пользовательское соглашение");
                getActivity().startActivity(textActivity);
            }
        });

        return rootView;
    }

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
}
