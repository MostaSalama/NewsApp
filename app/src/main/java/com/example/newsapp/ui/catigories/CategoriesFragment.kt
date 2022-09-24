package com.example.newsapp.ui.catigories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.Model.Category
import com.example.newsapp.R

class CategoriesFragment : Fragment() {

    val categoriesList = listOf(
        Category("sports",R.drawable.sports,R.string.sports,R.color.red),
        Category("technology",R.drawable.politics,R.string.technology,R.color.blue),
        Category("health",R.drawable.health,R.string.health,R.color.pink),
        Category("business",R.drawable.bussines,R.string.business,R.color.light_brown),
        Category("general",R.drawable.environment,R.string.general,R.color.light_blue),
        Category("science",R.drawable.science,R.string.science,R.color.yellow)
    )
    val adapter = CategoriesAdapter(categoriesList)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories,container,false)
    }

    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)
        adapter.onItemClickListener = object :CategoriesAdapter.OnItemClickListener{
            override fun onItemClick(pos: Int, category: Category) {
                onCategoryClickListener?.onCategoryClick(category)
            }
        }
        recyclerView.adapter = adapter
    }

    var onCategoryClickListener:OnCategoryClickListener?=null
    interface OnCategoryClickListener{
        fun onCategoryClick(category:Category)
    }
}