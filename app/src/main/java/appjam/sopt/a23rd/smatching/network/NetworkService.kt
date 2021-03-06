package appjam.sopt.a23rd.smatching.network

import appjam.sopt.a23rd.smatching.Delete.DeleteSmatchingCondsResponse
import appjam.sopt.a23rd.smatching.Get.*
import appjam.sopt.a23rd.smatching.Put.PutNoticeScrap
import appjam.sopt.a23rd.smatching.Put.PutSmatchingCount
import appjam.sopt.a23rd.smatching.Put.PutSmatchingEdit
import appjam.sopt.a23rd.smatching.post.PostLogInResponse
import appjam.sopt.a23rd.smatching.post.PostSignUpResponse
import appjam.sopt.a23rd.smatching.post.PostSmatchingAdd
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
    //유저의 맞춤조건 현황 조회
    @GET("/users/cond")
    fun getUserSmatchingCondResponse(
            @Header("Authorization") token : String
    ) : Call<GetUserSmatchingCondResponse>
    //맞춤지원사업 목록 조회
    @GET("/notices/list")
    fun getFitNoticeListResponse(
            @Header("Authorization") token : String,
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int,
            @Query("cond_idx") cond_idx : Int
    ) : Call<GetNoticeListResponse>
    //전체지원사업 목록 조회
    @GET("/notices/list")
    fun getAllNoticeListResponse(
            @Header("Authorization") token : String,
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int
    ) : Call<GetNoticeListResponse>
    //전체지원사업 개수 조회
    @GET("/notices/count")
    fun getAllNoticeListSizeResponse(

    ) : Call <GetAllNoticeListSizeResponse>
    //맞춤조건 조회
    @GET("/conds")
    fun getSmatchingCondsResponse(
            @Query("cond_idx") cond_idx: Int
    ) : Call<GetSmatchingListResponse>
    //맞춤조건에 맞는 지원사업 개수 조회
    @PUT("/conds/count")
    fun putSmatchingCondsCountResponse(
            @Header("Content-Type") content_type : String,
            @Body() body : JsonObject
    ) : Call<PutSmatchingCount>
    //맞춤조건 추가
    @POST("/conds")
    fun postSmatchingCondsAddResponse(
            @Header("Content-Type") content_type : String,
            @Header("Authorization") token : String,
            @Body() body : JsonObject
    ) : Call<PostSmatchingAdd>
    //맞춤조건 변경
    @PUT("/conds/{condIdx}")
    fun putSmatchingCondsChangeResponse(
            @Header("Content-Type") content_type : String,
            @Header("Authorization") token : String,
            @Path("condIdx") cond_idx: Int,
            @Body() body : JsonObject
    ) : Call<PutSmatchingEdit>
    //맞춤조건 삭제
    @DELETE("/conds/{condIdx}")
    fun deleteSmatchingCondsDeleteResponse(
            @Header("Authorization") token : String,
            @Path("condIdx") cond_idx: Int
    ) : Call<DeleteSmatchingCondsResponse>
    //지원사업 스크랩 여부 조회
    @GET("/notices/scrap")
    fun getNoticeScrap(
            @Header("Authorization") token : String,
            @Query("notice_idx") notice_idx: Int
    ) : Call<PutNoticeScrap>
    //지원사업 스크랩 설정/해제
    @PUT("/notices/scrap/{noticeIdx}")
    fun putNoticeScrap(
            @Header("Authorization") token : String,
            @Path("noticeIdx") notice_idx: Int
    ) : Call<PutNoticeScrap>
    //지원사업 상세 조회
    @GET("/notices/detail")
    fun getDetailContentResponse(
            @Query("notice_idx") notice_idx : Int
    ) : Call<GetDetailContentResponse>
    //스크랩한 지원사업 목록 조회
    @GET("/users/noticelist")
    fun getSmatchingScrapListResponse(
            @Header("Authorization") token : String,
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int
    ) : Call<GetNoticeListResponse>
    //맞춤지원 스크랩에서 검색
    @GET("/search/notices/scrap")
    fun getScrapSearchResponse(
            @Header("Authorization") token : String,
            @Query("query") query : String,
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int
    ) : Call<GetNoticeListResponse>
    //회원정보 조회
    @GET("/users/edit")
    fun getUserInfo(
            @Header("Authorization") token : String
    ) : Call<GetUserInfoDataResponse>
    //회원정보 설정
    @PUT("/users/edit")
    fun putUserInfoEditResponse(
            @Header("Content-Type") content_type : String,
            @Header("Authorization") token : String,
            @Body() body : JsonObject
    ) : Call<PutSmatchingEdit>
    //회원탈퇴
    @DELETE("/users")
    fun deleteUserInfoResponse(
            @Header("Authorization") token : String
    ) : Call<DeleteSmatchingCondsResponse>
    //전체 지원사업 검색
    @GET("/search/notices")
    fun getSearchResultResponse(
            @Header("Authorization") token : String,
            @Query("query") query : String,
            @Query("request_num") request_num : Int,
            @Query("exist_num") exist_num : Int
    ) : Call<GetNoticeListResponse>
}