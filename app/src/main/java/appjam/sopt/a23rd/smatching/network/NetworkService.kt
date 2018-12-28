package appjam.sopt.a23rd.smatching.network

import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.post.PostLogInResponse
import appjam.sopt.a23rd.smatching.post.PostSignUpResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface NetworkService {
    //회원가입
    @POST("/users")
    fun postSignUpResponse(
            @Header("Content-Type") content_type : String,
            @Body() body : JsonObject
    ) : Call<PostSignUpResponse>
    //로그인
    @POST("/users/login")
    //@POST("/login")
    fun postLoginResponse(
            @Header("Content-Type") content_type : String,
            @Body() body : JsonObject
    ) : Call<PostLogInResponse>
    //전체공고
    @GET("/notices/list/")
    fun getAllNoticeListResponseGuest(
            /*@Header("Authorization") token : String,*/
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int
    ) : Call<GetNoticeListResponse>
    @GET("/notices/list/")
    fun getAllNoticeListResponse(
            @Header("Authorization") token : String,
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int
    ) : Call<GetNoticeListResponse>
    @GET("/notices/fit/{cond_idx}&{request_num}&{exist_num}")
    fun getFitNoticeListResponse(
            @Header("Authorization") token : String,
            @Query("cond_idx") cond_idx : Int,
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int
    ) : Call<GetNoticeListResponse>
}