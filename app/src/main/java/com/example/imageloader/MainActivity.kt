package com.example.imageloader

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader

class MainActivity : ComponentActivity(), LoaderManager.LoaderCallbacks<Bitmap?> {

    private lateinit var urlEditText: EditText
    private lateinit var loadImageButton: Button
    private lateinit var imageView: ImageView
    private lateinit var statusTextView: TextView
    private lateinit var networkReceiver: NetworkChangeReceiver

    companion object {
        const val IMAGE_LOADER_ID = 1
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        urlEditText = findViewById(R.id.urlEditText)
        loadImageButton = findViewById(R.id.loadImageButton)
        imageView = findViewById(R.id.imageView)
        statusTextView = findViewById(R.id.statusTextView)

        loadImageButton.setOnClickListener {
            val url = urlEditText.text.toString()
            if (url.isNotBlank()) {
                statusTextView.text = "Loading..."
                imageView.setImageBitmap(null)
                val bundle = Bundle()
                bundle.putString("url", url)
                LoaderManager.getInstance(this).restartLoader(IMAGE_LOADER_ID, bundle, this)
            } else {
                Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show()
            }
        }
        networkReceiver = NetworkChangeReceiver { isConnected ->
            if (isConnected) {
                loadImageButton.isEnabled = true
                statusTextView.text = "Connected"
            } else {
                loadImageButton.isEnabled = false
                statusTextView.text = "No internet connection"
            }
        }

        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkReceiver, filter)
        val serviceIntent = Intent(this, ImageLoaderService::class.java)
        startService(serviceIntent)
    }
    override fun onCreateLoader(id: Int, args: Bundle?): AsyncTaskLoader<Bitmap?> {
        val urlString = args?.getString("url") ?: ""
        return ImageLoader(this, urlString)
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkReceiver)
    }
    @SuppressLint("SetTextI18n")
    override fun onLoadFinished(loader: Loader<Bitmap?>, data: Bitmap?) {
        if (data != null) {
            imageView.setImageBitmap(data)
            statusTextView.text = "Image loaded successfully"
        } else {
            statusTextView.text = "Failed to load image"
        }
    }

    override fun onLoaderReset(loader: Loader<Bitmap?>) {
        imageView.setImageBitmap(null)
        statusTextView.text = ""
    }
//    @SuppressLint("StaticFieldLeak")
//    inner class ImageLoaderTask : AsyncTask<String, Void, Bitmap?>() {
//        @SuppressLint("SetTextI18n")
//        @Deprecated("Deprecated in Java")
//        override fun onPreExecute() {
//            super.onPreExecute()
//            statusTextView.text = "Loading..."
//            imageView.setImageBitmap(null)
//        }
//
//        @Deprecated("Deprecated in Java")
//        override fun doInBackground(vararg params: String): Bitmap? {
//            val urlString = params[0]
//            return try {
//                val url = URL(urlString)
//                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//                connection.doInput = true
//                connection.connect()
//                val input: InputStream = connection.inputStream
//                BitmapFactory.decodeStream(input)
//            } catch (e: Exception) {
//                e.printStackTrace()
//                null
//            }
//        }
//
//        @SuppressLint("SetTextI18n")
//        @Deprecated("Deprecated in Java")
//        override fun onPostExecute(result: Bitmap?) {
//            if (result != null) {
//                imageView.setImageBitmap(result)
//                statusTextView.text = "Image loaded successfully"
//            } else {
//                statusTextView.text = "Failed to load image"
//            }
//        }
//    }
}
