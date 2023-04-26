package com.example.pistalix.watoolkit.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.activity.AnimatedListActivity
import com.google.android.material.card.MaterialCardView
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class AnimatedAdapter(
    val context: Context,
    private val emojiList: JSONArray,
): RecyclerView.Adapter<AnimatedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.animated_emoji_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return emojiList.length()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val randomcolor = Random()
        val color = Color.argb(255, randomcolor.nextInt(255), randomcolor.nextInt(255), randomcolor.nextInt(255))
        val item= emojiList.getJSONObject(position)
        holder.seemore.setOnClickListener {
            val intent = Intent(context, AnimatedListActivity::class.java)
            intent.putExtra("Sheetid", item.getString("Sheet_id"))
            intent.putExtra("category", item.getString("Cat_name"))
            context.startActivity(intent)
        }
        holder.relative.backgroundTintList = ColorStateList.valueOf(color)
        holder.cardview.strokeColor = color
        Log.d("sheetresponse",item.getString("Sheet_id"))
        holder.emojiname.text=item.getString("Cat_name")
        val sheet_id=item.getString("Sheet_id")
        val item1= JSONObject(sheet_id)
        val itemarray=item1.getJSONArray("Sheet1")
        for(i in 0..3){
            val item2=itemarray.getJSONObject(i)
            if(i==0){
                Glide.with(context).load(item2.getString("Sticker_url")).placeholder(R.drawable.ic_placeholder).into(holder.emojiview1)
            }
            if(i==1){
                Glide.with(context).load(item2.getString("Sticker_url")).placeholder(R.drawable.ic_placeholder).into(holder.emojiview2)

            }
            if(i==2){
                Glide.with(context).load(item2.getString("Sticker_url")).placeholder(R.drawable.ic_placeholder).into(holder.emojiview3)

            }
            if(i==3){
                Glide.with(context).load(item2.getString("Sticker_url")).placeholder(R.drawable.ic_placeholder).into(holder.emojiview4)

            }

        }

    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val emojiview1:ImageView=itemView.findViewById(R.id.sticker_one)
        val emojiview2:ImageView=itemView.findViewById(R.id.sticker_two)
        val emojiview3:ImageView=itemView.findViewById(R.id.sticker_three)
        val emojiview4:ImageView=itemView.findViewById(R.id.sticker_four)
        val seemore: ImageView = itemView.findViewById(R.id.seeMore)
        val emojiname:TextView=itemView.findViewById(R.id.emojiName)
        val cardview:MaterialCardView=itemView.findViewById(R.id.card_view)
        val relative:RelativeLayout=itemView.findViewById(R.id.relative_1)
    }




}

