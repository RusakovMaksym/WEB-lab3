package com.example.urlsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.urlsapp.R
import com.example.urlsapp.backendapi.DjangoApi
import com.example.urlsapp.backendapi.RetrofitHelper
import com.example.urlsapp.models.LogInBody
import com.example.urlsapp.models.Token
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    var djangoApi = RetrofitHelper.getInstance().create(DjangoApi::class.java)
    var job: Job? = null
    val token = MutableLiveData<Token>()
    val postsLoadError = MutableLiveData<String?>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity_main_loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val loginInfo = LogInBody(email, password)
            djangoApi.getToken(loginInfo).enqueue(object: Callback<Token> {
                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    token.value = response.body()
                    Log.d("ayush: ", token.value.toString())
                }
            })
            if (token.value != null) {
                val intent = Intent(this@MainActivity, UrlActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Wrong Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}