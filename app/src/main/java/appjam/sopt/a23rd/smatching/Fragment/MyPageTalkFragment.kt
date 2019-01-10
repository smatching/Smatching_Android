package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Data.UserInfoData
import appjam.sopt.a23rd.smatching.Get.GetUserInfoDataResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_my_page_user.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageTalkFragment : Fragment(){

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_page_user_talk, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserInfo()

//        fragment_my_page_user_rl_profile.setOnClickListener{
//            replaceFragment(MyPageSettingMemberInfoFragment())
//        }
        fragment_my_page_user_talkscrap.setOnClickListener {
            replaceFragment(MyPageTalkFragment())
        }
        fragment_my_page_user_smatchingscrap.setOnClickListener{
            replaceFragment(MyPageFragment())
        }
    }

        private fun replaceFragment(fragment: Fragment) {
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.act_bottom_navi_fl, fragment)
            transaction.commit()
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
}