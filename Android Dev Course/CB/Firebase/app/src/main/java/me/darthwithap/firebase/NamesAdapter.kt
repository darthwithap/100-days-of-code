package me.darthwithap.firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_name_list.view.*

class NamesAdapter(private val names: ArrayList<String>) :
    RecyclerView.Adapter<NamesAdapter.NameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        return NameViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_name_list, parent, false
            )
        )
    }

    override fun getItemCount() = names.size

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(names[position])
    }

    inner class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(name: String) {
            itemView.tvName.text = name
        }
    }
}