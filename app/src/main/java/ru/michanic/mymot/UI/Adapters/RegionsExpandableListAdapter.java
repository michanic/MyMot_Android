package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.michanic.mymot.Enums.CellAccessoryType;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Cells.SimpleCell;

public class RegionsExpandableListAdapter extends BaseExpandableListAdapter {

    public Context context;
    private List<Location> regions;
    private Location filterRegion = MyMotApplication.searchManager.getRegion();

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
            int citiesCount = MyMotApplication.dataManager.getRegionCitiesCount(region.getId());
            if (citiesCount > 0) {
                return citiesCount + 1;
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
        if (groupPosition == 0) {
            CellAccessoryType state = (filterRegion == null) ? CellAccessoryType.CHECKED : CellAccessoryType.HIDDEN;
            SimpleCell.fillWithTitle(cell, "По всей России", state, 1);
        } else {
            Location region = regions.get(groupPosition - 1);
            CellAccessoryType cellAccessoryType = CellAccessoryType.BOTTOM;
            if (isExpanded) {
                int citiesCount = MyMotApplication.dataManager.getRegionCitiesCount(region.getId());
                cellAccessoryType = citiesCount == 0 ? CellAccessoryType.LOADING : CellAccessoryType.TOP;
            }
            SimpleCell.fillWithTitle(cell, region.getName(), cellAccessoryType, 1);
        }
        return cell;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View cell = View.inflate(context, R.layout.cell_simple, null);

        if (childPosition == 0) {
            CellAccessoryType state = CellAccessoryType.HIDDEN;
            if (filterRegion != null) {
                if (filterRegion.getId() == regions.get(groupPosition - 1).getId()) {
                    state = CellAccessoryType.CHECKED;
                }
            }
            SimpleCell.fillWithTitle(cell, "Все города", state, 2);

        } else {
            Location region = regions.get(groupPosition - 1);
            List<Location> cities = MyMotApplication.dataManager.getRegionCities(region.getId());
            Location city = cities.get(childPosition - 1);

            CellAccessoryType state = CellAccessoryType.HIDDEN;
            if (filterRegion != null) {
                Log.e("filterRegion", filterRegion.getName());

                if (filterRegion.getId() == city.getId()) {
                    state = CellAccessoryType.CHECKED;
                }
            }
            SimpleCell.fillWithTitle(cell, city.getName(), state, 2);
        }
        return cell;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
