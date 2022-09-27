package com.example.newsapp


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.model.ArticlesItem
import com.example.newsapp.databinding.ItemNewsBinding

class newsAdapter(var items:List<ArticlesItem>?): RecyclerView.Adapter<newsAdapter.ViewHolder>() {

    class ViewHolder(val itemBinding:ItemNewsBinding)
        :RecyclerView.ViewHolder(itemBinding.root){
            fun bind(item:ArticlesItem?){
                itemBinding.item = item
                itemBinding.invalidateAll()
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_news,parent,false)
//        return ViewHolder(view)
        val viewBinding : ItemNewsBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context),R.layout.item_news,
                parent,false)
        return ViewHolder(viewBinding)
    }


    override fun getItemCount(): Int = items?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.bind(item)
    }

    fun changeData(data:List<ArticlesItem?>?) {
        items = data?.filterNotNull()
        notifyDataSetChanged()

    }
}