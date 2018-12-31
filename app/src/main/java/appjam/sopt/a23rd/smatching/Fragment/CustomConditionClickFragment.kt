package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Adapter.CustomRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.CondSummaryListData
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Data.UserData
import appjam.sopt.a23rd.smatching.Get.GetSmatchingListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_custom_condition_click.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomConditionClickFragment : Fragment(){
    val dataList : ArrayList<CondSummaryListData> by lazy {
        ArrayList<CondSummaryListData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    var test:Int = 1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom_condition_click, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_custom_condition_click_iv_back.setOnClickListener {
            replaceFragment(CustomConditionNotClickFragment())
        }
        //getUserSmatchingCondResponse()
        testResponse()
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_custom_fl, fragment)
        transaction.commit()
    }
    private fun getUserSmatchingCondResponse(){
        val getUserSmatchingCondResponse = networkService.getUserSmatchingCondResponse(SharedPreferenceController.getAuthorization(activity!!))
        getUserSmatchingCondResponse.enqueue(object : Callback<GetUserSmatchingCondResponse> {
            override fun onFailure(call: Call<GetUserSmatchingCondResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetUserSmatchingCondResponse>, response: Response<GetUserSmatchingCondResponse>) {
                if (response.isSuccessful && response.body()!!.data.condSummaryList.size == 2){
                    getUserSmatchingListResponse(response.body()!!.data.condSummaryList.get(0).condIdx)
                    fragment_custom_condition_click_tv_smatching_name.text = response.body()!!.data.condSummaryList.get(0).condName
                } else if (response.isSuccessful && response.body()!!.data.condSummaryList.size == 1){
                    dataList.addAll(response.body()!!.data.condSummaryList)
                    toast("2")
                } else
                    toast("테스트2")
            }
        })
    }
    private fun getUserSmatchingListResponse(condIdx: Int){
        val getUserSmatchingListResponse = networkService.getSmatchingCondsResponse(condIdx)
        getUserSmatchingListResponse.enqueue(object : Callback<GetSmatchingListResponse> {
            override fun onFailure(call: Call<GetSmatchingListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetSmatchingListResponse>, response: Response<GetSmatchingListResponse>) {
                if (response.isSuccessful) {
                    toast("테스트")
                }
            }
        })
    }
    private fun testResponse(){
        val getUserSmatchingListResponse = networkService.getSmatchingCondsResponse(1)
        getUserSmatchingListResponse.enqueue(object : Callback<GetSmatchingListResponse> {
            override fun onFailure(call: Call<GetSmatchingListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetSmatchingListResponse>, response: Response<GetSmatchingListResponse>) {
                if (response.isSuccessful) {
                    toast("테스트")
                }
            }
        })
    }

    /*                    getSmatchingListFirstResponse = networkService.getSmatchingCondsResponse(response.body()!!.data.condSummaryList.get(0).condIdx)
                    getSmatchingListFirstResponse = Response<GetSmatchingListResponse>().body()!!.data*/
    private fun setTextView() {
    }
}