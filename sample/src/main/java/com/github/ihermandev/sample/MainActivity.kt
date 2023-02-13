package com.github.ihermandev.sample

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.ihermandev.advancedradiogroup.AdvancedRadioGroup

class MainActivity : AppCompatActivity() {

    private val numberRadioGroup: AdvancedRadioGroup by lazy {
        findViewById(R.id.numberRadioGroup)
    }

    private val titleRadioGroup: AdvancedRadioGroup by lazy {
        findViewById(R.id.titleRadioGroup)
    }

    private val assetRadioGroup: AdvancedRadioGroup by lazy {
        findViewById(R.id.assetRadioGroup)
    }

    private val mixedRadioGroup: AdvancedRadioGroup by lazy {
        findViewById(R.id.mixedRadioGroup)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            showMessage(radioButton.text.toString())
        }

        titleRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.firstTitleRadioButton -> {
                    showMessage("First title clicked")
                }
                R.id.secondTitleRadioButton -> {
                    showMessage("Second title clicked")
                }
            }
        }

        assetRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.oneKRadioButton -> {
                    showMessage("1K")
                }
                R.id.twoKRadioButton -> {
                    showMessage("2K")
                }
                R.id.threeKRadioButton -> {
                    showMessage("3K")
                }
            }
        }

        mixedRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.firstMixedRadioButton -> {
                    showMessage("Regular")
                }
                R.id.secondMixedRadioButton -> {
                    showMessage("Title / Subtitle")
                }
                R.id.thirdMixedRadioButton -> {
                    showMessage("Drawable")
                }
                R.id.fourthMixedRadioButton -> {
                    showMessage("Mipmap")
                }
            }
        }
    }

    private fun showMessage(message: String) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}