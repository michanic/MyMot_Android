package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ExpandableListView;

public class ExpandableListAdapter extends ExpandableListView {

    public ExpandableListAdapter(Context context)
    {
        super(context);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        DisplayMetrics lDisplayMetrics = getResources().getDisplayMetrics();
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(lDisplayMetrics.widthPixels, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
