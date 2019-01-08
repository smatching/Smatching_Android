package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService

class CorporateDetailFragment : Fragment() {
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_smatching_custom_corporate_detail, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}