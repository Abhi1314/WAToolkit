package com.example.pistalix.watoolkit.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.databinding.ActivityTextRepeaterBinding
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.hideSoftKeyboard
import com.example.pistalix.watoolkit.utils.shortShowToast


class TextRepeaterActivity : AppCompatActivity() {
    private val textRepeaterBinding: ActivityTextRepeaterBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_text_repeater)
    }

    lateinit var sharedads: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, textRepeaterBinding.adsContainer)
            } else {
                gone(textRepeaterBinding.adsContainer)
            }
            textRepeaterBinding.toolBarLayout.name = "Text Repeater"
            textRepeaterBinding.toolBarLayout.backImage.setOnClickListener {
                finish()
            }


            textRepeaterBinding.edChatText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.toString().trim().isEmpty()) {
                        textRepeaterBinding.edChatTextL.error = getString(R.string.textValidation)
                    } else {
                        textRepeaterBinding.edChatTextL.error = null
                    }
                }
            })
            textRepeaterBinding.edRepeatNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (textRepeaterBinding.edRepeatNumber.text.toString().trim().isEmpty()) {
                        textRepeaterBinding.edRepeatNumberL.error =
                            (getString(R.string.enter_number))
                    } else if(textRepeaterBinding.edRepeatNumber.text.toString().length>4){
                        textRepeaterBinding.edRepeatNumberL.error ="Max limit is 10000"
                    }
                    else {
                        textRepeaterBinding.edRepeatNumberL.error = null
                    }
                }
            })
            textRepeaterBinding.textShowView.movementMethod = ScrollingMovementMethod()
            textRepeaterBinding.repeatTextBtn.setOnClickListener {
                hideSoftKeyboard(it)
                if (textRepeaterBinding.edChatText.text.toString().trim().isEmpty()) {
                    textRepeaterBinding.edChatTextL.error = (getString(R.string.textValidation))
                    textRepeaterBinding.edRepeatNumberL.error = null
                } else if (textRepeaterBinding.edRepeatNumber.text.toString().trim().isEmpty()) {
                    textRepeaterBinding.edChatTextL.error = null
                    textRepeaterBinding.edRepeatNumberL.error = (getString(R.string.enter_number))
                } else if (textRepeaterBinding.edRepeatNumber.text.toString().length>4) {
                    textRepeaterBinding.edChatTextL.error = null
                    textRepeaterBinding.edRepeatNumberL.error ="Max limit is 10000"

                } else {
                    textRepeaterBinding.edChatTextL.error = null
                    textRepeaterBinding.edRepeatNumberL.error = null
                    textRepeaterBinding.textShowView.error = null
                    var outputText = ""
                    if (textRepeaterBinding.newLine.isChecked) {
                        val text = textRepeaterBinding.edChatText.text.toString().trim()
                        val number =
                            textRepeaterBinding.edRepeatNumber.text.toString().trim()
                        for (i in 1..number.toInt()) {
                            outputText += "$text \n"

                        }

                    }
                    else {
                        val text = textRepeaterBinding.edChatText.text.toString().trim()
                        val number =
                            textRepeaterBinding.edRepeatNumber.text.toString().trim()
                        for (i in 1..number.toInt()) {
                            outputText += "$text "

                        }

                    }
                    textRepeaterBinding.textShowView.text = outputText
                }

            }
            textRepeaterBinding.copyBtn.setOnClickListener {
                if (textRepeaterBinding.textShowView.text.trim().isEmpty()) {
                    shortShowToast(getString(R.string.chatcopy))
                } else {
                    textRepeaterBinding.textShowView.error = null
                    shortShowToast(getString(R.string.PatternCopy))
                    val clipboard =
                        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip =
                        ClipData.newPlainText(
                            getString(R.string.CopiedText),
                            textRepeaterBinding.textShowView.text.toString().trim()
                        )
                    clipboard.setPrimaryClip(clip)
                }
            }
            textRepeaterBinding.shareBtn.setOnClickListener {
                if (textRepeaterBinding.textShowView.text.trim().isEmpty()) {
                    shortShowToast(getString(R.string.Emojishare))
                } else {
                    textRepeaterBinding.textShowView.error = null
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.type = "text/plain"
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        textRepeaterBinding.textShowView.text.toString().trim()
                    )
                    startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
                }
            }
            textRepeaterBinding.cleartext.setOnClickListener {
                textRepeaterBinding.textShowView.text = ""

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
