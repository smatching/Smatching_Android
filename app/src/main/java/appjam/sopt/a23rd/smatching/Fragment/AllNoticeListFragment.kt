package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Adapter.AllNoticeListFragmentRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Adapter.HomeRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetAllNoticeListSizeResponse
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.Put.PutNoticeScrap
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_all_notice_list.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.rv_item_home.*
import org.jetbrains.anko.imageResource
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
        getAllNoticeListSizeResponse()
//        rv_item_home_rl_item.setOnItemClickListener{
//            replaceFragment(SmatchingCustomCorporateDetailFragment())
//        }
    }
    private fun setRecyclerView() {
        allNoticeListFragmentRecyclerViewAdapter =  AllNoticeListFragmentRecyclerViewAdapter(activity!!, dataList, SharedPreferenceController.getAuthorization(activity!!))
        fragment_all_notice_list_rv.adapter = allNoticeListFragmentRecyclerViewAdapter
        fragment_all_notice_list_rv.layoutManager = LinearLayoutManager(activity)
        fragment_all_notice_list_rv.addItemDecoration(DividerItemDecoration(view!!.getContext(), 1))
    }
    private fun getAllNoticeListFragmentResponse(){

        val getAllNoticeListResponse = networkService.getAllNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 999, 0)
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
    private fun getAllNoticeListSizeResponse(){
        val getAllNoticeListSizeResponse = networkService.getAllNoticeListSizeResponse()
        getAllNoticeListSizeResponse.enqueue(object : Callback<GetAllNoticeListSizeResponse> {
            override fun onFailure(call: Call<GetAllNoticeListSizeResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetAllNoticeListSizeResponse>, response: Response<GetAllNoticeListSizeResponse>) {
                if (response.isSuccessful && (response.body()!!.status == 200)) {
                    fragment_all_notice_list_all_count.text = response.body()!!.data.toString()
                }
            }
        })
    }
}
