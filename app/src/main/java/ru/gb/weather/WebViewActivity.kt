package ru.gb.weather

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.gb.weather.databinding.ActivityWebviewBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.stream.Collectors

internal class WebViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityWebviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ok.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
            }
        })

        binding.ok.setOnClickListener {
            binding.url.text.let {
                val uri = URL(it.toString())

                var myConnection: HttpURLConnection? = null

                myConnection = uri.openConnection() as HttpURLConnection
                myConnection.readTimeout = 5000
                val handler = Handler(Looper.myLooper()!!)
                Thread {
                    val reader = BufferedReader(InputStreamReader(myConnection.inputStream))
                    val result = getLines(reader)
                    Log.d("@@@", result)

                    handler.post {
                        binding.webView.loadDataWithBaseURL(
                            null, result, "text/html; charset=utf-8", "utf-8", null
                        )
                    }
                }.start()
            }


        }

    }

    fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

}