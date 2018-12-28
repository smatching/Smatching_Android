package appjam.sopt.a23rd.smatching.Get

import appjam.sopt.a23rd.smatching.Data.NoticeData

data class GetNoticeListResponse(
        val status : Int,
        val message : String,
        val data : ArrayList<NoticeData>
)