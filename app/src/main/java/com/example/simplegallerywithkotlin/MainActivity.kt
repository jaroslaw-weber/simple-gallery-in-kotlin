package com.example.simplegallerywithkotlin

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getImagesUrls()
        //addImage("https://i.picsum.photos/id/866/536/354.jpg?hmac=tGofDTV7tl2rprappPzKFiZ9vDh5MKj39oa2D--gqhA")
        //addImage("https://i.picsum.photos/id/866/536/354.jpg?hmac=tGofDTV7tl2rprappPzKFiZ9vDh5MKj39oa2D--gqhA")
        //addImage("https://i.picsum.photos/id/866/536/354.jpg?hmac=tGofDTV7tl2rprappPzKFiZ9vDh5MKj39oa2D--gqhA")

      }

    fun addImage(url:String)
    {
        var img = ImageView(this)
        imagesRoot.addView(img)
        Picasso.get().load(url).into(img)
        debugLog.text = "image added. url: ${url}"
    }

    fun getImagesUrls( )
    {

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://5f1188bd65dd950016fbd7a1.mockapi.io/images"
        debugLog.text = "sending request..."

        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                val imagesInfo = Klaxon().parseArray<ImageInfo>( response )
                val count = imagesInfo!!.count()
                debugLog.text = "parsed response. lenght: ${count}"
                //debugLog.text="${imagesInfo}";

                imagesInfo!!.forEach {
                    this.addImage(it.url)
                    //debugLog.text ="loaded "
                }
               // debugLog.text = "Response is: ${response.substring(0, 500)}"
                //debugLog.text ="${testImage!!.id}"
            },
            Response.ErrorListener {
                debugLog.text = "That didn't work! error: ${it}"
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)

    }


}
