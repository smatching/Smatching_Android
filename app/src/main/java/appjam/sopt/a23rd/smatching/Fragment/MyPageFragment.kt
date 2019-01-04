package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.SmatchingScrapRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_my_page_user.*
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
        return inflater.inflate(R.layout.fragment_my_page_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        getSmatchingScrapListResponse()

        fragment_my_page_user_rl_profile.setOnClickListener{
            replaceFragment(MyPageSettingMemberInfoFragment())
        }
        fragment_my_page_user_talkscrap.setOnClickListener {
            replaceFragment(MyPageTalkFragment())
        }
        fragment_my_page_user_smatchingscrap.setOnClickListener{
            replaceFragment(MyPageFragment())
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
        smatchingScrapFragmentRecyclerViewAdapter = SmatchingScrapRecyclerViewAdapter(activity!!, dataList)
        fragment_my_page_user_rv.adapter = smatchingScrapFragmentRecyclerViewAdapter
        fragment_my_page_user_rv.layoutManager = LinearLayoutManager(activity)
    }
    private fun getSmatchingScrapListResponse(){
        val getSmatchingScrapListResponse = networkService.getSmatchingScrapListResponse(SharedPreferenceController.getAuthorization(activity!!), 20, 0)
        getSmatchingScrapListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    val temp : ArrayList<NoticeData> = response.body()!!.data
                    if (temp.size > 0){
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
    private fun replaceFragment(fragment: Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
}