package com.example.pistalix.watoolkit.adapter

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pistalix.watoolkit.R

class PageAdapter(
    private val arrayList: ArrayList<String>,
    val context: Context): RecyclerView.Adapter<PageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.pageview,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            if (arrayList[position].endsWith(".mp4")) {
                holder.imageholder.visibility = View.GONE
                holder.videoholder.visibility = View.VISIBLE
                holder.videoholder.setVideoURI(Uri.parse(arrayList[position]))
                holder.videoholder.visibility = View.VISIBLE
                holder.videoholder.setOnPreparedListener { mediaPlayer: MediaPlayer -> mediaPlayer.start() }
                holder.videoholder.setOnCompletionListener { mediaPlayer: MediaPlayer -> mediaPlayer.start() }
            } else {
                holder.imageholder.visibility = View.VISIBLE
                holder.videoholder.visibility = View.GONE
                Glide.with(this.context).load(arrayList[position]).placeholder(R.drawable.ic_placeholder).skipMemoryCache(true)
                    .into(holder.imageholder)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val imageholder:ImageView=itemView.findViewById(R.id.imageView)
        val videoholder:VideoView=itemView.findViewById(R.id.videoView)
    }

}