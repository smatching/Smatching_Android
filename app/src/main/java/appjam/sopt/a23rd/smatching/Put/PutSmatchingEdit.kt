package appjam.sopt.a23rd.smatching.Put

import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Data.UserData

data class PutSmatchingEdit(
        val status : Int,
        val message : String,
        val data : Int
)