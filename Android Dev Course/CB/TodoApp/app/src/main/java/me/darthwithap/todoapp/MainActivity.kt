package me.darthwithap.todoapp

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private val todos = arrayListOf<TodoModel>()
    private val todoAdapter = TodoAdapter(todos)

    val db by lazy {
        TodoDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rvTodos.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = todoAdapter
        }

        initSwipe()

        db.todoDao().getTask().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                todos.clear()
                todos.addAll(it)
                todoAdapter.notifyDataSetChanged()
            } else {
                todos.clear()
                todoAdapter.notifyDataSetChanged()
            }
        })

        fab.setOnClickListener {
            startActivity(Intent(this, TaskActivity::class.java))
        }
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper
        .SimpleCallback(
            0, ItemTouchHelper.LEFT or
                    ItemTouchHelper.RIGHT
        ) {
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
                    val icon: Bitmap

                    if (dX > 0) {
                        icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_check)!!
                            .toBitmap()
                        paint.color = Color.parseColor(
                            String.format(
                                "#%06x",
                                ContextCompat.getColor(this@MainActivity, R.color.green)
                            )
                        )

                        canvas.drawRect(
                            itemView.left.toFloat(), itemView.top.toFloat() + 30,
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat() - 30, paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.left.toFloat() + 50,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    } else {
                        icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_delete)!!
                            .toBitmap()

                        paint.color = Color.parseColor(
                            String.format(
                                "#%06x",
                                ContextCompat.getColor(this@MainActivity, R.color.red)
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
                    }
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

                if (direction == ItemTouchHelper.RIGHT) {

                    GlobalScope.launch(Dispatchers.IO) {
                        val todo = todos[position]
                        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                            db.historyTodoDao().insertHistoryTodo(
                                HistoryTodoModel(
                                    todo.title,
                                    todo.descrption,
                                    todo.category,
                                    todo.date,
                                    todo.time,
                                    todo.id
                                )
                            )
                        }
                        db.todoDao().finishTodo(todoAdapter.getItemId(position))

                    }
                } else {
                    GlobalScope.launch(Dispatchers.IO) {
                        db.todoDao().deleteTodo(todoAdapter.getItemId(position))
                    }
                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(rvTodos)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val item = menu?.findItem(R.id.menuSearch)
        val searchView = item?.actionView as SearchView

        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                displayTodos()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                displayTodos()
                return true
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    displayTodos(newText)
                }
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun displayTodos(q: String = "") {
        db.todoDao().getTask().observe(this, Observer { it ->
            if (it.isNotEmpty()) {
                todos.clear()
                todos.addAll(
                    it.filter {
                        it.title.contains(q, true)
                                || it.category.contains(q, true)
                                || it.descrption.contains(q, true)

                    }
                )
                todoAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuHistory -> startActivity(Intent(this, HistoryActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
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


