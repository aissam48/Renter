package Reposotiry.RetrofitPackage

import Reposotiry.RoomDB.UserEntity
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface RetrofitApi {

    @POST("/CodeVerifyAtSignUp")
    suspend fun CodeVerifyAtSignUp(PhoneNumber:String):Response<JsonObject>

    @POST("/SignUp")
    suspend fun SignUp(entity: UserEntity):Response<JsonObject>

    @Multipart
    @POST("/UploadProfileImage")
    suspend fun UploadProfileImage (@Part file: MultipartBody.Part)

}