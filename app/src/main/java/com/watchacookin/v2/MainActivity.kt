package com.watchacookin.v2

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private var fileCallback: ValueCallback<Array<Uri>>? = null
    private val chooserRequest = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Keep the app's bottom navigation above Android's gesture/navigation area.
        // Android 15 enforces edge-to-edge for apps targeting API 35, so without
        // this inset handling the WebView can extend underneath the system buttons.
        window.navigationBarColor = Color.WHITE
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        webView = WebView(this)
        setContentView(webView)

        webView.setOnApplyWindowInsetsListener { view, insets ->
            val navInsets = insets.getInsets(WindowInsets.Type.navigationBars())
            val statusInsets = insets.getInsets(WindowInsets.Type.statusBars())
            view.setPadding(
                view.paddingLeft,
                statusInsets.top,
                view.paddingRight,
                navInsets.bottom
            )
            insets
        }
        webView.requestApplyInsets()

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(view: WebView?, callback: ValueCallback<Array<Uri>>?, params: FileChooserParams?): Boolean {
                fileCallback?.onReceiveValue(null)
                fileCallback = callback
                val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                }
                return try {
                    startActivityForResult(Intent.createChooser(intent, "Choose ingredient photos"), chooserRequest)
                    true
                } catch (_: Exception) {
                    fileCallback = null
                    false
                }
            }
        }
        if (savedInstanceState == null) webView.loadUrl("file:///android_asset/index.html") else webView.restoreState(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != chooserRequest) return
        val results = if (resultCode == RESULT_OK && data != null) {
            data.clipData?.let { clip -> Array(clip.itemCount) { i -> clip.getItemAt(i).uri } }
                ?: data.data?.let { arrayOf(it) }
        } else null
        fileCallback?.onReceiveValue(results)
        fileCallback = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated by Android API 33; retained for minSdk compatibility")
    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack() else super.onBackPressed()
    }

    override fun onDestroy() {
        webView.stopLoading()
        webView.destroy()
        super.onDestroy()
    }
}
