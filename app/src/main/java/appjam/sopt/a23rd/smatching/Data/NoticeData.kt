package appjam.sopt.a23rd.smatching.Data

data class NoticeData(
        val noticeIdx : Int,
        val title: String,
        val institution : String,
        val dday: Int,
        var scrap : Int,
        val readCnt : Int
)