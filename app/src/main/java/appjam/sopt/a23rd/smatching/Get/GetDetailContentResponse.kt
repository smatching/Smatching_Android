package appjam.sopt.a23rd.smatching.Get

import appjam.sopt.a23rd.smatching.Data.DetailData

data class GetDetailContentResponse(
        val status : Int,
        val message : String,
        val data : DetailData
)