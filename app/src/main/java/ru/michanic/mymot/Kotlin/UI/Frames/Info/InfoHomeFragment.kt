package ru.michanic.mymot.Kotlin.UI.Frames.Info

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.michanic.mymot.Kotlin.MyMotApplication
import ru.michanic.mymot.Kotlin.UI.Activities.TextActivity
import ru.michanic.mymot.R
import ru.michanic.mymot.databinding.FragmentInfoHomeBinding

class InfoHomeFragment : Fragment() {

    private var _binding: FragmentInfoHomeBinding? = null
    private val binding: FragmentInfoHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentInfoHomeBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoHomeBinding.inflate(inflater, container, false)
        val aboutText = binding.aboutText
        val htmlText = Html.fromHtml(MyMotApplication.configStorage?.aboutText)
        aboutText.text = htmlText
        val agreementView = binding.agreementView
        agreementView.setOnClickListener {
            val textActivity = Intent(activity, TextActivity::class.java)
            textActivity.putExtra("title", "Пользовательское соглашение")
            activity?.startActivity(textActivity)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSettings.setOnClickListener {
            chooseThemeDialog()
        }
    }

    private fun chooseThemeDialog() {
        val configStorage = MyMotApplication.configStorage

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.set_theme))
        val styles = arrayOf(
            getString(R.string.light_theme),
            getString(R.string.dark_theme),
            getString(R.string.system_theme)
        )
        val checkedItem = configStorage?.colorModeIndex ?: 0
        builder.setSingleChoiceItems(styles, checkedItem) { dialog, itemIndex ->
            configStorage?.saveColorModeIndex(itemIndex)
            AppCompatDelegate.setDefaultNightMode(configStorage?.colorMode ?: 0)
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}