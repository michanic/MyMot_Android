package ru.michanic.mymot.UI.Adapters;

import android.content.ClipData;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.halfbit.pinnedsection.PinnedSectionListView;
import ru.michanic.mymot.Models.Category;
import ru.michanic.mymot.Models.Model;
import ru.michanic.mymot.Models.SectionModelItem;
import ru.michanic.mymot.MyMotApplication;
import ru.michanic.mymot.Protocols.Const;
import ru.michanic.mymot.R;
import ru.michanic.mymot.UI.Cells.CatalogSliderCell;

public class SectionItemsListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    List<SectionModelItem> items;

    public SectionItemsListAdapter(List<SectionModelItem> items) {
        this.items = items;
    }

    public void setItems(List<SectionModelItem> items) {
        this.items = items;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == SectionModelItem.SECTION_TITLE;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override public int getViewTypeCount() {
        return 4;
    }

    @Override public int getItemViewType(int position) {
        return items.get(position).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SectionModelItem item = items.get(position);
        View view = convertView;

        switch (item.type) {
            case SectionModelItem.SECTION_TITLE:
                view = View.inflate(parent.getContext(), R.layout.cell_section_title, null);
                TextView title = (TextView) view.findViewById(R.id.section_title);
                title.setText(item.getSectionTitle());
                break;

            case SectionModelItem.LIST_MODEL:
                view = View.inflate(parent.getContext(), R.layout.cell_models_list, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.cell_image);
                TextView modelTitle = (TextView) view.findViewById(R.id.model_title);
                TextView years = (TextView) view.findViewById(R.id.model_years);

                Model model = item.getModel();
                Picasso.get().load(Const.DOMAIN + model.getPreview_picture()).placeholder(R.drawable.ic_placeholder).into(imageView);
                modelTitle.setText(model.getName());
                years.setText(model.getYears());
                break;

            case SectionModelItem.SIMPLE_CELL:
                view = View.inflate(parent.getContext(), R.layout.cell_simple, null);
                TextView simpleTitle = (TextView) view.findViewById(R.id.textView);
                simpleTitle.setText(item.getPropertyTitle());
                break;

            case SectionModelItem.PRICE_CELL:
                view = View.inflate(parent.getContext(), R.layout.cell_price, null);
                TextView priceTitle = (TextView) view.findViewById(R.id.textView);
                EditText priceValue = (EditText) view.findViewById(R.id.priceValue);

                final String propertyTitle = item.getPropertyTitle();

                priceValue.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (propertyTitle == SectionModelItem.PRICE_FROM_NAME) {
                            try {
                                int priceFrom = Integer.parseInt(s.toString());
                                MyMotApplication.searchManager.setPriceFrom(priceFrom);
                            } catch(NumberFormatException nfe) { }

                        } else if (propertyTitle == SectionModelItem.PRICE_FOR_NAME) {
                            try {
                                int priceFor = Integer.parseInt(s.toString());
                                MyMotApplication.searchManager.setPriceFor(priceFor);
                            } catch(NumberFormatException nfe) { }
                        }
                        item.setPropertyValue(s.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                priceTitle.setText(propertyTitle);
                priceValue.setText(item.getPropertyValue());
                break;
        }

        return view;
    }


}
