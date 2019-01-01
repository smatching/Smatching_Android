package appjam.sopt.a23rd.smatching.Get

import appjam.sopt.a23rd.smatching.Data.UserData
import appjam.sopt.a23rd.smatching.Data.UserSmatchingData

data class GetUserSmatchingCondResponse(
        val status : Int,
        val message : String,
        val data : UserSmatchingData
)