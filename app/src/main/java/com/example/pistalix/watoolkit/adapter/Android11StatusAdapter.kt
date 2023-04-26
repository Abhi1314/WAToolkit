package com.example.pistalix.watoolkit.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.activity.StatusPreviewActivity
import com.example.pistalix.watoolkit.utils.gone
import com.example.pistalix.watoolkit.utils.visible

class Android11StatusAdapter(
    var context: Context,
    var statusList: ArrayList<String>): RecyclerView.Adapter<Android11StatusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view:View=LayoutInflater.from(parent.context).inflate(R.layout.statusitem,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file: String = statusList[position]
        Glide.with(context).load(file).into(holder.image)
        if(file.endsWith(".mp4")){
            visible(holder.view)
        }
        else{
            gone(holder.view)
        }
        holder.image.setOnClickListener{

            val intent = Intent(context, StatusPreviewActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("isDownload",false)
            intent.putExtra("pos", position)
            intent.putExtra("key", statusList)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
      return statusList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView =itemView.findViewById(R.id.StatusImg)
        val view:ImageView=itemView.findViewById(R.id.view)
    }

}