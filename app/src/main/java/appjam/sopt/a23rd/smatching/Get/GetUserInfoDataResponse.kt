package appjam.sopt.a23rd.smatching.Get

import appjam.sopt.a23rd.smatching.Data.UserInfoData


data class GetUserInfoDataResponse(
        val status : Int,
        val message : String,
        val data : UserInfoData
)