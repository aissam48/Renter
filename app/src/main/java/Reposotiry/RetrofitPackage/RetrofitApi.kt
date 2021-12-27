package Reposotiry.RetrofitPackage

import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.POST


interface RetrofitApi {

    @POST("")
    suspend fun CodeVerifyAtSignUp(PhoneNumber:String):Response<JSONObject>
}