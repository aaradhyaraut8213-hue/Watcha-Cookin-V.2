package com.watchacookin.v2

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private var filePathCallback: ValueCallback<Array<android.net.Uri>>? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = android.graphics.Color.WHITE
        window.navigationBarColor = android.graphics.Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        val root=FrameLayout(this); webView=WebView(this); root.addView(webView,FrameLayout.LayoutParams(-1,-1)); setContentView(root)
        ViewCompat.setOnApplyWindowInsetsListener(webView){v,i->val b=i.getInsets(WindowInsetsCompat.Type.systemBars());v.updateLayoutParams<ViewGroup.MarginLayoutParams>{topMargin=b.top;bottomMargin=b.bottom;leftMargin=b.left;rightMargin=b.right};i}
        webView.settings.javaScriptEnabled=true; webView.settings.domStorageEnabled=true; webView.settings.allowFileAccess=true; webView.settings.allowContentAccess=true; webView.settings.cacheMode=android.webkit.WebSettings.LOAD_NO_CACHE
        CookieManager.getInstance().setAcceptCookie(true)
        webView.webViewClient=object:WebViewClient(){override fun onPageFinished(v:WebView?,u:String?){super.onPageFinished(v,u);injectV2Enhancements()}}
        webView.webChromeClient=object:WebChromeClient(){override fun onShowFileChooser(v:WebView?,cb:ValueCallback<Array<android.net.Uri>>?,p:FileChooserParams?):Boolean{filePathCallback?.onReceiveValue(null);filePathCallback=cb;val intent=p?.createIntent()?:return false;intent.putExtra(android.content.Intent.EXTRA_ALLOW_MULTIPLE,true);startActivityForResult(intent,1001);return true}}
        if(savedInstanceState==null)webView.loadUrl("file:///android_asset/index.html") else webView.restoreState(savedInstanceState)
    }
    private fun injectV2Enhancements(){try{val js=assets.open("v2-enhancements.js").bufferedReader().use{it.readText()};webView.evaluateJavascript("(function(){try{ $js }catch(e){console.error(e)}})();",null)}catch(_:Exception){}}
    override fun onActivityResult(r:Int,c:Int,d:android.content.Intent?){super.onActivityResult(r,c,d);if(r==1001){val x=if(c==RESULT_OK&&d!=null){val clip=d.clipData;if(clip!=null)Array(clip.itemCount){i->clip.getItemAt(i).uri}else d.data?.let{arrayOf(it)}}else null;filePathCallback?.onReceiveValue(x);filePathCallback=null}}
    override fun onSaveInstanceState(o:Bundle){webView.saveState(o);super.onSaveInstanceState(o)}
    override fun onBackPressed(){if(webView.canGoBack())webView.goBack()else super.onBackPressed()}
}
