package appjam.sopt.a23rd.smatching.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.SmatchingScrapRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_my_page_smtching_scrap.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageSmatchingScrapFragment : Fragment(){
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var smatchingScrapFragmentRecyclerViewAdapter: SmatchingScrapRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_page_smtching_scrap, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSmatchingScrapListResponse()

        //완료버튼 눌러도 검색기능 동작
        fragment_mypage_user_et_search.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                        if (actionId == EditorInfo.IME_ACTION_DONE) { // 뷰의 id를 식별, 키보드의 완료 키 입력 검출
                val mEditText = fragment_mypage_user_et_search
                val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(mEditText.windowToken, 0)
                getScrapSearchResponse()
                return@OnEditorActionListener true
            }
            false
        })

        fragment_mypage_user_btn_search.setOnClickListener {
            getScrapSearchResponse()
            val mEditText = fragment_mypage_user_et_search
            val inputMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(mEditText.windowToken, 0)
        }
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
                        fragment_my_page_user_ll.setVisibility(View.VISIBLE)
                        fragment_my_page_user_line.setVisibility(View.VISIBLE)
                        fragment_my_page_user_iv_noscrap.setVisibility(View.GONE)
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
        getScrapSearchResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t : Throwable){
                Log.e("response body fail", t.toString())
            }
            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>){
                if (response.isSuccessful) {
                    if(response.body()!!.status == 204) {
                        fragment_mypage_user_search_list_ll.visibility = View.GONE
                        fragment_my_page_user_tv_scrapCnt.text = "0"
                    }
                    else if(response.body()!!.status == 200) {
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
}