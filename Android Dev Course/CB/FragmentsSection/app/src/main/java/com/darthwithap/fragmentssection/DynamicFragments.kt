package com.darthwithap.fragmentssection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_dynamic_fragments.*

class DynamicFragments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dynamic_fragments)

        val fragmentManager = supportFragmentManager
        val bundle = Bundle()
        val fruitsFragment = FruitsFragment()
        fruitsFragment.arguments = bundle


        bundle.putString("data", "This is my fruit bundle data!")

        btnFruits.setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.flFragContainer, fruitsFragment)
                .commit()
        }

        btnVegetables.setOnClickListener {
            fragmentManager.beginTransaction()
                .replace(R.id.flFragContainer, VegetablesFragment())
                .commit()
        }

        btnViewPager.setOnClickListener {
            startActivity(Intent(this, ViewPager::class.java))
        }
    }
}