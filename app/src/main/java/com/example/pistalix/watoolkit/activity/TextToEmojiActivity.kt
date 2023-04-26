package com.example.pistalix.watoolkit.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.KeyboardEmojiAdapter
import com.example.pistalix.watoolkit.databinding.ActivityTextToEmojiBinding
import com.example.pistalix.watoolkit.model.Emoji
import com.example.pistalix.watoolkit.utils.*

class TextToEmojiActivity : AppCompatActivity() {
    private val textToEmojiBinding: ActivityTextToEmojiBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_text_to_emoji)
    }
    private val emojiList: ArrayList<Emoji> = arrayListOf(
        Emoji(
            "A",
            "****\n*         *\n*         *\n****\n*         *\n*         *\n*         *"
        ),
        Emoji(
            "B",
            "*****\n*                 * \n*                 * \n*****\n*                 * \n*                 * \n*****"
        ),
        Emoji(
            "C",
            "   *****\n*                   *\n* \n* \n* \n* \n*                   *\n   *****"
        ),
        Emoji(
            "D",
            "****\n*           *\n*            *\n*            *\n*            *\n*           *\n****"
        ),
        Emoji("E", "***** \n* \n* \n***** \n* \n* \n*****"),
        Emoji("F", "***** \n* \n* \n**** \n* \n* \n*"),
        Emoji(
            "G",
            "   ****\n*                *\n*\n*     ***\n*                *\n*                *\n   ****"
        ),
        Emoji(
            "H",
            "*                  * \n*                  * \n*                  * \n******\n*                  * \n*                  * \n*                  *"
        ),
        Emoji(
            "I",
            "***** \n          * \n          * \n          * \n          * \n          * \n*****"
        ),
        Emoji(
            "J",
            "***** \n             * \n             * \n             * \n             * \n*       * \n   **"
        ),
        Emoji(
            "K",
            "*               * \n*          * \n*     * \n** \n*     * \n*          * \n*               *"
        ),
        Emoji("L", "* \n* \n* \n* \n* \n* \n*****"),
        Emoji(
            "M",
            "*                            * \n**                   ** \n*     *       *       * \n*           *             * \n*                            * \n*                            * \n*                            *"
        ),
        Emoji(
            "N",
            "*                        * \n**                    * \n*     *               * \n*          *          * \n*               *     * \n*                    ** \n*                        *"
        ),
        Emoji(
            "O",
            "   ****      \n*                  * \n*                  * \n*                  * \n*                  * \n*                  * \n     ****    "
        ),
        Emoji("P", "**** \n*              * \n*              * \n**** \n* \n* \n*"),
        Emoji(
            "Q",
            "    ****      \n*                   * \n*                   * \n*                   * \n*          *     * \n*              * * \n     ****\n                          *\n                             *"
        ),
        Emoji(
            "R",
            "**** \n*               * \n*               * \n**** \n*     * \n*          * \n*               *"
        ),
        Emoji(
            "S",
            "     **** \n* \n* \n     *** \n                  * \n                  * \n****"
        ),
        Emoji(
            "T",
            "***** \n         * \n         * \n         * \n         * \n         * \n         *"
        ),
        Emoji(
            "U",
            "*                       * \n*                       * \n*                       * \n*                       * \n*                       * \n*                       * \n*                       * \n     *****"
        ),
        Emoji(
            "V",
            "*                    * \n*                    * \n*                    * \n*                    * \n     *          * \n          * *\n             *"
        ),
        Emoji(
            "W",
            "*                     * \n*                     * \n*                     * \n*                     * \n*       *         * \n*       *         * \n      *         *"
        ),
        Emoji(
            "X",
            "*                         * \n     *               * \n          *     * \n               * \n          *     * \n     *               * \n*                         *"
        ),
        Emoji(
            "Y",
            "*                         * \n     *               * \n          *     * \n               * \n               * \n               * \n               *"
        ),
        Emoji(
            "Z",
            "***** \n                      * \n                  * \n             * \n         * \n     * \n******"
        ),
        Emoji("a", "  **  \n" + "*       *\n" + "*       *\n" + "*      *\n" + "  **   *"),
        Emoji(
            "b",
            "*\n" + "*\n" + "*\n" + "***\n" + "*     *\n" + "*     *\n" + "*     *\n" + "***"
        ),
        Emoji("c", "       ***\n" + "   *\n" + " *\n" + " *\n" + "   *\n" + "       ***"),
        Emoji(
            "d",
            "             *\n" + "             *\n" + "             *\n" + "   ***\n" + "*        *\n" + "*        *\n" + "   ***"
        ),
        Emoji("e", "    ***\n" + " *           *\n" + "****\n" + "*\n" + "  *\n" + "     ***"),
        Emoji(
            "f",
            "       **\n" + "    *      *\n" + "   *\n" + "***\n" + "   *\n" + "   *\n" + "   *"
        ),
        Emoji(
            "g",
            "   * *\n" + "*        *\n" + "*        *\n" + "  ** *\n" + "            *\n" + " *      *\n" + "   *  *"
        ),
        Emoji("h", "*\n" + "*\n" + "*\n" + "***\n" + "*       *\n" + "*       *\n" + "*       *"),
        Emoji("i", "*\n" + "\n" + "*\n" + "*\n" + "*\n" + "*\n" + "*"),
        Emoji(
            "j",
            "           *\n" + "\n" + "           *\n" + "           *\n" + "           *\n" + "*      *\n" + "   **"
        ),
        Emoji("k", "*\n" + "*     *\n" + "*   *\n" + "**\n" + "**\n" + "*    *\n" + "*       *"),
        Emoji("l", "*\n" + "*\n" + "*\n" + "*\n" + "*\n" + "*\n" + "*"),
        Emoji(
            "m",
            "   **         **\n" + " *       **      *\n" + " *         *         *\n" + " *         *         *\n" + " *         *         *\n" + " *         *         *"
        ),
        Emoji(
            "n",
            "  **     **\n" + "        **      *\n" + "          *         *\n" + "          *         *\n" + "          *         *\n" + "          *         *"
        ),
        Emoji(
            "o",
            "    * *\n" + " *        *\n" + "*          *\n" + "*          *\n" + " *        *\n" + "    * *"
        ),
        Emoji("p", "**\n" + "*    *\n" + "*    *\n" + "**\n" + "*\n" + "*\n" + "*"),
        Emoji(
            "q",
            "    **\n" + "*    *\n" + "*    *\n" + "    **\n" + "        *\n" + "        *\n" + "        *"
        ),
        Emoji("r", "*       *\n" + "*  *     *\n" + "**\n" + "*\n" + "*\n" + "*\n" + "*"),
        Emoji(
            "s",
            "   **\n" + "*      *\n" + "   *\n" + "      *\n" + "        *\n" + "*      *\n" + "   **"
        ),
        Emoji(
            "t",
            "    *\n" + "    *\n" + "***\n" + "    *\n" + "    *\n" + "     *    *\n" + "       **"
        ),
        Emoji(
            "u",
            "*             *\n" + "*             *\n" + "*             *\n" + " *            *\n" + "    ****\n" + "                    *"
        ),
        Emoji(
            "v",
            " *        *\n" + "  *      *\n" + "   *    *\n" + "    *  *\n" + "     **\n" + "       *"
        ),
        Emoji(
            "w",
            "*                *\n" + "*                *\n" + "*      *     *\n" + "*      *     *\n" + " * *  * *\n" + "   *          *"
        ),
        Emoji(
            "x",
            " *        *\n" + "     *  *\n" + "        *\n" + "        *\n" + "     *  *\n" + "  *       *"
        ),
        Emoji(
            "y",
            "*         *\n" + " *       *\n" + "    *  *\n" + "        *\n" + "      *\n" + "   *\n" + "*"
        ),
        Emoji("z", "****\n" + "           *\n" + "        *\n" + "      *\n" + "   *\n" + "****"),
        Emoji(
            "0",
            "   **\n" + "*      *\n" + "*      *\n" + "*      *\n" + "*      *\n" + "*      *\n" + "   **"
        ),
        Emoji(
            "1",
            "     *\n" + " **\n" + "     *\n" + "     *\n" + "     *\n" + "     *\n" + "***"
        ),
        Emoji(
            "2",
            "  * *\n" + "*     *\n" + "         *\n" + "       *\n" + "    *\n" + "  *\n" + "* * *"
        ),
        Emoji(
            "3",
            "***\n" + "           *\n" + "           *\n" + "  **\n" + "           *\n" + "           *\n" + "***"
        ),
        Emoji(
            "4",
            "         *\n" + "      * *\n" + "    *   *\n" + "  *     *\n" + "****\n" + "            *\n" + "            *"
        ),
        Emoji("5", "***\n" + "*\n" + "*\n" + "***\n" + "          *\n" + "          *\n" + "***"),
        Emoji("6", "***\n" + "*\n" + "*\n" + "***\n" + "*     *\n" + "*     *\n" + "***"),
        Emoji(
            "7",
            "****\n" + "              *\n" + "            *\n" + "          *\n" + "        *\n" + "      *\n" + "    *"
        ),
        Emoji(
            "8",
            "***\n" + "*     *\n" + "*     *\n" + "***\n" + "*     *\n" + "*     *\n" + "***"
        ),
        Emoji(
            "9",
            "***\n" + "*     *\n" + "*     *\n" + "***\n" + "         *\n" + "         *\n" + "***"
        )
    )
    lateinit var sharedads: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            sharedads = getSharedPreferences("ads", MODE_PRIVATE)
            if (sharedads.getBoolean("ads", true)) {
                CommonAds.loadBannerAds(this, textToEmojiBinding.adsContainer)
            } else {
                gone(textToEmojiBinding.adsContainer)
            }
            textToEmojiBinding.emojiTxt.movementMethod = ScrollingMovementMethod()
            textToEmojiBinding.toolBarLayout.name = "Text To Emoji"
            textToEmojiBinding.toolBarLayout.backImage.setOnClickListener { finish() }
            textToEmojiBinding.edChatText.addTextChangedListener(object : TextWatcher {
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
                        textToEmojiBinding.edChatTextL.error = getString(R.string.textValidation)
                    } else {
                        textToEmojiBinding.edChatTextL.error = null
                    }
                }
            })
            textToEmojiBinding.edChatText.onFocusChangeListener =
                View.OnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        gone(textToEmojiBinding.keyBoardView)
                    }
                }
            textToEmojiBinding.emojisTab1.text = String(Character.toChars(0x1F600))
            textToEmojiBinding.emojisTab2.text = String(Character.toChars(0x1F981))
            textToEmojiBinding.emojisTab3.text = String(Character.toChars(0x1F30E))
            textToEmojiBinding.emojisTab4.text = String(Character.toChars(0x1F6B3))
            textToEmojiBinding.emojisTab5.text = String(Character.toChars(0x26BD))
            textToEmojiBinding.emojisTab6.text = String(Character.toChars(0x1F3A7))
            textToEmojiBinding.emojisTab7.text = "\uD83C\uDDEE\uD83C\uDDF3"

            textToEmojiBinding.chooseEmojiBtn.setOnClickListener {
                hideSoftKeyboard(it)

                textToEmojiBinding.edChatText.clearFocus()
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        simley, flags,
                        textToEmojiBinding.edEmoji, false, textToEmojiBinding
                    )
                visible(textToEmojiBinding.keyBoardView)
            }
            textToEmojiBinding.emojisTab1.setOnClickListener {
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        simley,
                        flags,
                        textToEmojiBinding.edEmoji,
                        false,
                        textToEmojiBinding
                    )
            }
            textToEmojiBinding.emojisTab2.setOnClickListener {
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        nature,
                        flags,
                        textToEmojiBinding.edEmoji,
                        false,
                        textToEmojiBinding
                    )
            }
            textToEmojiBinding.emojisTab3.setOnClickListener {
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        travel,
                        flags,
                        textToEmojiBinding.edEmoji,
                        false,
                        textToEmojiBinding
                    )
            }
            textToEmojiBinding.emojisTab4.setOnClickListener {
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        symbol,
                        flags,
                        textToEmojiBinding.edEmoji,
                        false,
                        textToEmojiBinding
                    )
            }
            textToEmojiBinding.emojisTab5.setOnClickListener {
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        activities,
                        flags,
                        textToEmojiBinding.edEmoji,
                        false,
                        textToEmojiBinding
                    )
            }
            textToEmojiBinding.emojisTab6.setOnClickListener {
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        objects,
                        flags,
                        textToEmojiBinding.edEmoji,
                        false,
                        textToEmojiBinding
                    )
            }
            textToEmojiBinding.emojisTab7.setOnClickListener {
                textToEmojiBinding.rvEmojiView.adapter =
                    KeyboardEmojiAdapter(
                        simley,
                        flags,
                        textToEmojiBinding.edEmoji,
                        true,
                        textToEmojiBinding
                    )
            }



            textToEmojiBinding.transformEmojiBtn.setOnClickListener {
                hideSoftKeyboard(it)
                gone(textToEmojiBinding.keyBoardView)
                textToEmojiBinding.edChatText.clearFocus()
                if (textToEmojiBinding.edChatText.text.toString().trim().isEmpty()) {
                    textToEmojiBinding.edChatTextL.error = (getString(R.string.textValidation))
                } else {
                    textToEmojiBinding.edChatTextL.error = null
                    var outputText = ""
                    val text = textToEmojiBinding.edChatText.text.toString().trim().toString()
                    val emoji = textToEmojiBinding.edEmoji.text.toString().trim().toString()
                    for (element in text) {
                        val letter = element.toString()
                        for (j in emojiList.indices) {
                            if (emojiList[j].charName == letter) {
                                outputText =
                                    outputText + emojiList[j].pattern + "\n\n"
                                break
                            }
                        }
                    }
                    textToEmojiBinding.emojiTxt.text = outputText.replace("*", emoji)
                }
            }
            textToEmojiBinding.copyBtn.setOnClickListener {
                if (textToEmojiBinding.emojiTxt.text.trim().isEmpty()) {
                    shortShowToast(getString(R.string.chatcopy))
                } else {
                    textToEmojiBinding.emojiTxt.error = null
                    shortShowToast(getString(R.string.PatternCopy))
                    val clipboard =
                        getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                    val clip =
                        ClipData.newPlainText(
                            getString(R.string.CopiedText),
                            textToEmojiBinding.emojiTxt.text.toString().trim()
                        )
                    clipboard.setPrimaryClip(clip)
                }
            }
            textToEmojiBinding.shareBtn.setOnClickListener {
                if (textToEmojiBinding.emojiTxt.text.trim().isEmpty()) {
                    shortShowToast(getString(R.string.Emojishare))
                } else {
                    textToEmojiBinding.emojiTxt.error = null
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.type = "text/plain"
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        textToEmojiBinding.emojiTxt.text.toString().trim()
                    )
                    startActivity(Intent.createChooser(intent, getString(R.string.share_via)))
                }
            }
            textToEmojiBinding.cleartext.setOnClickListener {
                textToEmojiBinding.emojiTxt.text = ""

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (textToEmojiBinding.keyBoardView.isVisible) {
            gone(textToEmojiBinding.keyBoardView)
        }else{
            super.onBackPressed()

        }


    }

}