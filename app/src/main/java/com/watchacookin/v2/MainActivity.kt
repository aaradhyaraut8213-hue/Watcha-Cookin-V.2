package com.watchacookin.v2

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private var fileCallback: ValueCallback<Array<Uri>>? = null
    private val chooserRequest = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.navigationBarColor = Color.WHITE
        window.statusBarColor = Color.WHITE
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR or
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        webView = WebView(this)
        val root = FrameLayout(this)
        root.setBackgroundColor(Color.WHITE)
        root.addView(webView, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ))
        setContentView(root)

        root.setOnApplyWindowInsetsListener { _, insets ->
            val systemInsets = insets.getInsets(
                WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars()
            )
            val lp = webView.layoutParams as FrameLayout.LayoutParams
            lp.topMargin = systemInsets.top
            lp.bottomMargin = systemInsets.bottom
            webView.layoutParams = lp
            insets
        }
        root.requestApplyInsets()

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                injectV2Enhancements()
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                view: WebView?,
                callback: ValueCallback<Array<Uri>>?,
                params: FileChooserParams?
            ): Boolean {
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

        if (savedInstanceState == null) webView.loadUrl("file:///android_asset/index.html")
        else webView.restoreState(savedInstanceState)
    }

    private fun injectV2Enhancements() {
        val js = """
            (function(){
              const detailed={
                'Paneer Butter Masala':[
                  'Finely chop the onion, garlic and tomatoes. Keep the paneer ready in bite-sized cubes.',
                  'Heat butter on medium heat. Add onion and garlic and cook until the onion becomes soft and lightly golden.',
                  'Add tomatoes and garam masala. Cook until the tomatoes become soft and the mixture looks thick.',
                  'Blend or mash the cooked tomato mixture until smooth. Add a small splash of water if needed.',
                  'Lower the heat, stir in cream, then add paneer. Simmer gently for 5–8 minutes so the paneer absorbs the sauce.',
                  'Taste and adjust seasoning. Garnish with coriander if available and serve hot.'
                ],
                'Paneer Tikka':[
                  'Cut paneer, capsicum and onion into similar-sized pieces so they cook evenly.',
                  'Mix curd with chilli and garam masala. Coat the paneer and vegetables evenly and let them rest for a few minutes.',
                  'Heat a non-stick pan or grill on medium-high heat. Lightly grease it if needed.',
                  'Cook the pieces in a single layer until lightly golden or charred, turning them as needed.',
                  'Check that the paneer is hot and the vegetables are tender-crisp. Serve immediately.'
                ],
                'Creamy Tomato Pasta':[
                  'Bring salted water to a boil and cook the pasta until just tender according to the packet timing.',
                  'Sauté finely chopped garlic over medium heat until fragrant, without letting it burn.',
                  'Add tomato and cook until it softens and forms a thick sauce.',
                  'Lower the heat and stir in cream and cheese until smooth.',
                  'Drain the pasta, keeping a little pasta water. Toss pasta through the sauce and add a splash of water if needed.',
                  'Taste, adjust seasoning and serve hot.'
                ],
                'Veg Fried Rice':[
                  'Use cooked, cooled rice so the grains stay separate while frying.',
                  'Heat a wide pan on high heat. Stir-fry garlic and onion until fragrant.',
                  'Add chopped vegetables and cook for a few minutes while keeping them slightly crisp.',
                  'Add rice and toss continuously so it heats evenly without sticking.',
                  'Add soy sauce and mix thoroughly. Keep the heat high for another minute.',
                  'Taste before adding salt because soy sauce is already salty. Serve immediately.'
                ],
                'Paneer Sandwich':[
                  'Chop paneer, onion and tomato into small pieces for an even filling.',
                  'Spread the filling over bread and add cheese if using. Keep the filling away from the edges.',
                  'Close the sandwich and lightly butter or oil the outside if desired.',
                  'Toast on medium heat until both sides are crisp and golden.',
                  'Check that the cheese has melted and the centre is hot, then cut and serve.'
                ],
                'Chole Masala':[
                  'If using dried chickpeas, soak them beforehand and cook until tender. Drain before using.',
                  'Sauté onion, ginger and garlic in oil until the onion becomes soft and lightly golden.',
                  'Add tomato and spices. Cook until the tomato breaks down and the masala becomes thick.',
                  'Add cooked chickpeas and stir well so they are coated in the masala.',
                  'Add a little water and simmer on low-medium heat for 10–15 minutes, stirring occasionally.',
                  'Taste and adjust seasoning. Serve hot with rice, roti or bread.'
                ],
                'Masala Omelette':[
                  'Finely chop onion, tomato and chilli so the vegetables cook quickly.',
                  'Whisk the eggs until combined, then mix in the vegetables and seasoning.',
                  'Heat a non-stick pan over medium heat and lightly grease it.',
                  'Pour in the egg mixture and spread it into an even layer.',
                  'Cook until the edges are set, then fold or flip carefully and cook until fully set.',
                  'Slide onto a plate and serve hot.'
                ],
                'Paneer Power Salad':[
                  'Wash and chop lettuce and tomatoes into bite-sized pieces. Keep them dry so the salad stays crisp.',
                  'Cut paneer into cubes and pan-sear over medium heat until lightly golden.',
                  'Mix curd with lemon juice and seasoning to make a simple dressing.',
                  'Combine vegetables and warm paneer, pour over the dressing and toss gently.',
                  'Taste and adjust lemon, salt or seasoning before serving.'
                ],
                'Poha':[
                  'Rinse the poha gently in a strainer until it softens slightly, then let the excess water drain.',
                  'Sauté onion, peanuts and chilli until the onion is soft and the peanuts are lightly toasted.',
                  'Add drained poha and seasoning. Fold gently so it does not become mushy.',
                  'Cook on low-medium heat for a few minutes until hot throughout.',
                  'Turn off the heat and squeeze fresh lemon over the poha.',
                  'Serve warm, optionally topped with coriander or extra peanuts.'
                ]
              };
              function enhance(){
                const logo=document.querySelector('.logo');
                if(logo&&!logo.querySelector('.version-label')){
                  const v=document.createElement('small');
                  v.className='version-label'; v.textContent='V.1';
                  v.style.cssText='font-size:10px;color:#777;font-weight:800;margin-left:5px;vertical-align:middle;';
                  logo.appendChild(v);
                }
                const title=document.querySelector('.detailbody h1');
                const steps=document.querySelectorAll('.detailbody .steps li p');
                if(!title||!steps.length)return;
                const list=detailed[title.textContent.trim()];
                if(!list||steps.datasetDetailed==='1')return;
                list.forEach((text,i)=>{if(steps[i])steps[i].textContent=text;});
                steps.datasetDetailed='1';
              }
              enhance(); setInterval(enhance,300);
            })();
        """.trimIndent()
        webView.evaluateJavascript("javascript:$js", null)
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
