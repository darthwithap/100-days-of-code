package com.darthwithap.fragmentssection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_fruits.*
import kotlinx.android.synthetic.main.fragment_fruits.view.*

class FruitsFragment : Fragment() {
    val fruits = arrayOf(
        "Guava", "Apple", "Banana",
        "Mango", "Papaya", "Kiwi",
        "Orange", "Watermelon", "Cherry"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fruits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val data = arguments?.getString("name") ?: "default data"
        tvBundleData.text = data
        view.lvFruits.adapter =
            ArrayAdapter(
                requireContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, fruits
            )
    }
}