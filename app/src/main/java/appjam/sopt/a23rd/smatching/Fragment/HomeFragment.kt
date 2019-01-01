package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_home.*
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import appjam.sopt.a23rd.smatching.Adapter.HomeRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Adapter.PagerAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import appjam.sopt.a23rd.smatching.MainActivity
import kotlinx.android.synthetic.main.content_main.*


class HomeFragment : Fragment(){
    var mInstace : HomeFragment? = null
    val dataList : ArrayList<NoticeData> by lazy {
        ArrayList<NoticeData>()
    }
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    lateinit var homeRecyclerViewAdapter: HomeRecyclerViewAdapter
    //다른 스레드에서 인스턴스 생성해줘서 하나만 생성됨
    @Synchronized
    fun getInstance(): HomeFragment{
        if(mInstace == null){
            mInstace = HomeFragment()
        }
        return mInstace!!
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //var homeCount : Int = 1
        val mViewPager = view.findViewById<ViewPager>(R.id.fragment_home_vp)
        val mTabLayout = view.findViewById<TabLayout>(R.id.fragment_home_tl)
        val bottomNaviLayout : View = activity!!.layoutInflater.inflate(R.layout.home_navigation_tab, null, false)//inflate뷰를 붙여줌

        /*if(homeCount < 1) {
            mViewPager.adapter = MyFragmentStatePagerAdapter(childFragmentManager, 1)
            //mTabLayout.setupWithViewPager(mViewPager)
            //mTabLayout.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.home_navigation_tab_first) as ImageView
        } else {*/
        mViewPager.adapter = PagerAdapter(childFragmentManager, 2)
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.home_navigation_tab_first) as ImageView
        mTabLayout.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.home_navigation_tab_second) as ImageView
        //}
        setRecyclerView()
        getAllNoticeListResponse()

        fragment_home_iv_more_smatching.setOnClickListener {
            replaceFragment(SmatchingCustom())
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBackground))
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).setTitleTextColor(resources.getColor(R.color.colorText))
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = true
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            (activity as AppCompatActivity).findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_main_iv_home).isSelected = false
            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_main_iv_smatching).isSelected = true
            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_main_iv_talk).isSelected = false
            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_main_iv_my_page).isSelected = false
            (activity as AppCompatActivity).findViewById<TextView>(R.id.act_bottom_navi_tv_title).setText("맞춤지원")
            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_bottom_navi_iv_title).visibility = View.INVISIBLE

        }
        fragment_home_iv_more.setOnClickListener{
            replaceFragment(AllNoticeListFragment())
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
            //(activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false ??
            (activity as AppCompatActivity).findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
//            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_main_iv_talk).isSelected = false
//            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_main_iv_my_page).isSelected = false
            (activity as AppCompatActivity).findViewById<TextView>(R.id.act_bottom_navi_tv_title).setText("전체공고")
            (activity as AppCompatActivity).findViewById<ImageView>(R.id.act_bottom_navi_iv_title).visibility = View.INVISIBLE
        }
    }


    private fun setRecyclerView() {
        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(activity!!, dataList)
        homeRecyclerViewAdapter.currentView = 0
        fragment_home_rv.adapter = homeRecyclerViewAdapter
        fragment_home_rv.layoutManager = LinearLayoutManager(activity)
    }
    private fun getAllNoticeListResponse(){
        val getAllNoticeListResponse = networkService.getAllNoticeListResponse(SharedPreferenceController.getAuthorization(activity!!), 4, 0)
        getAllNoticeListResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("board list fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful){
                    val temp : ArrayList<NoticeData> = response.body()!!.data
                    if (temp.size > 0){
                        val position = homeRecyclerViewAdapter.itemCount
                        for (a in 0..3)
                            homeRecyclerViewAdapter.dataList.add(temp.get(a))
                        homeRecyclerViewAdapter.notifyItemInserted(position)

                    }
                }
            }
        })
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
}