package ru.michanic.mymot.Kotlin.UI.Cells

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.michanic.mymot.R

class ImagesSliderSlide : Fragment() {
    var imagePath: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.cell_images_slide, container, false)
        val imageView = rootView.findViewById<View>(R.id.image) as ImageView
        Picasso.get().load(imagePath).into(imageView)
        return rootView
    }

    companion object {
        fun newInstance(imagePath: String?): ImagesSliderSlide {
            val fragment = ImagesSliderSlide()
            fragment.imagePath = imagePath
            return fragment
        }
    }
}