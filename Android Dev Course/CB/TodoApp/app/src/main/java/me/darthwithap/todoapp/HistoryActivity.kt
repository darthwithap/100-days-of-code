package me.darthwithap.todoapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private var historyTodos = arrayListOf<HistoryTodoModel>(
        HistoryTodoModel(
        "Sample", "Descr", "Finance", System.currentTimeMillis(), System.currentTimeMillis(), 1213)
    )
    private var historyTodoAdapter = HistoryTodoAdapter(historyTodos)

    val db by lazy {
        TodoDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(historyToolbar)

        initSwipe()

        db.historyTodoDao().getHistoryTask().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                historyTodos.clear()
                historyTodos.addAll(it)
                historyTodoAdapter.notifyDataSetChanged()
            } else {
                historyTodos.clear()
                historyTodoAdapter.notifyDataSetChanged()
            }
        })

        rvHistoryTodos.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
            adapter = historyTodoAdapter
        }
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper
        .SimpleCallback(
            0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onChildDraw(
                canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val paint = Paint()

                    val icon: Bitmap = ContextCompat.getDrawable(this@HistoryActivity, R.drawable.ic_delete)!!
                            .toBitmap()

                        paint.color = Color.parseColor(
                            String.format(
                                "#%06x",
                                ContextCompat.getColor(this@HistoryActivity, R.color.red)
                            )
                        )

                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat() + 30,
                            itemView.right.toFloat(), itemView.bottom.toFloat() - 30, paint
                        )

                        canvas.drawBitmap(
                            icon, itemView.right.toFloat() - icon.width - 50,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat()
                                    - icon.height.toFloat()) / 2, paint
                        )

                    viewHolder.itemView.translationX = dX

                } else {
                    super.onChildDraw(
                        canvas, recyclerView, viewHolder, dX, dY,
                        actionState, isCurrentlyActive
                    )
                }

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition

                if (direction == ItemTouchHelper.LEFT) {

                    GlobalScope.launch(Dispatchers.IO) {
                        db.historyTodoDao().deleteHistoryTodo(historyTodoAdapter.getItemId(position))
                    }
                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvHistoryTodos)
    }

    private fun Drawable.toBitmap(): Bitmap {
        if (this is BitmapDrawable) return this.bitmap
        val bitmap = Bitmap.createBitmap(
            this.intrinsicWidth, this.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        this.setBounds(0, 0, canvas.width, canvas.height)
        this.draw(canvas)

        return bitmap
    }
}