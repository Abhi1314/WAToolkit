package com.example.pistalix.watoolkit.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pistalix.watoolkit.R

class FontAdapter(
    val context: Context,
    private val edFont: String,
    private val fontStyleList: ArrayList<ArrayList<String>>
) : RecyclerView.Adapter<FontAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.font_itemlist, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var fontOutputStr = ""
        for (element in edFont) {
            if (element.isDigit()) {
                fontOutputStr += element
            } else {
                fontOutputStr += if(element == ' '){
                    " "
                }else if(element.code.mod(97) > 25){
                    element
                } else {
                    fontStyleList[position][element.code.mod(97)]
                }
            }
        }

        holder.inputText.text = fontOutputStr
        holder.txtWhatsapp.setOnClickListener{
            val intent = Intent()
            intent.action = "android.intent.action.SEND"
            intent.putExtra("android.intent.extra.TEXT", holder.inputText.text)
            intent.setPackage("com.whatsapp")
            intent.type = "text/plain"
            context.startActivity(intent)
        }
        holder.txtShare.setOnClickListener{
            val intent2 = Intent()
            intent2.action = Intent.ACTION_SEND
            intent2.type = "text/plain"
            intent2.putExtra(Intent.EXTRA_TEXT, holder.inputText.text)
            context.startActivity(Intent.createChooser(intent2, "share via"))
        }
        holder.txtCopy.setOnClickListener {
            (holder.itemView.context
                .getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager)
                .setPrimaryClip(
                    ClipData.newPlainText(
                        "stylish text",
                        holder.inputText.text
                    )
                )
            Toast.makeText(holder.itemView.context, "Text Copied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return fontStyleList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var inputText: TextView = itemView.findViewById(R.id.edStylishText)
        var txtShare: ImageView = itemView.findViewById(R.id.iv_share)
        var txtCopy: ImageView = itemView.findViewById(R.id.iv_copy)
        var txtWhatsapp: ImageView = itemView.findViewById(R.id.iv_whatsapp)

    }
}