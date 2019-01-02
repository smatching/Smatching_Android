package appjam.sopt.a23rd.smatching.Fragment

import android.app.Application
import android.app.Fragment
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService

class MyPageSmatchingScrapFragment : Fragment(){
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService : NetworkService by lazy{
        ApplicationController.instance.networkService
    }
   // lateinit var
}