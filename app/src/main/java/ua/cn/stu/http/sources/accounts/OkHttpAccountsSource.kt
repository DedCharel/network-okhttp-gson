package ua.cn.stu.http.sources.accounts

import kotlinx.coroutines.delay
import okhttp3.Request
import ua.cn.stu.http.app.model.accounts.AccountsSource
import ua.cn.stu.http.app.model.accounts.entities.Account
import ua.cn.stu.http.app.model.accounts.entities.SignUpData
import ua.cn.stu.http.sources.accounts.entities.*
import ua.cn.stu.http.sources.base.BaseOkHttpSource
import ua.cn.stu.http.sources.base.OkHttpConfig

class OkHttpAccountsSource(
    config: OkHttpConfig
) : BaseOkHttpSource(config), AccountsSource {

    override suspend fun signIn(email: String, password: String): String {
        delay(1000)
        // Call "POST /sign-in" endpoint and return token.
        // Use SignInRequestEntity and SignInResponseEntity.
        val signInRequestEntity = SignInRequestEntity(
            email = email,
            password = password
        )

        val request = Request.Builder()
            .post(signInRequestEntity.toJsonRequestBody())
            .endpoint("/sign-in")
            .build()
        val response = client.newCall(request).suspendEnqueue()
        return response.parseJsonResponse<SignInResponseEntity>().token
    }

    override suspend fun signUp(signUpData: SignUpData) {
        delay(1000)
        // Call "POST /sign-up" endpoint.
        // Use SignUpRequestEntity
        val signUpRequestEntity = SignUpRequestEntity(
            email = signUpData.email,
            password = signUpData.password,
            username = signUpData.username
        )
        val request = Request.Builder()
            .post(signUpRequestEntity.toJsonRequestBody())
            .endpoint("/sign-up")
            .build()
        client.newCall(request).suspendEnqueue()
    }

    override suspend fun getAccount(): Account {
        delay(1000)
        // Call "GET /me" endpoint.
        // Use GetAccountResponseEntity.
        val request = Request.Builder()
            .get()
            .endpoint("/me")
            .build()
        val response = client.newCall(request).suspendEnqueue()
        return response.parseJsonResponse<GetAccountResponseEntity>().toAccount()
    }

    override suspend fun setUsername(username: String) {
        delay(1000)
        // Call "PUT /me" endpoint.
        // Use UpdateUsernameRequestEntity.
        val updateUsernameRequestEntity = UpdateUsernameRequestEntity(
            username = username
        )
        val request = Request.Builder()
            .put(updateUsernameRequestEntity.toJsonRequestBody())
            .endpoint("/me")
            .build()
        client.newCall(request).suspendEnqueue()

    }

}