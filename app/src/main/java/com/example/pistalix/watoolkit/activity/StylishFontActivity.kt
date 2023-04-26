package com.example.pistalix.watoolkit.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.FontAdapter
import com.example.pistalix.watoolkit.databinding.ActivityStylishFontBinding
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.hideSoftKeyboard

class StylishFontActivity : AppCompatActivity() {
    private val stylishFontBinding:ActivityStylishFontBinding by lazy{
        DataBindingUtil.setContentView(this,R.layout.activity_stylish_font)
    }

    private val fontStyleList :ArrayList<ArrayList<String>> = arrayListOf(
        arrayListOf("ⓐ", "ⓑ", "ⓒ", "ⓓ", "ⓔ", "ⓕ", "ⓖ", "ⓗ", "ⓘ", "ⓙ", "ⓚ", "ⓛ", "ⓜ", "ⓝ", "ⓞ", "ⓟ", "ⓠ", "ⓡ", "ⓢ", "ⓣ", "ⓤ", "ⓥ", "ⓦ", "ⓧ", "ⓨ", "ⓩ"),
        arrayListOf("Д", "þ", "¢", "Ð", "3", "ƒ", "g", "ђ", "î", "j", "k", "ℓ", "м", "₪", "ø", "Þ", "Q", "Я", "§", "†", "û", "√", "w", "×", "¥", "ž"),
        arrayListOf("ค", "๒", "ς", "๔", "є", "Ŧ", "ﻮ", "ђ", "เ", "ן", "к", "l", "๓", "ภ", "๏", "ק", "ợ", "г", "ร", "t", "ย", "ש", "ฬ", "ץ", "א", "z"),
        arrayListOf("ª", "b", "¢", "Þ", "È", "F", "♀", "Ĥ", "Î", "j", "Κ", "¦", "∞", "η", "◊", "p", "Õ", "r", "S", "⊥", "µ", "√", "w", "×", "ý", "z"),
        arrayListOf("ά", "в", "ς", "∂", "έ", "ғ", "ģ", "ħ", "ί", "ј", "ķ", "Ļ", "м", "ή", "ό", "ρ", "q", "ŕ", "ş", "ţ", "ù", "ν", "ώ", "x", "ч", "ž"),
        arrayListOf("┌a┐", "┌b┐", "┌c┐", "┌d┐", "┌e┐", "┌f┐", "┌g┐", "┌h┐", "┌i┐", "┌j┐", "┌k┐", "┌l┐", "┌m┐", "┌n┐", "┌o┐", "┌p┐", "┌q┐", "┌r┐", "┌s┐", "┌t┐", "┌u┐", "┌v┐", "┌w┐", "┌x┐", "┌y┐", "┌z┐"),
        arrayListOf("α", "в", "¢", "∂", "є", "f", "g", "н", "ι", "נ", "к", "ℓ", "м", "и", "σ", "ρ", "q", "я", "ѕ", "т", "υ", "ν", "ω", "χ", "у", "z"),
        arrayListOf("a_", "b_", "c_", "d_", "e_", "f_", "g_", "h_", "i_", "j_", "k_", "l_", "m_", "n_", "o_", "p_", "q_", "r_", "s_", "t_", "u_", "v_", "w_", "x_", "y_", "z_"),
        arrayListOf("Λ", "B", "C", "D", "Σ", "F", "G", "Ή", "I", "J", "K", "L", "M", "П", "Ө", "P", "Q", "Я", "S", "Ŧ", "Ц", "V", "Щ", "X", "Y", "Z"),
        arrayListOf("Λ", "Þ", "⊂", "Ð", "ξ", "∫", "g", "∏", "¡", "j", "k", "l", "m", "∩", "♡", "p", "σ", "®", "§", "t", "∪", "ν", "w", "×", "ÿ", "2"),
        arrayListOf("𝘼", "в", "c", "d", "e", "ғ", "g", "н", "ι", "j", "ĸ", "l", "м", "n", "o", "p", "q", "r", "ѕ", "т", "υ", "v", "w", "х", "y", "z"),
        arrayListOf("4", "8", "(", "d", "3", "f", "9", "h", "!", "j", "k", "1", "m", "n", "0", "p", "q", "r", "5", "7", "u", "v", "w", "x", "y", "2"),
        arrayListOf("Æ", "þ", "©", "Ð", "∃", "ζ", "∉", "Η", "Ї", "¿", "¤", "∠", "m", "Ñ", "Θ", "¶", "Ø", "Ґ", "Š", "τ", "υ", "¥", "w", "χ", "y", "ž"),
        arrayListOf("α", "в", "c", "∂", "ε", "ғ", "g", "н", "ι", "נ", "к", "ℓ", "м", "η", "σ", "ρ", "q", "я", "s", "т", "υ", "v", "ω", "x", "ү", "z"),
        arrayListOf("ä", "b", "ċ", "d", "ë", "f", "ġ", "h", "ï", "j", "k", "l", "m", "n", "ö", "p", "q", "r", "s", "t", "ü", "v", "w", "x", "ÿ", "ż"),
        arrayListOf("α", "в", "¢", "∂", "є", "ƒ", "g", "н", "ι", "נ", "к", "ℓ", "м", "η", "σ", "ρ", "q", "я", "ѕ", "т", "υ", "ν", "ω", "χ", "у", "z"),
        arrayListOf("å", "ß", "¢", "Ð", "ê", "£", "g", "h", "ï", "j", "k", "l", "m", "ñ", "ð", "þ", "q", "r", "§", "†", "µ", "v", "w", "x", "¥", "z"),
        arrayListOf("Á", "ß", "Č", "Ď", "Ĕ", "Ŧ", "Ğ", "Ĥ", "Ĩ", "Ĵ", "Ķ", "Ĺ", "M", "Ń", "Ő", "P", "Q", "Ŕ", "Ś", "Ť", "Ú", "V", "Ŵ", "Ж", "Ŷ", "Ź"),
        arrayListOf("a", "b", "ς", "d", "є", "Ŧ", "ﻮ", "ђ", "i", "j", "к", "l", "m", "n", "o", "ק", "ợ", "г", "s", "t", "u", "ש", "w", "ץ", "א", "z"),
        arrayListOf("[-a-]", "[-b-]", "[-c-]", "[-d-]", "[-e-]", "[-f-]", "[-g-]", "[-h-]", "[-i-]", "[-j-]", "[-k-]", "[-l-]", "[-m-]", "[-n-]", "[-o-]", "[-p-]", "[-q-]", "[-r-]", "[-s-]", "[-t-]", "[-u-]", "[-v-]", "[-w-]", "[-x-]", "[-y-]", "[-z-]"),
        arrayListOf("å", "β", "ç", "ď", "£", "ƒ", "ğ", "Ћ", "!", "j", "ķ", "Ł", "๓", "ñ", "¤", "ק", "ợ", "ř", "§", "†", "µ", "√", "Ψ", "×", "ÿ", "ž"),
        arrayListOf("ą", "β", "č", "ď", "€", "ƒ", "δ", "Ђ", "ί", "j", "Ќ", "ℓ", "๓", "ŋ", "๏", "ρ", "ợ", "я", "$", "ţ", "µ", "ѵ", "ώ", "ж", "¥", "ź"),
        arrayListOf("Ã", "β", "Č", "Ď", "Ẹ", "₣", "Ğ", "Ĥ", "Į", "Ĵ", "Ќ", "Ĺ", "ℳ", "Ň", "Ỗ", "Ҏ", "Q", "Ř", "Ŝ", "Ť", "Ụ", "V", "Ŵ", "Ж", "Ў", "Ż"),
        arrayListOf("Ä", "B", "Ċ", "Đ", "Ë", "₣", "Ġ", "Ħ", "Ï", "Ĵ", "Ķ", "Ļ", "M", "Ņ", "Ö", "P", "Ҩ", "Ŗ", "Ś", "Ť", "Ů", "V", "Ŵ", "X", "Ÿ", "Ź"),
        arrayListOf("ɐ", "q", "ɔ", "p", "ǝ", "ɟ", "ƃ", "ɥ", "ᴉ", "ɾ", "ʞ", "l", "ɯ", "u", "o", "d", "b", "ɹ", "s", "ʇ", "n", "ʌ", "ʍ", "x", "ʎ", "z"),
        arrayListOf("∀", "ᗺ", "Ͻ", "ᗭ", "Ǝ", "Ⅎ", "⅁", "H", "I", "ſ", "Ʞ", "Ꞁ", "ꟽ", "N", "O", "Ԁ", "Ό", "Ꞟ", "S", "⊥", "ᑎ", "Λ", "M", "X", "ʎ", "Z"),
        arrayListOf("ᵃ", "ᵇ", "ᶜ", "ᵈ", "ᵉ", "ᶠ", "ᵍ", "ʰ", "ⁱ", "ʲ", "ᵏ", "ˡ", "ᵐ", "ⁿ", "ᵒ", "ᵖ", "ᵠ", "ʳ", "ˢ", "ᵗ", "ᵘ", "ᵛ", "ʷ", "ˣ", "ʸ", "ᶻ"),
        arrayListOf("ᴬ", "ᴮ", "ᶜ", "ᴰ", "ᴱ", "ᶠ", "ᴳ", "ᴴ", "ᴵ", "ᴶ", "ᴷ", "ᴸ", "ᴹ", "ᴺ", "ᴼ", "ᴾ", "ᵠ", "ᴿ", "ˢ", "ᵀ", "ᵁ", "ⱽ", "ᵂ", "ˣ", "ʸ", "ᶻ"),
        arrayListOf("ᴀ", "ʙ", "ᴄ", "ᴅ", "ᴇ", "ғ", "ɢ", "ʜ", "ɪ", "ᴊ", "ᴋ", "ʟ", "ᴍ", "ɴ", "ᴏ", "ᴘ", "ϙ", "ʀ", "s", "ᴛ", "ᴜ", "ᴠ", "ᴡ", "x", "ʏ", "ᴢ"),
        arrayListOf("卂", "乃", "匚", "ⅅ", "乇", "千", "Ꮆ", "卄", "丨", "丿", "Ҡ", "ㄥ", "爪", "几", "ㄖ", "卩", "Ɋ", "尺", "丂", "ㄒ", "凵", "ᐯ", "山", "乂", "ㄚ", "乙"),
        arrayListOf("α", "ɓ", "c", "∂", "ε", "ƒ", "ɠ", "ɦ", "เ", "ʝ", "ҡ", "ℓ", "ɱ", "ɳ", "σ", "ρ", "φ", "ɾ", "ร", "ƭ", "µ", "ѵ", "ω", "א", "ყ", "ƶ"),
        arrayListOf("ᗅ", "ℬ", "ℂ", "ⅅ", "ℰ", "ℱ", "ℊ", "ℍ", "ⅈ", "ℐ", "K", "ℒ", "ℳ", "ℕ", "⌾", "ℙ", "ℚ", "ℛ", "Տ", "ᝨ", "Ⴎ", "Ꮙ", "ᗯ", "ᝣ", "ℽ", "ℤ"),
        arrayListOf("ꍏ", "ꌃ", "ꉓ", "ꀸ", "ꍟ", "ꎇ", "ꁅ", "ꃅ", "ꀤ", "ꀭ", "ꀘ", "꒒", "ꎭ", "ꈤ", "ꂦ", "ᖘ", "ꆰ", "ꋪ", "ꌗ", "꓄", "ꀎ", "ᐯ", "ꅏ", "ꊼ", "ꌩ", "ꁴ"),
        arrayListOf("ᗩ", "ᗷ", "ᑕ", "ᗞ", "ᗴ", "ᖴ", "Ꮐ", "ᕼ", "Ꮖ", "ᒍ", "Ꮶ", "し", "ᗰ", "ᑎ", "ᝪ", "ᑭ", "ᑫ", "ᖇ", "ᔑ", "Ꭲ", "ᑌ", "ᐯ", "ᗯ", "᙭", "Ꭹ", "Ꮓ"),
        arrayListOf("α", "ճ", "c", "ժ", "ҽ", "բ", "ց", "հ", "í", "յ", "k", "l", "ต", "ղ", "օ", "թ", "զ", "ɾ", "s", "Ե", "մ", "ѵ", "ա", "x", "վ", "z"),
        arrayListOf("ค", "๒", "ς", "๔", "є", "Ŧ", "ﻮ", "ђ", "เ", "ן", "к", "l", "๓", "ภ", "๏", "ק", "ợ", "г", "ร", "t", "ย", "ש", "ฬ", "ץ", "א", "z"),
        arrayListOf("α", "ϐ", "ϲ", "∂", "є", "ƒ", "g", "н", "ι", "נ", "κ", "ℓ", "м", "и", "ο", "ρ", "գ", "я", "ѕ", "τ", "υ", "ν", "ω", "ϰ", "γ", "z"),
        arrayListOf("♬", "ᖲ", "¢", "ᖱ", "៩", "⨏", "❡", "Ϧ", "ɨ", "ɉ", "ƙ", "ɭ", "៣", "⩎", "០", "ᖰ", "ᖳ", "Ʀ", "ន", "Ƭ", "⩏", "⩔", "Ɯ", "✗", "ƴ", "Ȥ"),
        arrayListOf("𝒶", "𝒷", "𝒸", "𝒹", "ℯ", "𝒻", "ℊ", "𝒽", "𝒾", "𝒿", "𝓀", "𝓁", "𝓂", "𝓃", "ℴ", "𝓅", "𝓆", "𝓇", "𝓈", "𝓉", "𝓊", "𝓋", "𝓌", "𝓍", "𝓎", "𝓏"),
        arrayListOf("𝒜", "ℬ", "𝒞", "𝒟", "ℰ", "ℱ", "𝒢", "ℋ", "ℐ", "𝒥", "𝒦", "ℒ", "ℳ", "𝒩", "𝒪", "𝒫", "𝒬", "ℛ", "𝒮", "𝒯", "𝒰", "𝒱", "𝒲", "𝒳", "𝒴", "𝒵"),
        arrayListOf("𝕒", "𝕓", "𝕔", "𝕕", "𝕖", "𝕗", "𝕘", "𝕙", "𝕚", "𝕛", "𝕜", "𝕝", "𝕞", "𝕟", "𝕠", "𝕡", "𝕢", "𝕣", "𝕤", "𝕥", "𝕦", "𝕧", "𝕨", "𝕩", "𝕪", "𝕫"),
        arrayListOf("𝔸", "𝔹", "ℂ", "𝔻", "𝔼", "𝔽", "𝔾", "ℍ", "𝕀", "𝕁", "𝕂", "𝕃", "𝕄", "ℕ", "𝕆", "ℙ", "ℚ", "ℝ", "𝕊", "𝕋", "𝕌", "𝕍", "𝕎", "𝕏", "𝕐", "ℤ"),
        arrayListOf("𝔞", "𝔟", "𝔠", "𝔡", "𝔢", "𝔣", "𝔤", "𝔥", "𝔦", "𝔧", "𝔨", "𝔩", "𝔪", "𝔫", "𝔬", "𝔭", "𝔮", "𝔯", "𝔰", "𝔱", "𝔲", "𝔳", "𝔴", "𝔵", "𝔶", "𝔷"),
        arrayListOf("𝔄", "𝔅", "ℭ", "𝔇", "𝔈", "𝔉", "𝔊", "ℌ", "ℑ", "𝔍", "𝔎", "𝔏", "𝔐", "𝔑", "𝔒", "𝔓", "𝔔", "ℜ", "𝔖", "𝔗", "𝔘", "𝔙", "𝔚", "𝔛", "𝔜", "ℨ")
    )
    lateinit var sharedads: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, stylishFontBinding.adsContainer)
            } else {
                gone(stylishFontBinding.adsContainer)
            }
            stylishFontBinding.toolBarLayout.name="Stylish Font"
            stylishFontBinding.toolBarLayout.backImage.setOnClickListener { finish() }
            stylishFontBinding.edFontInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (s.toString().trim().isEmpty()) {
                        stylishFontBinding.fontInputL.error = getString(R.string.stylishfontvalid)
                    }else{
                        stylishFontBinding.fontInputL.error=null
                    }
                }
            })
            stylishFontBinding.submit.setOnClickListener {
                hideSoftKeyboard(it)

                if (stylishFontBinding.edFontInput.text.toString().trim().isNotEmpty()) {
                    stylishFontBinding.fontInputL.error = null
                    stylishFontBinding.rvFontList.adapter = FontAdapter(
                        this,
                        stylishFontBinding.edFontInput.text.toString().trim().lowercase(),
                        fontStyleList
                    )
                } else {
                    stylishFontBinding.fontInputL.error = "Please enter the text.."
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


}