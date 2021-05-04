package me.darthwithap.covidtracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.item_state.view.*

class StateAdapter(private val list: List<StatewiseItem?>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val itemView: View?
        val holder: StateViewHolder
        if (convertView == null) {
            itemView = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_state, parent, false
            )
            holder = StateViewHolder(itemView)
            itemView.tag = holder
        } else {
            holder = convertView.tag as StateViewHolder
            list[position]?.let { holder.bindView(it) }
            itemView = convertView
        }

        return itemView
    }

    inner class StateViewHolder(private val itemView: View) {
        fun bindView(state: StatewiseItem) {
            with(itemView) {
                tvCnf.text = state.confirmed?.length?.let {
                    SpannableDelta(
                        itemView.context,
                        "${state.confirmed}\n\n↑${state.deltaconfirmed ?: 0}",
                        R.color.dark_red, it
                    )
                }
                tvActv.text = state.active?.length?.let {
                    SpannableDelta(itemView.context,
                        "${state.active}\n\n↑${state.deltadeaths?.toInt()?.let { it1 ->
                            state.deltarecovered?.toInt()?.let { it2 ->
                                state.deltaconfirmed?.toInt()?.minus(it1)
                                    ?.minus(it2)
                            }
                        } ?: 0}",
                        R.color.dark_blue, it)
                }
                tvRcvrd.text = state.recovered?.length?.let {
                    SpannableDelta(
                        itemView.context,
                        "${state.recovered}\n\n↑${state.deltarecovered ?: 0}",
                        R.color.dark_green, it
                    )
                }
                tvDcsd.text = state.deaths?.length?.let {
                    SpannableDelta(
                        itemView.context,
                        "${state.deaths}\n\n↑${state.deltadeaths ?: 0}",
                        R.color.dark_yellow, it
                    )
                }
                tvState.text = state.state
            }
        }
    }

    override fun getItem(position: Int) = list[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = list.size

}