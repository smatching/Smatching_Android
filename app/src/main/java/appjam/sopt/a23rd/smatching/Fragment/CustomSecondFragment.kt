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
import appjam.sopt.a23rd.smatching.Adapter.CustomRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_second_custom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomSecondFragment: Fragment() {
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var customRecyclerViewAdapter: CustomRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second_custom, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        replaceFragment(SecondCustomConditionNotClickFragment())
        getUserSmatchingCondResponse()
        setRecyclerView()
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_second_custom_fl, fragment)
        transaction.commit()
    }
    private fun replaceFragmentContent(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_second_custom_fl_content, fragment)
        transaction.commit()
    }
    private fun replaceFragmentBody(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_second_custom_fl_body, fragment)
        transaction.commit()
    }
    private fun setRecyclerView() {
        customRecyclerViewAdapter =  CustomRecyclerViewAdapter(activity!!, dataList)
        fragment_second_custom_condition_rv.adapter = customRecyclerViewAdapter
        fragment_second_custom_condition_rv.layoutManager = LinearLayoutManager(activity)
        fragment_second_custom_condition_rv.addItemDecoration(DividerItemDecoration(view!!.getContext(), 1))

    }
    private fun getCustomSecondFragmentListResponse(cond_idx:Int){
        val getCustomSecondFragmentListResponse = networkService.getFitNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 999, 0, cond_idx)
        getCustomSecondFragmentListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    if (response.body()!!.status == 204 || response.body()!!.status == 206)
                        replaceFragmentContent(SecondCustomNullFragment())
                    else if(response.body()!!.status == 200){
                        val temp: ArrayList<NoticeData> = response.body()!!.data
                        if (temp.size > 0) {
                            val position = customRecyclerViewAdapter.itemCount
                            //for (a in 0..3)
                            //    allNoticeListFragmentRecyclerViewAdapter.dataList.add(temp.get(a))
                            customRecyclerViewAdapter.dataList.addAll(temp)
                            customRecyclerViewAdapter.notifyItemInserted(position)
                        }
                    }
                }
            }
        })
    }
    private fun getUserSmatchingCondResponse(){
    val getUserSmatchingCondResponse = networkService.getUserSmatchingCondResponse(SharedPreferenceController.getAuthorization(activity!!))
    getUserSmatchingCondResponse.enqueue(object : Callback<GetUserSmatchingCondResponse> {
        override fun onFailure(call: Call<GetUserSmatchingCondResponse>, t: Throwable) {
            Log.e("board list fail", t.toString())
        }

        override fun onResponse(call: Call<GetUserSmatchingCondResponse>, response: Response<GetUserSmatchingCondResponse>) {
            if (response.isSuccessful && response.body()!!.status == 200) {
                getCustomSecondFragmentListResponse(response.body()!!.data.condSummaryList.get(1).condIdx)
                fragment_second_custom_condition_notclick_tv_listsize.text = response.body()!!.data.condSummaryList.get(1).noticeCnt.toString()
            } else if(response.isSuccessful && (response.body()!!.status == 204 || response.body()!!.status == 206)) {
                replaceFragmentBody(SecondCustomEmptyFragment())
            }
        }
    })
}
}