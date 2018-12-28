package appjam.sopt.a23rd.smatching.Get

import appjam.sopt.a23rd.smatching.Data.AllNoticeListData
import appjam.sopt.a23rd.smatching.Data.HomeData

data class GetAllNoticeListResponse(
        val status : Int,
        val message : String,
        val data : ArrayList<HomeData>
)