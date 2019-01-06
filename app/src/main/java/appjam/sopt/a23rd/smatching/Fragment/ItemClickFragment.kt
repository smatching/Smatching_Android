//package appjam.sopt.a23rd.smatching.Fragment
//
//import android.os.Bundle
//import android.support.v4.app.Fragment
//import android.support.v4.app.FragmentTransaction
//import android.support.v7.widget.LinearLayoutManager
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import appjam.sopt.a23rd.smatching.Adapter.ItemClickAdapter
//import appjam.sopt.a23rd.smatching.Adapter.SmatchingScrapRecyclerViewAdapter
//import appjam.sopt.a23rd.smatching.Data.DetailData
//import appjam.sopt.a23rd.smatching.Data.NoticeData
//import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
//import appjam.sopt.a23rd.smatching.R
//import appjam.sopt.a23rd.smatching.R.layout.fragment_detailcontent
//import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
//import appjam.sopt.a23rd.smatching.network.ApplicationController
//import appjam.sopt.a23rd.smatching.network.NetworkService
//import kotlinx.android.synthetic.main.fragment_detailcontent.*
//import kotlinx.android.synthetic.main.fragment_my_page_user.*
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class ItemClickFragment : Fragment(){
//    val dataList : ArrayList<DetailData> by lazy {
//        ArrayList<DetailData>()
//    }
//    val networkService: NetworkService by lazy {
//        ApplicationController.instance.networkService
//    }
//    lateinit var itemClickAdapter : ItemClickAdapter
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_detailcontent, container, false)
//    }
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        setRecyclerView()
//        getSmatchingScrapListResponse()
//
//        /*
//        fragment_my_page_user_smatchingscrap.setOnClickListener {
//            fragment_my_page_user_smatchingscrap.setTextColor(resources.getColor(R.color.colorText))
//            fragment_my_page_user_talkscrap.setTextColor(resources.getColor(R.color.colorTextshallow))
//        }
//        fragment_my_page_user_talkscrap.setOnClickListener {
//            fragment_my_page_user_talkscrap.setTextColor(resources.getColor(R.color.colorText))
//            fragment_my_page_user_smatchingscrap.setTextColor(resources.getColor(R.color.colorTextshallow))
//        }*/
//    }
//    private fun setRecyclerView() {
//        itemClickAdapter = ItemClickAdapter(activity!!, dataList)
//        fragment_detailcontent.adapter = itemClickAdapter
//        fragment_detailcontent.layoutManager = LinearLayoutManager(activity)
//    }
//    private fun getSmatchingScrapListResponse(){
//        val getSmatchingScrapListResponse = networkService.getSmatchingScrapListResponse(SharedPreferenceController.getAuthorization(activity!!), 20, 0)
//        getSmatchingScrapListResponse.enqueue(object : Callback<GetNoticeListResponse> {
//            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
//                Log.e("board list fail", t.toString())
//            }
//
//            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
//                if (response.isSuccessful){
//                    val temp : ArrayList<DetailData> = response.body()!!.data
//                    if (temp.size > 0){
//                        val position = itemClickAdapter.itemCount
//                        itemClickAdapter.dataList.addAll(temp)
//                        itemClickAdapter.notifyItemInserted(position)
//                    }
//                }
//            }
//        })
//    }
//    private fun replaceFragment(fragment: Fragment) {
//        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
//        transaction.replace(R.id.act_bottom_navi_fl, fragment)
//        transaction.commit()
//    }
//}