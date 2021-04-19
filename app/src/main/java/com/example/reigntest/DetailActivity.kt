package com.example.reigntest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import java.lang.NullPointerException

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var url: String? = null
        if (intent.extras != null) {
            url = intent.getStringExtra("URL")
        }

        val webview: WebView = findViewById(R.id.webview)
        webview.settings.javaScriptEnabled = true
        url?.let { webview.loadUrl(it) }

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}