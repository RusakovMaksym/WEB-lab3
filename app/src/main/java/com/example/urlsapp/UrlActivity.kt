package com.example.urlsapp

import MainView
import ShortenedUrlResponse
import UrlShortenApi
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.urlsapp.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UrlActivity : AppCompatActivity() {
    lateinit var viewModel: MainView
    private lateinit var editText: EditText
    private lateinit var buttonSubmit: Button
    private lateinit var textViewResult: TextView

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://cutt.ly/api/api.php/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiService = retrofit.create(UrlShortenApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url)

        // Создаем экземпляр ViewModel
        viewModel = ViewModelProvider(this).get(MainView::class.java)

        // Находим View-элементы в макете
        editText = findViewById(R.id.urlEditText)
        buttonSubmit = findViewById(R.id.activity_main_cutButton)
        textViewResult = findViewById(R.id.textUrl)

        // Назначаем обработчик нажатия на кнопку
        buttonSubmit.setOnClickListener {
            val inputValue = editText.text.toString()
            shortenUrl(inputValue)
            //showToast("Submitted: $inputValue")

            //viewModel.setInputValue(inputValue)
        }

        // Наблюдаем за изменениями в результате и обновляем текстовое поле
        viewModel.result.observe(this, { result ->
            textViewResult.text = result
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun shortenUrl(inputUrl: String) {
        val apiUrl = "https://cutt.ly/api/api.php?key=cb6e9014af99c1735937297960ef782850bd3&short=$inputUrl"
        val call = apiService.shortenUrl(apiUrl)

        call.enqueue(object : Callback<ShortenedUrlResponse> {
            override fun onResponse(
                call: Call<ShortenedUrlResponse>,
                response: Response<ShortenedUrlResponse>
            ) {
                val shortenedUrlResponse = response.body()
                if (shortenedUrlResponse != null && shortenedUrlResponse.url?.status == 7) {
                    val shortenedUrl = shortenedUrlResponse.url?.shortLink
                    // Копируем укороченную ссылку в буфер обмена
                    val clipboardManager =
                        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    clipboardManager.setPrimaryClip(
                        android.content.ClipData.newPlainText(
                            "shortened_url",
                            shortenedUrl
                        )
                    )
                    // Показываем сообщение об успешном укорачивании и копировании ссылки
                    showToast("Посилання успішно скорочено та скопійовано")
                } else {
                    showToast("Помилка при скороченні!")
                }
            }

            override fun onFailure(call: Call<ShortenedUrlResponse>, t: Throwable) {
                showToast("Помилка при скороченні!")
            }
        })
    }

    private fun observeViewModel() {

    }
}