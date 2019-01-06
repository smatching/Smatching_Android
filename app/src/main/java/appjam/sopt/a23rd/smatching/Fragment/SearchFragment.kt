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
    }

    private fun getSearchResponse() {
        val query: String = fragment_search_et_search.text.toString()
        val getSearchResultResponse = networkService.getSearchResultResponse(
                SharedPreferenceController.getAuthorization(activity!!), query, 20, 0)
            getSearchResultResponse.enqueue(object : Callback<GetNoticeListResponse>{
                override fun onFailure(call: Call<GetNoticeListResponse>, t : Throwable){
                    Log.e("response body fail", t.toString())
                }
                override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>){
                    if (response.isSuccessful) {
                        if(response.body()!!.status == 204) {
                            frag_search_rl.visibility = View.VISIBLE
                            frag_search_ll.visibility = View.GONE
                        }
                        else if(response.body()!!.status == 200){
                            frag_search_rl.visibility = View.GONE
                            frag_search_ll.visibility = View.VISIBLE
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
//        //통신 시작
//        val getSearchResultResponse: Call<GetNoticeListResponse> =
//                networkService.getSearchResultResponse("application/json", query, 20, 0)
//
//        if (query.isNotEmpty()) {
//            var jsonObject = JSONObject()
//
//            jsonObject.put("query", query)
//
//            //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
//            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject
//        }
//
//            getSearchResultResponse.enqueue(object : Callback<GetNoticeListResponse> {
//                override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
//                    Log.e("Search Fail", t.toString())
//                }
//
//                override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
//                    if (response.isSuccessful) {
//                        val temp : ArrayList<NoticeData> = response.body()!!.data
//                        if (temp.size > 0){
//                            val position = searchAdapter.itemCount
//                            val scrapCnt: TextView = view!!.findViewById(R.id.fragment_search_tv_count)
//                            val tv1 : TextView = view!!.findViewById(R.id.fragment_search_tv1)
//                            val tv2 : TextView = view!!.findViewById(R.id.fragment_search_tv2)
//                            scrapCnt.setVisibility(View.VISIBLE)
//                            tv1.setVisibility(View.VISIBLE)
//                            tv2.setVisibility(View.VISIBLE)
//                            scrapCnt.setText(temp.size.toString())
//                            searchAdapter.dataList.addAll(temp)
//                            searchAdapter.notifyItemInserted(position)
//                        }
//                        else
//                            replaceFragment(SearchNoresultFragment())
//                    }
//                }
//            })
//        }


    private fun replaceFragment(fragment: Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_search_fl, fragment)
        transaction.commit()
    }
}