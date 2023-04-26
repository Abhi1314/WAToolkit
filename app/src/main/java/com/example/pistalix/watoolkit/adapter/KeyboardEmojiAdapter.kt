package com.example.pistalix.watoolkit.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.databinding.ActivityTextToEmojiBinding
import com.example.pistalix.watoolkit.utils.gone

class KeyboardEmojiAdapter(
    private val emojilist: ArrayList<Int>,
    private val emojiFlagList: ArrayList<String>,
    val editText: com.google.android.material.textfield.TextInputEditText,
    val isFlag: Boolean,
    val customKeyboardDialog: ActivityTextToEmojiBinding
) : RecyclerView.Adapter<KeyboardEmojiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.emoji_item_view, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return if (isFlag) {
            emojiFlagList.size
        } else {
            emojilist.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (isFlag) {
            holder.emojiview.text = emojiFlagList[position]
        } else {
            holder.emojiview.text = String(Character.toChars(emojilist[position]))
        }
        holder.itemView.setOnClickListener {
            if (isFlag) {
                editText.setText(emojiFlagList[position])
            } else {
                editText.setText(String(Character.toChars(emojilist[position])))
            }
            gone(customKeyboardDialog.keyBoardView)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emojiview = itemView.findViewById<TextView>(R.id.emojiItemView)

    }

}