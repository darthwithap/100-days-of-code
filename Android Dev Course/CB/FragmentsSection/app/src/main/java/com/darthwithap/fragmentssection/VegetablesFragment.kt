package com.darthwithap.fragmentssection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_vegetables.view.*

class VegetablesFragment : Fragment() {

    val vegetables = arrayOf(
        "Potato", "Onion", "Tomato",
        "Lettuce", "Carrot", "Capsicum",
        "Ginger", "Garlic", "Pumpkin"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.fragment_vegetables, container, false)
        fragmentView.lvVegetables.adapter = context?.let {
            ArrayAdapter(
                it, android.R.layout.simple_list_item_1,
                android.R.id.text1, vegetables
            )
        }


        return fragmentView
    }
}