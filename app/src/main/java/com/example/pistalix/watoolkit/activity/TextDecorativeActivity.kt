package com.example.pistalix.watoolkit.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.TextDecorAdapter
import com.example.pistalix.watoolkit.databinding.ActivityTextDecorativeBinding
import com.example.pistalix.watoolkit.model.Textdecorlist
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.hideSoftKeyboard

class TextDecorativeActivity : AppCompatActivity() {
    private val textDecorativeBinding: ActivityTextDecorativeBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.activity_text_decorative)
    }
    lateinit var sharedads: SharedPreferences
    private var decorlist : ArrayList<Textdecorlist> = arrayListOf(
        Textdecorlist("°¤*(¯´★`¯)*¤°•.", ".•°¤*(¯`★´¯)*¤°"),
        Textdecorlist("▁ ▂ ▄ ▅ ▆ ▇ █ ", "█ ▇ ▆ ▅ ▄ ▂ ▁"),
        Textdecorlist("░▒▓█►─═", "═─◄█▓▒░"),
        Textdecorlist("★·.·´¯`·.·★ ", "★·.·´¯`·.·★"),
        Textdecorlist("▌│█║▌║▌║ ", "║▌║▌║█│▌"),
        Textdecorlist("¤¸¸.•´¯`•¸¸.•..>> ", "<<..•.¸¸•´¯`•.¸¸¤"),
        Textdecorlist("«-(¯`v´¯)-« ", "»-(¯`v´¯)-»"),
        Textdecorlist("★·.·´¯`·.·★", "★·.·´¯`·.·★"),
        Textdecorlist(".•♫•♬• ", "•♬•♫•."),
        Textdecorlist("(¯´•._.• ", "•._.•´¯)"),
        Textdecorlist("ஜ۩۞۩ஜ ", "ஜ۩۞۩ஜ"),
        Textdecorlist("๑۞๑,¸¸,ø¤º°`°๑۩ ", "๑۩ ,¸¸,ø¤º°`°๑۞๑"),
        Textdecorlist("╰☆☆ ", "☆☆╮"),
        Textdecorlist("➶➶➶➶➶ ", "➷➷➷➷➷"),
        Textdecorlist("-漫~*'¨¯¨'*·舞~", "~舞*'¨¯¨'*·~漫-"),
        Textdecorlist("¸,ø¤º°`°º¤ø,¸ ", "¸,ø¤º°`°º¤ø,¸")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, textDecorativeBinding.adsContainer)
            } else {
                gone(textDecorativeBinding.adsContainer)
            }
            textDecorativeBinding.toolBarLayout.name="Text Decorative"
            textDecorativeBinding.toolBarLayout.backImage.setOnClickListener { finish() }
            textDecorativeBinding.edTxtDecor.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.toString().trim().isEmpty()) {
                        textDecorativeBinding.edTxtDecorL.error = getString(R.string.stylishfontvalid)
                    }else{
                        textDecorativeBinding.edTxtDecorL.error=null
                    }
                }
            })
            textDecorativeBinding.createBtn.setOnClickListener {
                hideSoftKeyboard(it)
                val text: String = textDecorativeBinding.edTxtDecor.text.toString().trim()
                if (text.isEmpty()) {
                    textDecorativeBinding.edTxtDecorL.error = getString(R.string.textValidation)
                } else {
                    textDecorativeBinding.edTxtDecorL.error = null
                    textDecorativeBinding.rvTextDecor.adapter =
                        TextDecorAdapter(this, text, decorlist)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}