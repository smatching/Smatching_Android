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
        val location : LocationData,
        val age : AgeData,
        val period : PeriodData,
        val field : FieldData,
        val advantage : AdvantageData,
        val busiType : BusiTypeData,
        val excCategory : ExcCategoryData
)

data class LocationData(
        val jeonbuk: Boolean,
        val gangwon: Boolean,
        val gwangju: Boolean,
        val ulsan: Boolean,
        val kyungbuk: Boolean,
        val sejong: Boolean,
        val chungbuk: Boolean,
        val kyungnam: Boolean,
        val seoul: Boolean,
        val chungnam: Boolean,
        val daejeon: Boolean,
        val busan: Boolean,
        val jeju: Boolean,
        val daegu: Boolean,
        val aborad: Boolean,
        val kyunggi: Boolean,
        val incheon: Boolean,
        val jeonnam: Boolean,
        val domesticAll: Boolean
)
data class AgeData(
        val forty_more: Boolean,
        val twenty_less: Boolean,
        val twenty_forty: Boolean
)
data class PeriodData(
        val three_four: Boolean,
        val six_seven: Boolean,
        val one_two: Boolean,
        val seven_more: Boolean,
        val zero_one: Boolean,
        val four_five: Boolean,
        val yet: Boolean,
        val two_three: Boolean,
        val five_six: Boolean
)
data class FieldData(
        val a: Boolean,
        val b: Boolean,
        val c: Boolean,
        val d: Boolean,
        val e: Boolean,
        val f: Boolean,
        val g: Boolean,
        val h: Boolean,
        val i: Boolean,
        val j: Boolean,
        val k: Boolean,
        val l: Boolean,
        val m: Boolean,
        val n: Boolean,
        val o: Boolean,
        val p: Boolean,
        val q: Boolean,
        val r: Boolean,
        val s: Boolean,
        val t: Boolean,
        val u: Boolean,
        val v: Boolean
)
data class AdvantageData(
        val sole: Boolean,
        val univ: Boolean,
        val woman: Boolean,
        val social: Boolean,
        val disabled: Boolean,
        val togather: Boolean,
        val fourth: Boolean,
        val retry: Boolean
)
data class BusiTypeData(
        val sole: Boolean,
        val small: Boolean,
        val big: Boolean,
        val pre: Boolean,
        val midsmall: Boolean,
        val midbig: Boolean,
        val tradi: Boolean
)
data class ExcCategoryData(
        val loan: Boolean,
        val edu: Boolean,
        val know: Boolean,
        val global: Boolean,
        val place: Boolean,
        val make: Boolean,
        val local: Boolean,
        val gov: Boolean
)