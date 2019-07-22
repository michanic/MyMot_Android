package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.CellAccessoryType;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.FilterModelItem;
import ru.michanic.mymot.Models.Location;
import ru.michanic.mymot.Models.Manufacturer;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Cells.SimpleCell;
import ru.michanic.mymot.Utils.DataManager;

public class ModelsExpandableListAdapter extends BaseExpandableListAdapter {

    public Context context;
    private List<FilterModelItem> topCells;

    public ModelsExpandableListAdapter(Context context) {
        this.context = context;

        topCells = new ArrayList<>();
        topCells.add(new FilterModelItem("Все мотоциклы", false));

        DataManager dataManager = new DataManager();

        for (Manufacturer manufacturer: dataManager.getManufacturers(true)) {
            topCells.add(new FilterModelItem(manufacturer.getName()));
            topCells.add(new FilterModelItem("Все мотоциклы " + manufacturer.getName(), false));

            for (Category category: dataManager.getCategories(true)) {
                List<Model> models = dataManager.getManufacturerModels(manufacturer, category);
                if (models.size() > 0) {
                    topCells.add(new FilterModelItem(category, models));
                }
            }
        }

    }

    @Override
    public int getGroupCount() {
        return topCells.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        FilterModelItem item = topCells.get(groupPosition);
        switch (item.type) {
            case FilterModelItem.SECTION_TITLE:
                return 0;
            case FilterModelItem.SIMPLE_CELL:
                return 0;
            case FilterModelItem.CATEGORY_CELL:
                return item.getModels().size();
        }
        return 0;
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
        FilterModelItem item = topCells.get(groupPosition);
        switch (item.type) {
            case FilterModelItem.SECTION_TITLE:
                View sectionView = View.inflate(context, R.layout.cell_section_title, null);
                TextView title = (TextView) sectionView.findViewById(R.id.section_title);
                title.setText(item.getTitle());
                return sectionView;
            case FilterModelItem.SIMPLE_CELL:
                View view = View.inflate(context, R.layout.cell_simple, null);
                SimpleCell.fillWithTitle(view, item.getTitle(), CellAccessoryType.HIDDEN, 1);
                return view;
            case FilterModelItem.CATEGORY_CELL:
                View categoryView = View.inflate(context, R.layout.cell_simple, null);
                SimpleCell.fillWithTitle(categoryView, item.getTitle(), isExpanded ? CellAccessoryType.TOP : CellAccessoryType.BOTTOM, 1);
                return categoryView;
        }
        return null;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.cell_models_list, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.cell_image);
        TextView modelTitle = (TextView) view.findViewById(R.id.model_title);
        TextView years = (TextView) view.findViewById(R.id.model_years);

        Model model = topCells.get(groupPosition).getModels().get(childPosition);
        Picasso.get().load(Const.DOMAIN + model.getPreview_picture()).placeholder(R.drawable.ic_placeholder).into(imageView);
        modelTitle.setText(model.getName());
        years.setText(model.getYears());

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
