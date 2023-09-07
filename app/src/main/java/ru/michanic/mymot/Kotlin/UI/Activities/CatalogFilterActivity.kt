package ru.michanic.mymot.Kotlin.UI.Activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.databinding.ActivityCatalogFilterBinding


class CatalogFilterActivity : UniversalActivity() {

    var cylynders_count: Int = 0
    var cylynders_placement_type: Int = 0
    var cooling: Int = 0
    var power: String? = null
    var drive_type: Int = 0
    var seat_height: String? = null
    var wet_weight: String? = null
    var max = Double
    var min = Double

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
            binding.yearsReleaseOfEt.setText("от : ${values[0].toInt()}")
            binding.yearsReleaseToEt.setText("до : ${values[1].toInt()}")
        }
    }

    private fun engineDisplacement() {
        binding.engineDisplacementSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.engineDisplacementSlider.values
            binding.engineDisplacementOfEt.setText("от : ${values[0].toInt()}")
            binding.engineDisplacementToEt.setText("до : ${values[1].toInt()}")
        }
    }

    private fun maxPower() {
        val from: Double = 5.0
        val to: Double = 305.0
        val currentFrom: Double = 100.0
        val currentFor: Double = 200.0
        binding.maxPowerSlider.valueFrom = from.toFloat()
        binding.maxPowerSlider.valueTo = to.toFloat()
        binding.maxPowerSlider.setValues(currentFrom.toFloat(), currentFor.toFloat())

        binding.maxPowerSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.maxPowerSlider.values
            binding.maxPowerOfEt.setText("от : ${values[0].toInt()}")
            binding.maxPowerToEt.setText("до : ${values[1].toInt()}")
        }
    }

    private fun enginePrm() {
        binding.enginePrmSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.enginePrmSlider.values
            binding.engineRpmOfEt.setText("от : ${values[0].toInt()}")
            binding.engineRpmToEt.setText("до : ${values[1].toInt()}")
        }
    }

    private fun seatHeight() {
        binding.seatHeightSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.seatHeightSlider.values
            binding.seatHeightOfEt.setText("от : ${values[0].toInt()}")
            binding.seatHeightToEt.setText("до : ${values[1].toInt()}")
        }
    }

    private fun curbWeight() {
        binding.curbWeightSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.curbWeightSlider.values
            binding.curbWeightOfEt.setText("от : ${values[0].toInt()}")
            binding.curbWeightToEt.setText("до : ${values[1].toInt()}")
        }
    }


    private fun cylinderCount() {
        binding.cylinderCountSlider.addOnChangeListener { slider, value, fromUser ->
            binding.cylinderCountSliderTv.text = value.toInt().toString()
        }
    }

    override fun onBackPressed() {
        super.getOnBackPressedDispatcher().onBackPressed()
        MyMotApplication.searchManager?.backPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> MyMotApplication.searchManager?.backPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}