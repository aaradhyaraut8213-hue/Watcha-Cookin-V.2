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
                  'Wash the tomatoes and peel the onion and garlic. Chop the onion and tomatoes into small pieces so they cook quickly.',
                  'Cut the paneer into medium bite-sized cubes. Keep the cubes aside while you prepare the gravy.',
                  'Heat butter in a pan over medium heat. Add the chopped onion and garlic and sauté until the onion turns soft and lightly golden.',
                  'Add the chopped tomatoes and cook for 5–7 minutes, stirring occasionally, until they become completely soft.',
                  'Add garam masala and a little salt. Mix well and cook for another 1–2 minutes so the spices become fragrant.',
                  'Turn off the heat and let the mixture cool slightly. Blend or mash it until you get a smooth gravy.',
                  'Return the gravy to the pan. Add a small splash of water if it is too thick and bring it to a gentle simmer.',
                  'Lower the heat and stir in the cream. Mix slowly until the gravy becomes smooth and creamy.',
                  'Add the paneer cubes and gently coat them with the gravy. Simmer for 5–8 minutes so the paneer warms through and absorbs the flavour.',
                  'Taste and adjust salt or garam masala. Garnish with coriander or a little cream if available, then serve hot.'
                ],
                'Paneer Tikka':[
                  'Cut paneer into medium cubes. Cut the onion and capsicum into pieces roughly the same size as the paneer.',
                  'In a bowl, add curd, chilli powder, garam masala and a little salt. Mix until smooth.',
                  'Add paneer, onion and capsicum to the marinade and gently toss until every piece is coated.',
                  'Let the coated pieces rest for at least 10–15 minutes so the flavour gets into the paneer and vegetables.',
                  'Heat a non-stick pan or grill over medium-high heat. Lightly grease the surface if needed.',
                  'Place the paneer and vegetables in a single layer. Do not overcrowd the pan because they should brown rather than steam.',
                  'Cook for 2–3 minutes on one side, then turn the pieces with tongs or a spatula.',
                  'Continue turning and cooking until the paneer is hot and the edges of the vegetables have light brown or charred spots.',
                  'Lower the heat if the marinade is browning too quickly. Make sure the vegetables are cooked but still have some bite.',
                  'Transfer to a plate and serve immediately. Add lemon juice or coriander if available.'
                ],
                'Creamy Tomato Pasta':[
                  'Bring a large pot of water to a boil and add some salt. Keep the water boiling before adding the pasta.',
                  'Add the pasta and cook according to the packet instructions until tender but not mushy.',
                  'Before draining, save about half a cup of pasta water. Drain the pasta and keep it aside.',
                  'Heat a pan over medium heat and add a little oil or butter. Add finely chopped garlic and cook until fragrant.',
                  'Add the tomato and cook for several minutes until it softens and the mixture starts to look like a sauce.',
                  'Add the required seasoning and garam masala or herbs if your recipe uses them. Stir everything together.',
                  'Lower the heat and add cream slowly while stirring continuously so the sauce stays smooth.',
                  'Add cheese if using and stir until it melts into the sauce. If the sauce becomes too thick, add a little reserved pasta water.',
                  'Add the drained pasta and toss until every piece is coated. Cook together for 1–2 minutes.',
                  'Taste and adjust salt and seasoning. Serve hot with extra cheese or herbs if available.'
                ],
                'Veg Fried Rice':[
                  'Use cooked, completely cooled rice. Break up any large clumps with your fingers or a fork before starting.',
                  'Finely chop the onion, garlic and vegetables so all the pieces cook quickly and evenly.',
                  'Heat a wide pan or wok on high heat. Add a little oil and let it become hot before adding the vegetables.',
                  'Add garlic and onion and stir-fry for about a minute until fragrant. Do not let the garlic burn.',
                  'Add the harder vegetables first and stir continuously for 2–3 minutes so they stay slightly crisp.',
                  'Add the remaining vegetables and cook for another minute or two.',
                  'Add the cooled rice and gently toss from the bottom of the pan. Keep the heat high so the rice fries instead of becoming soggy.',
                  'Pour in soy sauce around the sides of the hot pan and quickly mix it through the rice.',
                  'Cook for another 1–2 minutes while tossing. Taste before adding salt because soy sauce already contains salt.',
                  'Turn off the heat and serve immediately while the rice is hot and the vegetables are still slightly crisp.'
                ],
                'Paneer Sandwich':[
                  'Cut the paneer, onion and tomato into small pieces. Smaller pieces make the sandwich easier to eat and help the filling heat evenly.',
                  'Put the chopped filling into a bowl and add the seasoning you want. Mix everything thoroughly.',
                  'Place one slice of bread on a clean plate or board. Spread the paneer mixture over it, leaving a small gap around the edges.',
                  'Add cheese if using. Do not pile the filling too high or it may fall out while toasting.',
                  'Place the second slice of bread on top and press gently so the sandwich holds together.',
                  'Lightly butter or oil the outside of the bread if desired. This helps the outside become crisp and golden.',
                  'Heat a pan over medium heat. Place the sandwich on the pan and cook without pressing too hard.',
                  'Toast for 2–3 minutes until the bottom is golden and crisp, then carefully flip it.',
                  'Toast the second side until golden and make sure the centre is hot and the cheese has melted if used.',
                  'Transfer to a plate, cut in half and serve while hot.'
                ],
                'Chole Masala':[
                  'If using dried chickpeas, rinse them and soak them in plenty of water beforehand. Cook them until completely tender and drain.',
                  'Finely chop the onion, garlic, ginger and tomato so they break down easily into the masala.',
                  'Heat oil in a pan over medium heat. Add onion, ginger and garlic and sauté until the onion becomes soft and lightly golden.',
                  'Add the chopped tomato and cook until it becomes soft and the mixture starts to look thick.',
                  'Add the spices and salt. Stir well and cook for 1–2 minutes so the spices become fragrant.',
                  'Add the cooked chickpeas and mix until they are completely coated in the masala.',
                  'Add a little water and stir. Scrape the bottom of the pan gently if any masala is sticking.',
                  'Cover and simmer on low-medium heat for 10–15 minutes. Stir occasionally and add a little more water if needed.',
                  'Lightly mash a few chickpeas against the side of the pan if you want a thicker gravy.',
                  'Taste and adjust the seasoning. Serve hot with rice, roti, bread or another side of your choice.'
                ],
                'Masala Omelette':[
                  'Wash and finely chop the onion, tomato and chilli. Keep the pieces small so they cook properly inside the omelette.',
                  'Crack the eggs into a bowl and whisk until the yolks and whites are fully combined.',
                  'Add the chopped vegetables, salt and other seasoning to the eggs. Mix gently so everything is evenly distributed.',
                  'Heat a non-stick pan over medium heat and add a small amount of oil or butter.',
                  'Pour the egg mixture into the centre of the pan and gently spread it into an even layer.',
                  'Leave it undisturbed for about a minute while the bottom begins to set. Keep the heat at medium or medium-low.',
                  'As the edges cook, gently lift them with a spatula so any uncooked egg can run underneath.',
                  'Cook until the top is mostly set. Add cheese or coriander if desired before folding.',
                  'Fold the omelette in half or carefully flip it. Cook for another 30–60 seconds until fully set.',
                  'Slide onto a plate and serve immediately while hot.'
                ],
                'Paneer Power Salad':[
                  'Wash the lettuce and tomatoes well and drain them. Pat them dry so the salad does not become watery.',
                  'Cut the lettuce and tomatoes into bite-sized pieces and place them in a large bowl.',
                  'Cut the paneer into small cubes so it is easy to eat with the salad.',
                  'Heat a pan over medium heat and lightly sear the paneer until the outside becomes lightly golden. Turn the cubes so multiple sides brown.',
                  'In a small bowl, mix curd, lemon juice and your chosen seasoning to make a simple creamy dressing.',
                  'Let the warm paneer cool for a minute so it does not make the lettuce wilt immediately.',
                  'Add the paneer to the bowl with the vegetables and gently mix everything together.',
                  'Pour the dressing over the salad gradually rather than all at once. Toss gently until coated.',
                  'Taste and adjust lemon juice, salt or seasoning according to your preference.',
                  'Serve immediately for the best crunch and texture.'
                ],
                'Poha':[
                  'Place the poha in a strainer and rinse it gently with water. Do not soak it for too long because it can become mushy.',
                  'Let the rinsed poha drain for a few minutes. The flakes should feel soft but still hold their shape.',
                  'Heat oil in a pan over medium heat. Add peanuts and cook until lightly toasted, then keep them in the pan.',
                  'Add chopped onion and chilli and sauté until the onion becomes soft. Stir frequently so nothing burns.',
                  'Add the drained poha and sprinkle in the seasoning. Mix gently using a spatula.',
                  'Cook on low-medium heat for 2–4 minutes, folding rather than aggressively stirring so the poha stays intact.',
                  'Check the texture. If it feels too dry, sprinkle a very small amount of water and mix gently.',
                  'Turn off the heat once the poha is hot throughout. Squeeze fresh lemon juice over it.',
                  'Taste and adjust salt, lemon or seasoning. Add the toasted peanuts evenly on top.',
                  'Serve warm, optionally garnished with coriander or another topping you have available.'
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
                if(!title)return;
                const list=detailed[title.textContent.trim()];
                if(!list)return;
                const stepList=document.querySelector('.detailbody .steps');
                if(!stepList)return;
                if(stepList.datasetDetailedTitle===title.textContent.trim())return;
                stepList.innerHTML='';
                list.forEach((text,i)=>{
                  const li=document.createElement('li');
                  const p=document.createElement('p');
                  p.textContent=text;
                  li.appendChild(p);
                  stepList.appendChild(li);
                });
                stepList.datasetDetailedTitle=title.textContent.trim();
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
