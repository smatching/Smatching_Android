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
import android.widget.TextView
import android.widget.Toast
import appjam.sopt.a23rd.smatching.Adapter.SearchAdapter
import appjam.sopt.a23rd.smatching.Adapter.SmatchingScrapRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import appjam.sopt.a23rd.smatching.post.PostSignUpResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.fragment_my_page_user.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_result.*
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    val dataList: ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_search_iv_search.setOnClickListener {
            getSearchResponse()
        }

    }
    private fun setRecyclerView() {
        searchAdapter = SearchAdapter(activity!!, dataList)
        fragment_search_list_rv.adapter = searchAdapter
        fragment_search_list_rv.layoutManager = LinearLayoutManager(activity)
        fragment_search_list_rv.addItemDecoration(DividerItemDecoration(view!!.getContext(), 1))
    }

    private fun getSearchResponse() {
        val query: String = fragment_search_et_search.text.toString()
        val getSearchResultResponse = networkService.getSearchResultResponse(
                SharedPreferenceController.getAuthorization(activity!!), query, 999, 0)
            getSearchResultResponse.enqueue(object : Callback<GetNoticeListResponse>{
                override fun onFailure(call: Call<GetNoticeListResponse>, t : Throwable){
                    Log.e("response body fail", t.toString())
                }
                override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>){
                    if (response.isSuccessful) {
                        if(response.body()!!.status == 204) {
                            frag_search_rl.visibility = View.VISIBLE
                            fragment_search_list_ll.visibility = View.GONE
                        }
                        else if(response.body()!!.status == 200){
                            frag_search_rl.visibility = View.GONE
                            fragment_search_list_ll.visibility = View.VISIBLE
                            setRecyclerView()
                            val temp : ArrayList<NoticeData> = response.body()!!.data
                            val position = searchAdapter.itemCount
                            val scrapCnt: TextView = fragment_search_tv_count
                            scrapCnt.setText(temp.size.toString())
                            searchAdapter.dataList.clear()
                            searchAdapter.dataList.addAll(temp)
                            searchAdapter.notifyItemInserted(position)
                        }
                }
            }
            })
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_search_fl, fragment)
        transaction.commit()
    }
}