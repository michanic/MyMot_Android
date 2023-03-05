package ru.michanic.mymot.Kotlin.UI.Frames.Catalog

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.michanic.mymot.Extensions.Font
import ru.michanic.mymot.Kotlin.UI.Activities.CatalogByClassActivity
import ru.michanic.mymot.Kotlin.UI.Activities.CatalogByManufacturerActivity
import ru.michanic.mymot.Kotlin.UI.Activities.CatalogByVolumeActivity
import ru.michanic.mymot.Kotlin.UI.Adapters.ClassesSliderAdapter
import ru.michanic.mymot.Kotlin.UI.Adapters.ManufacturersSliderAdapter
import ru.michanic.mymot.Kotlin.UI.Adapters.VolumessSliderAdapter
import ru.michanic.mymot.Protocols.ClickListener
import ru.michanic.mymot.R
import ru.michanic.mymot.Utils.DataManager

class CatalogHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_catalog_home, null)
        val dataManager = DataManager()
        val classesTitleView = rootView.findViewById<View>(R.id.classesTitle) as TextView
        val classesRecyclerView = rootView.findViewById<View>(R.id.classesSlider) as RecyclerView
        val manufacturersTitleView =
            rootView.findViewById<View>(R.id.manufacturersTitle) as TextView
        val manufacturersRecyclerView =
            rootView.findViewById<View>(R.id.manufacturersSlider) as RecyclerView
        val volumesTitleView = rootView.findViewById<View>(R.id.volumesTitle) as TextView
        val volumesRecyclerView = rootView.findViewById<View>(R.id.volumesSlider) as RecyclerView
        classesTitleView.typeface = Font.oswald
        manufacturersTitleView.typeface = Font.oswald
        volumesTitleView.typeface = Font.oswald
        val classesLayoutManager = LinearLayoutManager(activity)
        classesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        classesRecyclerView.layoutManager = classesLayoutManager
        val classes = dataManager.getCategories(true)
        val classPressed = ClickListener { section, row ->
            val catalogByClassActivity = Intent(activity, CatalogByClassActivity::class.java)
            catalogByClassActivity.putExtra("classId", classes[row].id)
            activity?.startActivity(catalogByClassActivity)
        }
        val classesAdapter = ClassesSliderAdapter(this.requireContext(), classes, classPressed)
        classesRecyclerView.adapter = classesAdapter
        val manufacturersLayoutManager = LinearLayoutManager(activity)
        manufacturersLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        manufacturersRecyclerView.layoutManager = manufacturersLayoutManager
        val manufacturers = dataManager.getManufacturers(true)
        val manufacturerPressed = ClickListener { section, row ->
            val catalogByManufacturerActivity =
                Intent(activity, CatalogByManufacturerActivity::class.java)
            catalogByManufacturerActivity.putExtra("manufacturerId", manufacturers[row].id)
            activity?.startActivity(catalogByManufacturerActivity)
        }
        val manufacturersAdapter =
            ManufacturersSliderAdapter(this.requireContext(), manufacturers, manufacturerPressed)
        manufacturersRecyclerView.adapter = manufacturersAdapter
        val volumesLayoutManager = LinearLayoutManager(activity)
        volumesLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        volumesRecyclerView.layoutManager = volumesLayoutManager
        val volumes = dataManager.volumes
        val volumePressed = ClickListener { section, row ->
            val catalogByVolumeActivity = Intent(activity, CatalogByVolumeActivity::class.java)
            catalogByVolumeActivity.putExtra("volumeId", volumes[row].id)
            activity?.startActivity(catalogByVolumeActivity)
        }
        val volumesAdapter = VolumessSliderAdapter(this.requireContext(), volumes, volumePressed)
        volumesRecyclerView.adapter = volumesAdapter
        return rootView
    }
}