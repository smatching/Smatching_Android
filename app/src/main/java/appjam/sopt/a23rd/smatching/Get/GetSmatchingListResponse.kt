package appjam.sopt.a23rd.smatching.Get

import appjam.sopt.a23rd.smatching.Data.*

data class GetSmatchingListResponse(
        val status : Int,
        val message : String,
        val data : CondData
)
data class CondData(
        val condIdx : Int,
        val condName : String,
        val alert : Boolean,
        val location : ArrayList<LocationData>,
        val age : ArrayList<AgeData>,
        val period : ArrayList<PeriodData>,
        val field : ArrayList<FieldData>,
        val advantage : ArrayList<AdvantageData>,
        val busiType : ArrayList<BusiTypeData>,
        val excCategory : ArrayList<ExcCategoryData>
)