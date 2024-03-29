package ru.michanic.mymot.Kotlin.UI.Frames.Favourites

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import de.halfbit.pinnedsection.PinnedSectionListView
import ru.michanic.mymot.Kotlin.Extensions.Font
import ru.michanic.mymot.Kotlin.UI.Activities.CatalogModelActivity
import ru.michanic.mymot.Kotlin.UI.Adapters.SectionItemsListAdapter
import ru.michanic.mymot.Kotlin.Models.SectionModelItem
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.R

class FavouriteModelsFragment : Fragment() {
    private val tabs: TabLayout? = null
    private var sectionItemsListAdapter: SectionItemsListAdapter? = null
    private var listView: PinnedSectionListView? = null
    private var placeholder: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_favourite_models, null)
        listView = rootView.findViewById<View>(R.id.listView) as PinnedSectionListView
        placeholder = rootView.findViewById<View>(R.id.listIsEmpty) as TextView
        placeholder?.typeface = Font.oswald
        loadModels()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadModels()
    }

    private fun loadModels() {
        val items: MutableList<SectionModelItem?> = ArrayList()
        val models = MyMotApplication.dataManager?.favouriteModels ?: emptyList()
        if (models.size > 0) {
            for (model in models) {
                items.add(SectionModelItem(model))
            }
            sectionItemsListAdapter = SectionItemsListAdapter(items)
            listView?.adapter = sectionItemsListAdapter
            listView?.onItemClickListener = OnItemClickListener { parent, view, position, id ->
                val model = items[position]?.model
                if (model != null) {
                    val catalogModelActivity = Intent(context, CatalogModelActivity::class.java)
                    catalogModelActivity.putExtra("modelId", model.id)
                    startActivity(catalogModelActivity)
                }
            }
            listView?.visibility = View.VISIBLE
            sectionItemsListAdapter?.notifyDataSetChanged()
        } else {
            listView?.visibility = View.GONE
        }
    }
}