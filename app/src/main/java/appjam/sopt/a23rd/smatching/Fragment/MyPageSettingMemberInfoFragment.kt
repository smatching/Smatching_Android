package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Data.UserInfoData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserInfoDataResponse
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.R.id.*
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.fragment_mypage_setting_memberinfo.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val etNickname = fragment_mypage_setting_memberinfo_et_nickname
val etEmail = fragment_mypage_setting_memberinfo_et_email
val etPassword =  fragment_mypage_setting_memberinfo_et_password
val etNewPassword = fragment_mypage_setting_memberinfo_et_new_password
val etNewPasswordAgain = fragment_mypage_setting_memberinfo_et_new_password_check

class MyPageSettingMemberInfoFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getUserInfo()

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(edit: Editable) {
                if (fragment_mypage_setting_memberinfo_et_new_password.text.toString().isNotEmpty())
                    fragment_mypage_setting_memberinfo_tv_newPassword_hint.setVisibility(View.GONE)
                else
                    fragment_mypage_setting_memberinfo_tv_newPassword_hint.setVisibility(View.VISIBLE)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //텍스트의 길이가 변경되었을 경우 발생할 이벤트를 작성.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //텍스트가 변경될때마다 발생할 이벤트를 작성.
            }
        }

        return inflater.inflate(R.layout.fragment_mypage_setting_memberinfo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_mypage_setting_memberinfo_btn_save.setOnClickListener{
            // 비밀번호 확인창을 채우지 않았을 경우
            val newPassword : String = fragment_mypage_setting_memberinfo_et_new_password.text.toString()
            val newPasswordAgain : String = fragment_mypage_setting_memberinfo_et_new_password_check.text.toString()
            if(!newPassword.equals(newPasswordAgain))
                toast("새 비밀번호와 새비밀번호 재입력란이\n일치하지 않습니다.")
            else if(newPassword.isEmpty())
                newPassword == null
        }
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
                        fragment_mypage_setting_memberinfo_et_nickname.setText(arr.nickname)
                        fragment_mypage_setting_memberinfo_et_email.setText(arr.email)
                        fragment_mypage_setting_memberinfo_et_password.setText(arr.password)
                    }
                }
            }
        })
    }
    /*
    private fun putUserInfoEditResponse() {
        if(fragment_mypage_setting_memberinfo_et_nickname.text.toString().isEmpty() ||
                fragment_mypage_setting_memberinfo_et_password.text.toString().isEmpty() ||
                x ||
                fragment_mypage_setting_memberinfo_et_new_password_check.text.toString().isEmpty())
            toast("닉")
        if()
        val nickname: String = fragment_mypage_setting_memberinfo_et_nickname.text.toString()
        val password : String =  fragment_mypage_setting_memberinfo_et_password.text.toString()
        val newPassword : String = fragment_mypage_setting_memberinfo_et_new_password.text.toString()
        val newPasswordAgain : String = fragment_mypage_setting_memberinfo_et_new_password_check.text.toString()
        val getSearchResultResponse = networkService.getSearchResultResponse(
                SharedPreferenceController.getAuthorization(activity!!), query, 20, 0)
        getSearchResultResponse.enqueue(object : Callback<GetNoticeListResponse> {
            override fun onFailure(call: Call<GetNoticeListResponse>, t: Throwable) {
                Log.e("response body fail", t.toString())
            }

            override fun onResponse(call: Call<GetNoticeListResponse>, response: Response<GetNoticeListResponse>) {
                if (response.isSuccessful) {
                }
            }
        })
    }
    */
}