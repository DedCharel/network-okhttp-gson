package ua.cn.stu.http

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor

data class SignInRequestBody(
    val email: String,
    val password: String
)

data class SignInResponseBody(
    val token: String
)

val contentType = "application/json; charset=utf-8".toMediaType()

fun main(){
    val loggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val gson = Gson()

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val requestBody = SignInRequestBody(
        email = "admin@google.com",
        password = "123"
    )

    val requestBodySting = gson.toJson(requestBody)
    val okHttpRequestBody = requestBodySting.toRequestBody(contentType)

    val request = Request.Builder()
        .post(okHttpRequestBody)
        .url("http://127.0.0.1:12345/sign-in")
        .build()

    val call = client.newCall(request)

    val response = call.execute()


    if (response.isSuccessful){
        val responseBodyString = response.body!!.string()
        val signInResponseBody = gson.fromJson(
            responseBodyString,
            SignInResponseBody::class.java
        )
        println("TOKEN: ${signInResponseBody.token}")
    } else{
        throw IllegalStateException()
    }
}