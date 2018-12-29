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
import appjam.sopt.a23rd.smatching.Adapter.CustomRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_all_notice_list.*
import kotlinx.android.synthetic.main.fragment_custom.*
import kotlinx.android.synthetic.main.fragment_custom_condition_click.*
import kotlinx.android.synthetic.main.fragment_custom_condition_notclick.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomFragment : Fragment(){
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var customRecyclerViewAdapter: CustomRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        replaceFragment(CustomConditionNotClickFragment())
        setRecyclerView()
        getCustomFragmentResponse()
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_custom_fl, fragment)
        transaction.commit()
    }
    private fun setRecyclerView() {
        customRecyclerViewAdapter =  CustomRecyclerViewAdapter(activity!!, dataList)
        fragment_custom_condition_rv.adapter = customRecyclerViewAdapter
        fragment_custom_condition_rv.layoutManager = LinearLayoutManager(activity)
        fragment_custom_condition_rv.addItemDecoration(DividerItemDecoration(view!!.getContext(), 1))

    }
    private fun getCustomFragmentResponse(){
        val getCustomFragmentResponse = networkService.getAllNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 20, 0)
        getCustomFragmentResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    val temp : ArrayList<NoticeData> = response.body()!!.data
                    if (temp.size > 0){
                        val position = customRecyclerViewAdapter.itemCount
                        //for (a in 0..3)
                        //    allNoticeListFragmentRecyclerViewAdapter.dataList.add(temp.get(a))
                        customRecyclerViewAdapter.dataList.addAll(temp)
                        customRecyclerViewAdapter.notifyItemInserted(position)

                    }
                }
            }
        })
    }
}