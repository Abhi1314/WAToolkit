package com.example.pistalix.watoolkit.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.activity.AnimatedDetailsActivity
import org.json.JSONArray

class AnimatedEmojiAdapter(val context: Context, private val Animatedlist: JSONArray) : RecyclerView.Adapter<AnimatedEmojiAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.emoji_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item=Animatedlist.getJSONObject(position)
        Log.d("Stikcerid",item.getString("Sticker_url"))
        Glide.with(context).load(item.getString("Sticker_url")).placeholder(R.drawable.ic_placeholder).into(holder.emojiview)
        holder.emojiview.setOnClickListener {
           val intent= Intent(context,AnimatedDetailsActivity::class.java)
           intent.putExtra("Sticker_url",item.getString("Sticker_url"))
            intent.putExtra("category", item.getString("Cat_name"))
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return  Animatedlist.length()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val emojiview: ImageView = itemView.findViewById(R.id.sticker_one)

    }
}