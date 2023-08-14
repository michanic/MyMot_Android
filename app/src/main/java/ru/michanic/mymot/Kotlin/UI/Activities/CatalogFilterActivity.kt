package ru.michanic.mymot.Kotlin.UI.Activities

import android.annotation.SuppressLint
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

        yearsRelease()
        cylinderCount()
        engineDisplacement()
        maxPower()
        enginePrm()
        seatHeight()
        curbWeight()
    }

    private fun yearsRelease() {
        binding.yearsReleaseSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.yearsReleaseSlider.values
            binding.yearsReleaseOfEt.setText("от : ${values[0]}")
            binding.yearsReleaseToEt.setText("до : ${values[1]}")
        }
    }

    private fun engineDisplacement() {
        binding.engineDisplacementSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.engineDisplacementSlider.values
            binding.engineDisplacementOfEt.setText("от : ${values[0]}")
            binding.engineDisplacementToEt.setText("до : ${values[1]}")
        }
    }

    private fun maxPower() {
        binding.maxPowerSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.maxPowerSlider.values
            binding.maxPowerOfEt.setText("от : ${values[0]}")
            binding.maxPowerToEt.setText("до : ${values[1]}")
        }
    }

    private fun enginePrm() {
        binding.enginePrmSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.enginePrmSlider.values
            binding.engineRpmOfEt.setText("от : ${values[0]}")
            binding.engineRpmToEt.setText("до : ${values[1]}")
        }
    }

    private fun seatHeight() {
        binding.seatHeightSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.seatHeightSlider.values
            binding.seatHeightOfEt.setText("от : ${values[0]}")
            binding.seatHeightToEt.setText("до : ${values[1]}")
        }
    }

    private fun curbWeight() {
        binding.curbWeightSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.curbWeightSlider.values
            binding.curbWeightOfEt.setText("от : ${values[0]}")
            binding.curbWeightToEt.setText("до : ${values[1]}")
        }
    }


    private fun cylinderCount() {
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