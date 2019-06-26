package ru.michanic.mymot.UI.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import ru.michanic.mymot.R;

public class ParametersListAdapter extends BaseAdapter {

    private List<LinkedTreeMap<String,String>> parameters;

    public ParametersListAdapter(List<LinkedTreeMap<String,String>> parameters) {
        this.parameters = parameters;
    }

    @Override
    public int getCount() {
        return parameters.size();
    }

    @Override
    public Object getItem(int position) {
        return parameters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_parameter, parent, false);
        }

        LinkedTreeMap<String,String> parameter = parameters.get(position);
        String title = parameter.keySet().iterator().next();
        String value = parameter.values().iterator().next();

        TextView parameterTitle = (TextView) convertView.findViewById(R.id.parameter_title);
        parameterTitle.setText(title);
        TextView parameterValue = (TextView) convertView.findViewById(R.id.parameter_value);
        parameterValue.setText(value);

        return convertView;
    }
}
