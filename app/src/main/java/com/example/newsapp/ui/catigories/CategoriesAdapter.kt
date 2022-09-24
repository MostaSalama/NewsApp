package com.example.newsapp.ui.catigories

import android.annotation.SuppressLint
import android.os.Build
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.graphics.red
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Model.Category
import com.example.newsapp.R
import com.google.android.material.card.MaterialCardView

class CategoriesAdapter(val catigories:List<Category>):RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title)
        val image:ImageView = itemView.findViewById(R.id.image)
        val materialCard:MaterialCardView = itemView.findViewById(R.id.material_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(
                    if (viewType==LEFT_SIDE_VIEW_TYPE)
                        R.layout.left_side_category
                    else R.layout.right_side_category
                    ,parent,false)
        return ViewHolder(view)
    }

    val LEFT_SIDE_VIEW_TYPE = 10
    val RIGHT_SIDE_VIEW_TYPE = 20
    override fun getItemViewType(position: Int): Int {
        return if (position%2==0) LEFT_SIDE_VIEW_TYPE else RIGHT_SIDE_VIEW_TYPE
    }


    @SuppressLint("NewApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = catigories[position]
        holder.title.setText(item.titleId)
        holder.image.setImageResource(item.imageRes)
        holder.materialCard
            .setCardBackgroundColor(ContextCompat.getColor(holder.itemView.context,item.backgroundColorId))
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,item)
            }
        }
    }

    var onItemClickListener : OnItemClickListener?=null

    interface OnItemClickListener{
        fun onItemClick(pos:Int,category:Category)
    }

    override fun getItemCount(): Int {
        return catigories.size
    }
}