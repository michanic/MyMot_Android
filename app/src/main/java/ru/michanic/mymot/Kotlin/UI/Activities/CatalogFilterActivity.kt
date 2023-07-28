package ru.michanic.mymot.Kotlin.UI.Activities

import android.os.Bundle
import android.view.MenuItem
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.databinding.ActivityCatalogFilterBinding


class CatalogFilterActivity : UniversalActivity() {

    lateinit var binding: ActivityCatalogFilterBinding

    private var goBackOnSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCatalogFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setNavigationTitle("Фильтр")
        goBackOnSearch = intent.getBooleanExtra("goBackOnSearch", false)

        binding.cylinderCountSlider.addOnChangeListener { slider, value, fromUser ->
            binding.cylinderCountSliderTv.text = value.toInt().toString()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        MyMotApplication.searchManager?.backPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> MyMotApplication.searchManager?.backPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}