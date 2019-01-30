package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import appjam.sopt.a23rd.smatching.Adapter.MyPageAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.Data.UserInfoData
import appjam.sopt.a23rd.smatching.Get.GetUserInfoDataResponse
import appjam.sopt.a23rd.smatching.MainActivity
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.fragment_my_page_user.*
import org.jetbrains.anko.support.v4.toast
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getUserInfo()
        return inflater.inflate(R.layout.fragment_my_page_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
        (activity as AppCompatActivity).findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
        //

        (activity as MainActivity).setpageNum(3)

        Handler().postDelayed({
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
        }, 1000)
      /*  fragment_my_page_user_rl_profile.setOnClickListener{
            replaceFragment(MyPageSettingMemberInfoFragment())
        }
        */
        configureBottomNavigation()
    }
    private fun getUserInfo(){
        val getUserInfo = networkService.getUserInfo(SharedPreferenceController.getAuthorization(activity!!))
        getUserInfo.enqueue(object : Callback<GetUserInfoDataResponse> {
            override fun onFailure(call: Call<GetUserInfoDataResponse>, t: Throwable) {
                Log.e("response body fail", t.toString())
            }
            override fun onResponse(call: Call<GetUserInfoDataResponse>, response: Response<GetUserInfoDataResponse>) {
                if (response.isSuccessful) {
                    Log.e("mypage setting member", response.body()!!.status.toString())
                    if (response.body()!!.status != 200)
                        toast(response.body()!!.message)
                    else {
                        val arr: UserInfoData = response.body()!!.data
                        fragment_my_page_profile_tv_nickname.setText(arr.nickname)
                    }
                }
            }
        })
    }
    private fun configureBottomNavigation() {
    val mViewPager = (activity as AppCompatActivity).findViewById<ViewPager>(R.id.fragment_mypage_viewpager)
    val mTabLayout = (activity as AppCompatActivity).findViewById<TabLayout>(R.id.fragment_mypage_tablayout)
    val bottomNaviLayout: View = this.layoutInflater.inflate(R.layout.mypage_tab, null, false)

    mViewPager.adapter = MyPageAdapter((activity as AppCompatActivity).supportFragmentManager, 2)
    mTabLayout.setupWithViewPager(mViewPager)
    mTabLayout.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.fragment_my_page_user_smatchingscrap) as ImageView
    mTabLayout.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.fragment_my_page_user_talkscrap) as ImageView
    }
}



