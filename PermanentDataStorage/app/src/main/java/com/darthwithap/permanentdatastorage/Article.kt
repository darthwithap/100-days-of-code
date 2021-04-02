package com.darthwithap.permanentdatastorage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class Article : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)

        val webView = findViewById<WebView>(R.id.web_view_article)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        intent.getStringExtra("content")?.let { webView.loadData(it, "text/html", "UTF-8") }
    }
}