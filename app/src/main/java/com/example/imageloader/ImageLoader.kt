package com.example.imageloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.loader.content.AsyncTaskLoader
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageLoader(context: Context, private val urlString: String) : AsyncTaskLoader<Bitmap?>(context) {

    override fun loadInBackground(): Bitmap? {
        return try {
            val url = URL(urlString)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onStartLoading() {
        forceLoad()
    }
}