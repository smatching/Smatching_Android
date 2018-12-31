package appjam.sopt.a23rd.smatching.Put

import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Data.UserData

data class PutSmatchingCount(
        val status : Int,
        val message : String,
        val data : ArrayList<UserData>
)