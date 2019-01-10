package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import appjam.sopt.a23rd.smatching.Adapter.CustomRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserSmatchingCondResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_first_custom.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomFirstFragment : Fragment(){
    var customFirstFragmentPage = 0
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var customRecyclerViewAdapter: CustomRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_custom, container, false)
    }
/*
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if((Fragment() as CustomFirstFragment).loadingFirstCustom1 == 1
                && (Fragment() as CustomFirstFragment).loadingFirstCustom2 == 1
                && (Fragment() as FirstCustomConditionClickFragment).loadingFirstCustomConditionClick1 == 1
                && (Fragment() as FirstCustomConditionClickFragment).loadingFirstCustomConditionClick2 == 1
                && (Fragment() as FirstCustomConditionNotClickFragment).loadingFirstCustomConditionNotClick == 1
                && (Fragment() as CustomSecondFragment).loadingSecondCustom1 == 1
                && (Fragment() as CustomSecondFragment).loadingSecondCustom2 == 1
                && (Fragment() as SecondCustomConditionClickFragment).loadingSecondCustomConditionClick1 == 1
                && (Fragment() as SecondCustomConditionClickFragment).loadingSecondCustomConditionClick2 == 1
                && (Fragment() as SecondCustomConditionNotClickFragment).loadingSecondCustomConditionNotClick == 1) {
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
        }
        else {
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
            (activity as AppCompatActivity).findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
        }
    }*/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        replaceFragment(FirstCustomConditionNotClickFragment())
        getUserSmatchingCondResponse()
        setRecyclerView()
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_first_custom_fl, fragment)
        transaction.commit()
    }
    private fun replaceFragmentContent(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_first_custom_fl_content, fragment)
        transaction.commit()
    }
    private fun replaceFragmentBody(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.frag_first_custom_fl_body, fragment)
        transaction.commit()
    }
    private fun setRecyclerView() {
        customRecyclerViewAdapter =  CustomRecyclerViewAdapter(activity!!, dataList, SharedPreferenceController.getAuthorization(activity!!))
        fragment_first_custom_condition_rv.adapter = customRecyclerViewAdapter
        fragment_first_custom_condition_rv.layoutManager = LinearLayoutManager(activity)

    }
    private fun getCustomFirstFragmentListResponse(cond_idx:Int){
        val getCustomFirstFragmentListResponse = networkService.getFitNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 999, 0, cond_idx)
        getCustomFirstFragmentListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful) {
                    if (response.body()!!.status == 204)
                        replaceFragmentContent(FirstCustomNullFragment())
                    else if (response.body()!!.status == 200 || response.body()!!.status == 206) {
                        val temp: ArrayList<NoticeData> = response.body()!!.data
                        if (temp.size > 0) {
                            val position = customRecyclerViewAdapter.itemCount
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
                if(response.isSuccessful) {
                    if (response.body()!!.status == 200 || response.body()!!.status == 206) {
                        getCustomFirstFragmentListResponse(response.body()!!.data.condSummaryList.get(0).condIdx)
                        fragment_first_custom_condition_notclick_tv_listsize.text = response.body()!!.data.condSummaryList.get(0).noticeCnt.toString()
                    } else if (response.body()!!.status == 204) {
                        replaceFragmentBody(FirstCustomEmptyFragment())
                    }
                }
            }
        })
    }
}