package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.SmatchingScrapRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Data.UserInfoData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserInfoDataResponse
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_my_page_user.*
import kotlinx.android.synthetic.main.fragment_mypage_setting_memberinfo.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageFragment : Fragment(){
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var smatchingScrapFragmentRecyclerViewAdapter: SmatchingScrapRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getUserInfo()
        return inflater.inflate(R.layout.fragment_my_page_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
        (activity as AppCompatActivity).findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
        //

        getSmatchingScrapListResponse()

        (activity as MainActivity).setpageNum(3)

        Handler().postDelayed({
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
        }, 1000)
      /*  fragment_my_page_user_rl_profile.setOnClickListener{
            replaceFragment(MyPageSettingMemberInfoFragment())
        }
        */
        fragment_my_page_user_talkscrap.setOnClickListener {
            replaceFragment(MyPageTalkFragment())
        }
        fragment_my_page_user_smatchingscrap.setOnClickListener{
            replaceFragment(MyPageFragment())
        }
        fragment_mypage_user_btn_search.setOnClickListener {
            getScrapSearchResponse()
        }

        /*
        fragment_my_page_user_smatchingscrap.setOnClickListener {
            fragment_my_page_user_smatchingscrap.setTextColor(resources.getColor(R.color.colorText))
            fragment_my_page_user_talkscrap.setTextColor(resources.getColor(R.color.colorTextshallow))
        }
        fragment_my_page_user_talkscrap.setOnClickListener {
            fragment_my_page_user_talkscrap.setTextColor(resources.getColor(R.color.colorText))
            fragment_my_page_user_smatchingscrap.setTextColor(resources.getColor(R.color.colorTextshallow))
        }*/
    }
    private fun setRecyclerView() {
        smatchingScrapFragmentRecyclerViewAdapter = SmatchingScrapRecyclerViewAdapter(activity!!, dataList, SharedPreferenceController.getAuthorization(activity!!))
        fragment_my_page_user_rv.adapter = smatchingScrapFragmentRecyclerViewAdapter
        fragment_my_page_user_rv.layoutManager = LinearLayoutManager(activity)
    }
    private fun getSmatchingScrapListResponse(){
        val getSmatchingScrapListResponse = networkService.getSmatchingScrapListResponse(SharedPreferenceController.getAuthorization(activity!!),
                999, 0)
        getSmatchingScrapListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    if(response.body()!!.status == 204) {
                        Log.d("mypage test : ", response.body()!!.status.toString())
                        fragment_my_page_user_ll.setVisibility(View.GONE)
                        fragment_my_page_user_line.setVisibility(View.GONE)
                        fragment_my_page_user_iv_noscrap.setVisibility(View.VISIBLE)
                    }
                    else {
                        val temp : ArrayList<NoticeData> = response.body()!!.data
                        setRecyclerView()
                        val position = smatchingScrapFragmentRecyclerViewAdapter.itemCount
                        val scrapCnt: TextView = view!!.findViewById(R.id.fragment_my_page_user_tv_scrapCnt)
                        scrapCnt.setText(temp.size.toString())
                        smatchingScrapFragmentRecyclerViewAdapter.dataList.addAll(temp)
                        smatchingScrapFragmentRecyclerViewAdapter.notifyItemInserted(position)
                    }
                }
            }
        })
    }
    private fun getScrapSearchResponse() {
        val query: String = fragment_mypage_user_et_search.text.toString()
        val getScrapSearchResponse = networkService.getScrapSearchResponse(SharedPreferenceController.getAuthorization(activity!!), query, 999, 0)
        getScrapSearchResponse.enqueue(object : Callback<GetNoticeListResponse>{
            override fun onFailure(call: Call<GetNoticeListResponse>, t : Throwable){
                Log.e("response body fail", t.toString())
            }
            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>){
                if (response.isSuccessful) {
                    if(response.body()!!.status == 204) {
                        frag_mypage_user_search_rl.visibility = View.VISIBLE
                        fragment_mypage_user_search_list_ll.visibility = View.GONE
                        fragment_my_page_user_tv_scrapCnt.text = "0"
                    }
                    else if(response.body()!!.status == 200) {
                        frag_mypage_user_search_rl.visibility = View.GONE
                        fragment_mypage_user_search_list_ll.visibility = View.VISIBLE
                        setRecyclerView()
                        val temp: ArrayList<NoticeData> = response.body()!!.data
                        val position = smatchingScrapFragmentRecyclerViewAdapter.itemCount
                        val scrapCnt: TextView = fragment_my_page_user_tv_scrapCnt
                        scrapCnt.setText(temp.size.toString())
                        smatchingScrapFragmentRecyclerViewAdapter.dataList.clear()
                        smatchingScrapFragmentRecyclerViewAdapter.dataList.addAll(temp)
                        smatchingScrapFragmentRecyclerViewAdapter.notifyItemInserted(position)
                    }
                }
            }
        })
    }
    private fun getUserInfo(){
        val getUserInfo = networkService.getUserInfo(SharedPreferenceController.getAuthorization(activity!!))
        getUserInfo.enqueue(object : Callback<GetUserInfoDataResponse> {
            override fun onFailure(call: Call<GetUserInfoDataResponse>, t: Throwable) {
                Log.e("response body fail", t.toString())
            }
            override fun onResponse(call: Call<GetUserInfoDataResponse>, response: Response<GetUserInfoDataResponse>) {
                if (response.isSuccessful) {
                    Log.e("mypage setting member", response.body()!!.status.toString())
                    if (response.body()!!.status != 200)
                        toast(response.body()!!.message)
                    else {
                        val arr: UserInfoData = response.body()!!.data
                        fragment_my_page_profile_tv_nickname.setText(arr.nickname)
                    }
                }
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
}