package appjam.sopt.a23rd.smatching.Fragment

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import appjam.sopt.a23rd.smatching.Adapter.MyFragmentStatePagerAdapter
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_home.*
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import appjam.sopt.a23rd.smatching.Adapter.HomeRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Adapter.PagerAdapter
import appjam.sopt.a23rd.smatching.Data.HomeData
import appjam.sopt.a23rd.smatching.MainActivity
import org.jetbrains.anko.support.v4.intentFor


class HomeFragment : Fragment(){
    var mInstace : HomeFragment? = null
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

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setRecyclerView()
    }
    private fun setRecyclerView() {
        var dataList: ArrayList<HomeData> = ArrayList()
        dataList.add(HomeData("a", "b", "c"))


        homeRecyclerViewAdapter = HomeRecyclerViewAdapter(activity!!, dataList)
        fragment_home_rv.adapter = homeRecyclerViewAdapter
        fragment_home_rv.layoutManager = GridLayoutManager(activity, 3)
    }
}