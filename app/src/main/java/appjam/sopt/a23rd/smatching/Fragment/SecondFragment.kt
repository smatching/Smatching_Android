package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_second.*
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.HomeRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.Test2Activity
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import org.jetbrains.anko.support.v4.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SecondFragment : Fragment(){
    var loadingSecondFrag1 = 0
    var loadingSecondFrag2 = 0
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var homeFragmentFragmentRecyclerViewAdapter: HomeRecyclerViewAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        frag_second_iv_blue.setOnClickListener {
            val refresh = Intent(activity, MainActivity::class.java )
            refresh.putExtra("view", 1)
            refresh.putExtra("page", 1)
            startActivity(refresh)
        }
        setRecyclerView()
        getUserSmatchingCondResponse()
    }

    private fun setRecyclerView() {
        var noticeCnt: TextView = view!!.findViewById(R.id.fragment_second_tv_cnt)
        noticeCnt.setPaintFlags(noticeCnt.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        var ro: TextView = view!!.findViewById(R.id.fragment_second_tv_ro)
        ro.setPaintFlags(ro.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        homeFragmentFragmentRecyclerViewAdapter = HomeRecyclerViewAdapter(activity!!, dataList, SharedPreferenceController.getAuthorization(activity!!))
        fragment_second_rv.adapter = homeFragmentFragmentRecyclerViewAdapter
        fragment_second_rv.layoutManager = LinearLayoutManager(activity)
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_second_fl, fragment)
        transaction.commit()
    }
    private fun getSecondFitListResponse(cond_idx:Int){
        loadingSecondFrag1 = 0
        val getCustomSecondFragmentListResponse = networkService.getFitNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 3, 0, cond_idx)
        getCustomSecondFragmentListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 204) {
                        replaceFragment(SecondNullFragment())
                        fragment_second_ll_not_null.visibility = View.GONE
                    }else {
                        fragment_second_ll_not_null.visibility = View.VISIBLE
                        val temp: ArrayList<NoticeData> = response.body()!!.data
                        if (temp.size > 0) {
                            val position = homeFragmentFragmentRecyclerViewAdapter.itemCount
                            if(temp.size > 2) {
                                for (a in 0..2)
                                    homeFragmentFragmentRecyclerViewAdapter.dataList.add(temp.get(a))
                                homeFragmentFragmentRecyclerViewAdapter.notifyItemInserted(position)
                            } else {
                                homeFragmentFragmentRecyclerViewAdapter.dataList.addAll(temp)
                                homeFragmentFragmentRecyclerViewAdapter.notifyItemInserted(position)
                            }
                        }
                    }
                }
                loadingSecondFrag1 = 1
            }
        })
    }
    private fun getUserSmatchingCondResponse(){
        loadingSecondFrag1 = 0
        val getUserSmatchingCondResponse = networkService.getUserSmatchingCondResponse(SharedPreferenceController.getAuthorization(activity!!))
        getUserSmatchingCondResponse.enqueue(object : Callback<GetUserSmatchingCondResponse> {
            override fun onFailure(call: Call<GetUserSmatchingCondResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetUserSmatchingCondResponse>, response: Response<GetUserSmatchingCondResponse>) {
                if (response.isSuccessful && response.body()!!.status == 204) {
                    fragment_second_ll_not_null.visibility = View.GONE
                    replaceFragment(SecondEmptyFragment())
                } else if (response.isSuccessful && response.body()!!.status == 206) {
                    fragment_second_ll_not_null.visibility = View.GONE
                    replaceFragment(SecondEmptyFragment())
                } else if (response.isSuccessful && response.body()!!.status == 200) {
                    fragment_second_ll_not_null.visibility = View.VISIBLE
                    getSecondFitListResponse(response.body()!!.data.condSummaryList.get(1).condIdx)
                    fragment_second_tv_cnt.text = response.body()!!.data.condSummaryList.get(1).noticeCnt.toString()
                    fragment_second_tv_nickname.text = response.body()!!.data.nickname
                }
                loadingSecondFrag1 = 1
            }
        })
    }
}