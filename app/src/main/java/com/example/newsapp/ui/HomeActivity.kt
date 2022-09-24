package com.example.newsapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.newsapp.Model.Category
import com.example.newsapp.R
import com.example.newsapp.ui.catigories.CategoriesFragment
import com.example.newsapp.ui.news.NewsFragment


class HomeActivity : AppCompatActivity() {
    lateinit var drawerButton:ImageView
    lateinit var drawerLayout: DrawerLayout
    lateinit var categories: View
    lateinit var settings: View


    val categoriesFragment = CategoriesFragment()
    val settingsFragment = SettingsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        pushFragment(categoriesFragment)
        categoriesFragment.onCategoryClickListener = object : CategoriesFragment.OnCategoryClickListener{
            override fun onCategoryClick(category: Category) {
                pushFragment(NewsFragment.getInstance(category),true)
            }
        }
        }

    override fun onResume() {
        super.onResume()
        categoriesFragment.onCategoryClickListener = object : CategoriesFragment.OnCategoryClickListener{
            override fun onCategoryClick(category: Category) {
                pushFragment(NewsFragment.getInstance(category),true)
            }
        }
    }

    private fun initView() {
        drawerButton = findViewById(R.id.menu_button)
        drawerLayout = findViewById(R.id.drawer_layout)
        categories = findViewById(R.id.categories)
        settings = findViewById(R.id.settings)

        drawerButton.setOnClickListener{
            drawerLayout.open()
    }
        settings.setOnClickListener {
            pushFragment(settingsFragment)
        }
        categories.setOnClickListener {
            pushFragment(categoriesFragment)
            categoriesFragment.onCategoryClickListener = object : CategoriesFragment.OnCategoryClickListener{
                override fun onCategoryClick(category: Category) {
                    pushFragment(NewsFragment(),true)
                }
            }
        }

}

    fun pushFragment(fragment:Fragment,addToBackStack:Boolean=false) {

        supportFragmentManager.commit {
            if (addToBackStack) {
                addToBackStack("")
            }
            setCustomAnimations(
                androidx.transition.R.anim.abc_fade_in,
                androidx.transition.R.anim.abc_fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out

            )
            replace(R.id.fragment_container,fragment)
        }
        drawerLayout.close()

    }
}