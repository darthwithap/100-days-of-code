  package com.darthwithap.permanentdatastorage

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL
  lateinit var articlesDB : SQLiteDatabase
  class NewsReader : AppCompatActivity() {
    private var titles = ArrayList<String>()
    private var contents = ArrayList<String>()
    lateinit var adapter : ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_reader)

        articlesDB = this.openOrCreateDatabase("Articles", MODE_PRIVATE, null)
        articlesDB.execSQL("CREATE TABLE IF NOT EXISTS articles (id INT PRIMARY KEY, articleID INTEGER, title VARCHAR, content VARCHAR)")

        val task = DownloadTask()
        try {
            task.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty")
        } catch (e: Exception) {

        }

        val listView = findViewById<ListView>(R.id.list_view_news_reader)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, titles)
        listView.adapter = adapter

        updateListView()

        listView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Article::class.java)
            intent.putExtra("content", contents.get(position))
            startActivity(intent)
        }
    }

      fun updateListView() {
          val cursor = articlesDB.rawQuery("SELECT * FROM articles", null)
          if (cursor!=null && cursor.moveToFirst()) {
              titles.clear()
              contents.clear()
              do {
                  titles.add(cursor.getString(cursor.getColumnIndex("title")))
                  contents.add(cursor.getString(cursor.getColumnIndex("content")))
              } while (cursor.moveToNext())

              adapter.notifyDataSetChanged()
          }
      }
      inner class DownloadTask : AsyncTask<String, Void, String>() {

          private  val TAG = "NewsReader"

          override fun doInBackground(vararg urls: String?): String {
              var result = ""

              try {
                  var url = URL(urls[0])
                  var connection = url.openConnection()
                  var inputStream = connection.getInputStream()
                  var streamReader = InputStreamReader(inputStream)
                  var data = streamReader.read()

                  while (data != -1) {
                      val current = data.toChar()
                      result += current
                      data = streamReader.read()
                  }

                  val jsonArray = JSONArray(result)
                  var numOfItems = 10

                  if (jsonArray.length() < 10) numOfItems = jsonArray.length()
                  articlesDB.execSQL("DELETE FROM articles")
                  for (i in (0 until numOfItems)) {
                      var articleInfo = ""
                      val articleID = jsonArray.getString(i)
                      url = URL("https://hacker-news.firebaseio.com/v0/item/$articleID.json?print=pretty")
                      connection = url.openConnection()
                      inputStream = connection.getInputStream()
                      streamReader = InputStreamReader(inputStream)
                      data = streamReader.read()

                      while (data != -1) {
                          val current = data.toChar()
                          articleInfo += current
                          data = streamReader.read()
                      }
                      Log.d(TAG, " ARTICLE INFO : $articleInfo")
                      val jsonObject = JSONObject(articleInfo)
                      if (!jsonObject.isNull("title") && !jsonObject.isNull("url")) {
                          val articleTitle = jsonObject.getString("title")
                          val articleUrl = jsonObject.getString("url")

                          url = URL(articleUrl)
                          connection = url.openConnection()
                          inputStream = connection.getInputStream()
                          streamReader = InputStreamReader(inputStream)
                          data = streamReader.read()
                          var articleContent = ""
                          while (data != -1) {
                              val current = data.toChar()
                              articleContent += current
                              data = streamReader.read()
                          }
                          val sql = "INSERT INTO articles (articleID, title, content) VALUES (?, ?, ?)"
                          val statement = articlesDB.compileStatement(sql)
                          statement.bindString(1, articleID)
                          statement.bindString(2, articleTitle)
                          statement.bindString(3, articleContent)

                          statement.execute()
                      }
                  }

                  Log.d(TAG, "doInBackground: URL content : $result")

              } catch (e: Exception) {
                  e.printStackTrace()
              }

              return result
          }

          override fun onPreExecute() {
              super.onPreExecute()
              updateListView()
          }
      }
}

