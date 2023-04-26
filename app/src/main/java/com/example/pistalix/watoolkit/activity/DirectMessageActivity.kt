package com.example.pistalix.watoolkit.activity

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.databinding.ActivityDirectMessageBinding
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.hideKeyBoard
import com.example.pistalix.watoolkit.utils.hideSoftKeyboard
import com.hbb20.CountryCodePicker.DialogEventsListener


class DirectMessageActivity : AppCompatActivity() {
    private val directMessageBinding: ActivityDirectMessageBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_direct_message)
    }
    lateinit var sharedads: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {


            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, directMessageBinding.adsContainer)
            } else {
                gone(directMessageBinding.adsContainer)
            }
            directMessageBinding.edTextMsg.setRawInputType(InputType.TYPE_CLASS_TEXT)
            directMessageBinding.toolBarLayout.name = "Direct Chat"
            directMessageBinding.toolBarLayout.backImage.setOnClickListener { finish() }
            directMessageBinding.edTextMsg.addTextChangedListener(object : TextWatcher {
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
                        directMessageBinding.edTextMsgL.error = getString(R.string.msgValidation)
                    } else {
                        directMessageBinding.edMobileNumberL.error = null
                        directMessageBinding.edTextMsgL.error = null
                    }
                }
            })
            directMessageBinding.edMobileNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    val mobileno = s.toString().trim()
                    if (mobileno.isEmpty()) {
                        directMessageBinding.edTextMsgL.error = null
                        directMessageBinding.edMobileNumberL.error =
                            getString(R.string.mobileNoValidation)
                    } else {
                        directMessageBinding.edTextMsgL.error = null
                        directMessageBinding.edMobileNumberL.error = null
                    }
                }
            })
            directMessageBinding.btSendMsg.setOnClickListener {
                val message: String = directMessageBinding.edTextMsg.text.toString().trim()
                val mobileno: String = directMessageBinding.edMobileNumber.text.toString().trim()
                val countrycode: String =
                    directMessageBinding.countryCode.selectedCountryCodeWithPlus
                val number = countrycode + mobileno
                if (mobileno.isEmpty()) {
                    directMessageBinding.edMobileNumberL.error =
                        getString(R.string.mobileNoValidation)
                } else if (message.isEmpty()) {
                    directMessageBinding.edMobileNumberL.error = null
                    directMessageBinding.edTextMsgL.error = getString(R.string.msgValidation)

                } else {
                    directMessageBinding.edMobileNumberL.error = null
                    directMessageBinding.edTextMsgL.error = null
                    hideKeyBoard(this)
                    openWhatsApp(message, number)
                }

            }

            directMessageBinding.countryCode.setDialogEventsListener(object : DialogEventsListener {
                override fun onCcpDialogOpen(dialog: Dialog?) {
                    Log.d("keyboard", directMessageBinding.countryCode.toString())
                    hideKeyBoard(this@DirectMessageActivity)
                }

                override fun onCcpDialogDismiss(dialogInterface: DialogInterface) {
                    //your code
                }

                override fun onCcpDialogCancel(dialogInterface: DialogInterface) {
                    //your code
                }
            })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun openWhatsApp(message: String, number: String) {
        val url = "https://api.whatsapp.com/send?phone=$number&text=$message"
        val send = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(send)
    }


}