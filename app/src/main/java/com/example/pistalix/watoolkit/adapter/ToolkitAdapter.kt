package com.example.pistalix.watoolkit.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.activity.*
import com.example.pistalix.watoolkit.model.ToolkitModel
import com.example.pistalix.watoolkit.sticker.StickerPackListActivity
import com.example.pistalix.watoolkit.utils.CommonAds
import com.example.pistalix.watoolkit.utils.longShowToast


class ToolkitAdapter(private val arrayList: ArrayList<ToolkitModel>, val context: Context) :
    RecyclerView.Adapter<ToolkitAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toolkit: ToolkitModel = arrayList[position]
        holder.toolNameTxt.setText(toolkit.toolName)
        holder.toolImg.setImageResource(toolkit.toolImage)

        holder.itemView.setOnClickListener {
            when (toolkit.toolPosition) {
                0 -> {
                    val intent = Intent(context, DeleteMsgActivity::class.java)
                    startActivity(intent, context)
                }
                1 -> {
                    val isWhatsappInstalled: Boolean = whatsappInstalledOrNot("com.whatsapp")
                    if (isWhatsappInstalled) {
                        val intent = Intent(context, DirectMessageActivity::class.java)
                        startActivity(intent, context)


                    } else {
                        context.longShowToast(context.getString(R.string.Appnotinstalled))
                    }
                }
                2 -> {
                    val intent = Intent(context, StatusSaverActivity::class.java)
                    startActivity(intent, context)
                }
                3 -> {
                    val intent = Intent(context, ShakeDetectionActivity::class.java)
                    startActivity(intent, context)
                }
                4 -> {
                    val intent = Intent(context, StylishFontActivity::class.java)
                    startActivity(intent, context)
                }
                5 -> {
                    val intent = Intent(context, StickerPackListActivity::class.java)
                    startActivity(intent, context)
                }
                6 -> {
                    val intent = Intent(context, TextDecorativeActivity::class.java)
                    startActivity(intent, context)
                }
                7 -> {
                    val intent = Intent(context, TextToEmojiActivity::class.java)
                    startActivity(intent, context)
                }
                8 -> {
                    val intent = Intent(context, AnimatedEmojiActivity::class.java)
                    startActivity(intent, context)
                }
                9 -> {
                    val intent = Intent(context, TextRepeaterActivity::class.java)
                    startActivity(intent, context)
                }

            }
        }
    }

    private fun startActivity(intent: Intent, context: Context) {

        if (MainActivity.click) {
            context.startActivity(intent)
            MainActivity.click = false
        } else {
            MainActivity.click = true
            if (CommonAds.mInterstitialAd != null) {

                CommonAds.showInterstitialAds(context as Activity, intent, false)
            } else {
                context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    private fun whatsappInstalledOrNot(uri: String): Boolean {
        val pm = context.packageManager
        var app_installed = false
        app_installed = try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
        return app_installed
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var toolNameTxt: TextView = itemView.findViewById(R.id.toolNameTxt)
        var toolImg: ImageView = itemView.findViewById(R.id.toolImg)

    }


}

