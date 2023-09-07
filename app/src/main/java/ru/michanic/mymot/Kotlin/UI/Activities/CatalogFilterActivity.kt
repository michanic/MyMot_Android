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

        setupYearsReleaseSlider()
        setupEngineDisplacementSlider()
        setupCountCylinderSlider()
        setupPowerSlider()
        setupSeatHeightSlider()
        setupCurbWeightSlider()
    }

    private fun setupYearsReleaseSlider() {
        binding.yearsReleaseSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.yearsReleaseSlider.values
            binding.yearsReleaseOfEt.setText("${values[0].toInt()}")
            binding.yearsReleaseToEt.setText("${values[1].toInt()}")
        }
    }

    private fun setupEngineDisplacementSlider() {
        binding.engineDisplacementSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.engineDisplacementSlider.values
            binding.engineDisplacementOfEt.setText("${values[0].toInt()}")
            binding.engineDisplacementToEt.setText("${values[1].toInt()}")
        }
    }

    private fun setupPowerSlider() {
        val from: Double = 5.0
        val to: Double = 400.0
        val currentFrom: Double = 100.0
        val currentTo: Double = 200.0

        with(binding.powerSlider) {
            valueFrom = from.toFloat()
            valueTo = to.toFloat()
            setValues(currentFrom.toFloat(), currentTo.toFloat())
        }

        binding.powerSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.powerSlider.values
            binding.powerOfEt.setText("${values[0].toInt()}")
            binding.powerToEt.setText("${values[1].toInt()}")
        }
    }

    private fun setupSeatHeightSlider() {
        val from: Double = 600.0
        val to: Double = 1000.0
        val currentFrom: Double = 700.0
        val currentTo: Double = 800.0

        with(binding.seatHeightSlider) {
            valueFrom = from.toFloat()
            valueTo = to.toFloat()
            setValues(currentFrom.toFloat(), currentTo.toFloat())
        }

        binding.seatHeightSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.seatHeightSlider.values
            binding.seatHeightOfEt.setText("${values[0].toInt()}")
            binding.seatHeightToEt.setText("${values[1].toInt()}")
        }
    }

    private fun setupCurbWeightSlider() {
        val from: Double = 60.0
        val to: Double = 450.0
        val currentFrom: Double = 100.0
        val currentTo: Double = 300.0

        with(binding.curbWeightSlider) {
            valueFrom = from.toFloat()
            valueTo = to.toFloat()
            setValues(currentFrom.toFloat(), currentTo.toFloat())
        }

        binding.curbWeightSlider.addOnChangeListener { slider, value, fromUser ->
            val values = binding.curbWeightSlider.values
            binding.curbWeightOfEt.setText("${values[0].toInt()}")
            binding.curbWeightToEt.setText("${values[1].toInt()}")
        }
    }


    private fun setupCountCylinderSlider() {
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