package ru.michanic.mymot.UI.Cells;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.michanic.mymot.R;

enum CellAccessoryType {
    RIGHT,
    TOP,
    BOTTOM,
    HIDDEN,
    CHECKED,
    LOADING;

    public double angle() {
        switch (this) {
            case RIGHT:
                return 0;
            case TOP:
                return - Math.PI / 2;
            case BOTTOM:
                return Math.PI / 2;
            case HIDDEN:
                return 0;
            case CHECKED:
                return 0;
            case LOADING:
                return 0;
        }
        return 0;
    }

}
public class SimpleCell extends RecyclerView.ViewHolder  {

    ImageView arrowImage;
    TextView simpleLabel;
    ProgressBar loadingIndicator;

    public SimpleCell(View itemView) {
        super(itemView);
        arrowImage = (ImageView) itemView.findViewById(R.id.arrowView);
        simpleLabel = (TextView) itemView.findViewById(R.id.textView);
        loadingIndicator = (ProgressBar) itemView.findViewById(R.id.progressBar);
    }

    public void fillWithTitle(String title) {
        simpleLabel.setText(title);
        loadingIndicator.setVisibility(View.GONE);
    }

    public void fillWithTitle(String title, CellAccessoryType accessoryType) {
        simpleLabel.setText(title);
        loadingIndicator.setVisibility(View.GONE);
        switch (accessoryType) {
            case RIGHT:
                arrowImage.setImageResource(R.drawable.ic_arrow_right);
                break;
            case TOP:
                break;
            case BOTTOM:
                break;
            case HIDDEN:
                arrowImage.setVisibility(View.GONE);
                break;
            case CHECKED:
                arrowImage.setImageResource(R.drawable.ic_arrow_right);
                break;
            case LOADING:
                arrowImage.setVisibility(View.GONE);
                loadingIndicator.setVisibility(View.VISIBLE);
                break;
        }


    }

}
