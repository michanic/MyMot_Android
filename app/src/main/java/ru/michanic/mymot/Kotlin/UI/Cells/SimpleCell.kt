package ru.michanic.mymot.UI.Cells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.michanic.mymot.Enums.CellAccessoryType;
import ru.michanic.mymot.R;


public class SimpleCell  {

    public static void fillWithTitle(View view, String title, CellAccessoryType accessoryType, int level) {

        ImageView arrowImage = (ImageView) view.findViewById(R.id.arrowView);
        TextView simpleLabel = (TextView) view.findViewById(R.id.textView);
        ProgressBar loadingIndicator = (ProgressBar) view.findViewById(R.id.progressBar);

        if (level == 2) {
            title = "    " + title;
        }

        simpleLabel.setText(title);
        loadingIndicator.setVisibility(View.GONE);

        switch (accessoryType) {
            case RIGHT:
                arrowImage.setImageResource(R.drawable.ic_arrow_right);
                break;
            case TOP:
                arrowImage.setImageResource(R.drawable.ic_arrow_up);
                break;
            case BOTTOM:
                arrowImage.setImageResource(R.drawable.ic_arrow_down);
                break;
            case HIDDEN:
                arrowImage.setVisibility(View.GONE);
                break;
            case CHECKED:
                arrowImage.setImageResource(R.drawable.ic_checked);
                break;
            case LOADING:
                arrowImage.setVisibility(View.GONE);
                loadingIndicator.setVisibility(View.VISIBLE);
                break;
        }
    }

}
