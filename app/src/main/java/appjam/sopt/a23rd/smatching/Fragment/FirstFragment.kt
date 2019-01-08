package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.HomeFragmentRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Adapter.HomeRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_second_custom.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Handler
import android.support.v4.app.FragmentTransaction


class FirstFragment : Fragment(){
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var homeFragmentFragmentRecyclerViewAdapter: HomeRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //
        (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
        (activity as AppCompatActivity).findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
        //


        setRecyclerView()
        getUserSmatchingCondResponse()

        Handler().postDelayed({
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
        }, 1000)

    }

    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_first_fl, fragment)
        transaction.commit()
    }
    private fun setRecyclerView() {
        var noticeCnt: TextView = view!!.findViewById(R.id.fragment_first_tv_cnt)
        noticeCnt.setPaintFlags(noticeCnt.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)
        var ro: TextView = view!!.findViewById(R.id.fragment_first_tv_ro)
        ro.setPaintFlags(ro.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        homeFragmentFragmentRecyclerViewAdapter = HomeRecyclerViewAdapter(activity!!, dataList)
        fragment_first_rv.adapter = homeFragmentFragmentRecyclerViewAdapter
        fragment_first_rv.layoutManager = LinearLayoutManager(activity)
    }
    private fun getFirstFitListResponse(cond_idx:Int){
        val getCustomSecondFragmentListResponse = networkService.getFitNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 3, 0, cond_idx)
        getCustomSecondFragmentListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    if (response.body()!!.status == 204)
                        replaceFragment(FirstNullFragment())
                    else {
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
                if (response.isSuccessful && response.body()!!.status == 204) {
                    replaceFragment(NullFragment())
                } else if ((response.isSuccessful && response.body()!!.status == 200)
                        || (response.isSuccessful && response.body()!!.status == 206)) {
                    getFirstFitListResponse(response.body()!!.data.condSummaryList.get(0).condIdx)
                    fragment_first_tv_cnt.text = response.body()!!.data.condSummaryList.get(0).noticeCnt.toString()
                    fragment_first_tv_nickname.text = response.body()!!.data.nickname
                }

                val state =  Bundle()
                state.putInt("firstState", 1)
            }
        })
    }
}