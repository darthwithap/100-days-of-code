package me.darthwithap.todoapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Query

@Entity
data class TodoModel(
    var title: String,
    var descrption: String,
    var category: String,
    var date: Long,
    var time: Long,
    var isDone: Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
)

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoModel: TodoModel): Long

    @Query("SELECT * FROM TodoModel WHERE isDone == 0")
    fun getTask(): LiveData<List<TodoModel>>

    @Query("UPDATE TodoModel SET isDone = 1 WHERE id = :tid")
    fun finishTodo(tid: Long)

    @Query("DELETE FROM TodoModel WHERE id = :tid")
    fun deleteTodo(tid: Long)
}

@Database(entities = [TodoModel::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}