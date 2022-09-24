package ua.cn.stu.http.sources.base

import com.google.gson.Gson
import okhttp3.OkHttpClient


class OkHttpConfig(
    val baseUrl: String,
    val client: OkHttpClient,
    val gson: Gson
)