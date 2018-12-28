package appjam.sopt.a23rd.smatching.Fragment

import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.HomeFragmentRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_first.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FirstFragment : Fragment(){
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var homeFragmentFragmentRecyclerViewAdapter: HomeFragmentRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
    }
    private fun setRecyclerView() {
        var noticeCnt: TextView = view!!.findViewById(R.id.fragment_first_tv_cnt)
        noticeCnt.setPaintFlags(noticeCnt.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        homeFragmentFragmentRecyclerViewAdapter = HomeFragmentRecyclerViewAdapter(activity!!, dataList)
        fragment_first_rv.adapter = homeFragmentFragmentRecyclerViewAdapter
        fragment_first_rv.layoutManager = LinearLayoutManager(activity)
    }
    private fun getFitNoticeListResponse(){
        val getFitNoticeListResponse = networkService.getFitNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!),0 ,3, 0)
        getFitNoticeListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    //val nickname = SharedPreferenceController.getNickname(activity!!)
                    //fragment_first_tv_nickname.text = nickname
                    val temp : ArrayList<NoticeData> = response.body()!!.data
                    if (temp.size > 0){
                        val position = homeFragmentFragmentRecyclerViewAdapter.itemCount
                        homeFragmentFragmentRecyclerViewAdapter.dataList.addAll(temp)
                        homeFragmentFragmentRecyclerViewAdapter.notifyItemInserted(position)
                    }
                }
            }
        })
    }
}