package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;

import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.R;

public class RegionsExpandableListAdapter extends BaseExpandableListAdapter {

    public Context context;
    private List<Location> regions;

    public RegionsExpandableListAdapter(List<Location> regions, Context context) {
        this.regions = regions;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return regions.size() + 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition == 0) {
            return 0;
        } else {
            Location region = regions.get(groupPosition - 1);
            int citiesCount = region.getCitiesCount();
            if (citiesCount > 0) {
                return region.getCitiesCount() + 1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View cell = View.inflate(context, R.layout.cell_simple, null);
        TextView cellTitle = (TextView) cell.findViewById(R.id.textView);
        if (groupPosition == 0) {
            cellTitle.setText("По всей России");
        } else {
            Location region = regions.get(groupPosition - 1);
            cellTitle.setText(region.getName());
        }
        return cell;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View cell = View.inflate(context, R.layout.cell_simple, null);
        TextView cellTitle = (TextView) cell.findViewById(R.id.textView);
        if (childPosition == 0) {
            cellTitle.setText("Все города");
        } else {
            Location region = regions.get(groupPosition - 1);
            List<Location> cities = region.getCities();
            Location city = cities.get(childPosition - 1);
            cellTitle.setText(city.getName());
        }
        return cell;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
