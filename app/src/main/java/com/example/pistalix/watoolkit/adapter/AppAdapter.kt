package com.example.pistalix.watoolkit.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pistalix.watoolkit.R
import org.json.JSONArray
import org.json.JSONObject

class AppAdapter(var name : JSONArray): RecyclerView.Adapter<AppAdapter.ViewHolder>()
{
    lateinit var context1: Context

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val mainJson : JSONObject = name.getJSONObject(position)

        try
        {
            Glide.with(context1).load(mainJson.getString("Image")).placeholder(R.drawable.ic_placeholder).into(holder.appimage)
            holder.appname.text = mainJson.getString("App Name")
            holder.appname.isSelected = true

            holder.download.setOnClickListener{
                val pacakegname = mainJson.getString("Package Name")
                try {
                    context1.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$pacakegname")))
                } catch (anfe: android.content.ActivityNotFoundException) {
                    context1.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$pacakegname")))
                }
            }
        }catch (e:Exception){
            Toast.makeText(context1, "Error while Requesting", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return name.length()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.app_layout, parent, false)
        context1=parent.context
        return ViewHolder(itemView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var appimage: ImageView = itemView.findViewById(R.id.appimage)
        var appname: TextView = itemView.findViewById(R.id.appname)
        var download: TextView = itemView.findViewById(R.id.download)
    }
}