package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.Model.ArticlesItem
import com.example.newsapp.Model.NewsResponse

class newsAdapter(var items:List<ArticlesItem>?): RecyclerView.Adapter<newsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val title:TextView = itemView.findViewById(R.id.title)
        val author:TextView = itemView.findViewById(R.id.author)
        val dateTime:TextView = itemView.findViewById(R.id.date_time)
        val image:ImageView = itemView.findViewById(R.id.image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news,parent,false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int = items?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.title.text = item?.title
        holder.author.text = item?.author
        holder.dateTime.text = item?.publishedAt
        Glide.with(holder.itemView)
            .load(item?.urlToImage)
            .into(holder.image)
    }

    fun changeData(data:List<ArticlesItem?>?) {

        items = data?.filterNotNull()
        notifyDataSetChanged()


    }
}