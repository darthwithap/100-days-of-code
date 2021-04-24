package me.darthwithap.todoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryTodoAdapter(private val historyTodos: List<HistoryTodoModel>) :
    RecyclerView.Adapter<HistoryTodoAdapter.HistoryTodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryTodoViewHolder {
        return HistoryTodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo, parent, false)
        )
    }

    override fun getItemCount() = historyTodos.size

    override fun onBindViewHolder(holder: HistoryTodoViewHolder, position: Int) {
        holder.bind(historyTodos[position])
    }

    override fun getItemId(position: Int) = historyTodos[position].id

    inner class HistoryTodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(historyTodoModel: HistoryTodoModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.category_colors)
                val color = colors[TaskActivity.categories.indexOf(historyTodoModel.category)]
                vColorTag.setBackgroundColor(color)
                tvTitle.text = historyTodoModel.title
                tvDescription.text = historyTodoModel.description
                tvCategory.text = historyTodoModel.category
                tvDate.text = SimpleDateFormat(resources.getString(R.string.date_format))
                    .format(Date(historyTodoModel.date))
                tvTime.text = SimpleDateFormat(resources.getString(R.string.time_format))
                    .format(Date(historyTodoModel.time))
            }
        }
    }
}