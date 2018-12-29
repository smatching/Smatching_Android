package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Adapter.AllNoticeListFragmentRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Adapter.HomeRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_all_notice_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllNoticeListFragment : Fragment(){
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var allNoticeListFragmentRecyclerViewAdapter: AllNoticeListFragmentRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_all_notice_list, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        getAllNoticeListFragmentResponse()

    }
    private fun setRecyclerView() {
        allNoticeListFragmentRecyclerViewAdapter =  AllNoticeListFragmentRecyclerViewAdapter(activity!!, dataList)
        fragment_all_notice_list_rv.adapter = allNoticeListFragmentRecyclerViewAdapter
        fragment_all_notice_list_rv.layoutManager = LinearLayoutManager(activity)
        fragment_all_notice_list_rv.addItemDecoration(DividerItemDecoration(view!!.getContext(), 1))

    }
    private fun getAllNoticeListFragmentResponse(){
        val getAllNoticeListResponse = networkService.getAllNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 20, 0)
        getAllNoticeListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    val temp : ArrayList<NoticeData> = response.body()!!.data
                    if (temp.size > 0){
                        val position = allNoticeListFragmentRecyclerViewAdapter.itemCount
                        //for (a in 0..3)
                        //    allNoticeListFragmentRecyclerViewAdapter.dataList.add(temp.get(a))
                        allNoticeListFragmentRecyclerViewAdapter.dataList.addAll(temp)
                        allNoticeListFragmentRecyclerViewAdapter.notifyItemInserted(position)

                    }
                }
            }
        })
    }
}