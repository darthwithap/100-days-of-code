package com.darthwithap.roompersistancelibrary

import androidx.lifecycle.LiveData
import androidx.room.*

data class Todo(var task: String, var done: Boolean) {
    override fun toString(): String {
        return task
    }
}

@Entity
data class User(
    val name: String, val phNo: String, val city: String, val age: Int, val sex: Int,
    @PrimaryKey(autoGenerate = true) val id: Long = 0L
)

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Insert
    fun insertAll(list: List<User>)

    @Delete
    fun delete(uid: Int)

    @Query("SELECT * FROM User")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * FROM User WHERE age > :age")
    fun getAllUsersAbove(age: Int): List<User>
}