package ru.michanic.mymot.Kotlin.UI.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.google.gson.internal.LinkedTreeMap
import ru.michanic.mymot.R

class ParametersListAdapter(private val parametersTree: List<LinkedTreeMap<String, String>>?) :
    BaseAdapter() {
    override fun getCount(): Int {
        return parametersTree?.size ?: 0
    }

    override fun getItem(position: Int): Any {
        return (if (parametersTree != null) parametersTree else {
            throw KotlinNullPointerException()
        })[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(parent.context).inflate(R.layout.cell_parameter, parent, false)
        }
        val parameter =
            (if (parametersTree != null) parametersTree else {
                throw KotlinNullPointerException()
            })[position]
        val title = parameter.keys.iterator().next()
        val value = parameter.values.iterator().next()
        val parameterTitle = convertView?.findViewById<View>(R.id.parameter_title) as TextView
        parameterTitle.text = title
        val parameterValue = convertView?.findViewById<View>(R.id.parameter_value) as TextView
        parameterValue.text = value
        return convertView
    }
}