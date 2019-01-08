package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import appjam.sopt.a23rd.smatching.Data.UserInfoData
import appjam.sopt.a23rd.smatching.Get.GetNoticeListResponse
import appjam.sopt.a23rd.smatching.Get.GetUserInfoDataResponse
import appjam.sopt.a23rd.smatching.Put.PutSmatchingEdit
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.R.id.*
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_mypage_setting_memberinfo.*
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageSettingMemberInfoFragment : Fragment() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getUserInfo()
        return inflater.inflate(R.layout.fragment_mypage_setting_memberinfo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_mypage_setting_memberinfo_btn_save.setOnClickListener{
            val newPassword : String = fragment_mypage_setting_memberinfo_et_new_password.text.toString()
            val newPasswordAgain : String = fragment_mypage_setting_memberinfo_et_new_password_check.text.toString()
            if(!newPassword.equals(newPasswordAgain))
                toast("새 비밀번호와 새비밀번호 재입력란이\n일치하지 않습니다.")
            else
                putUserInfoEditResponse()
        }

        fragment_mypage_setting_memberinfo_et_new_password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (fragment_mypage_setting_memberinfo_et_new_password.text.toString().isEmpty())
                    fragment_mypage_setting_memberinfo_tv_newPassword_hint.setVisibility(View.VISIBLE)
                else
                    fragment_mypage_setting_memberinfo_tv_newPassword_hint.setVisibility(View.GONE)
            }
        })
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

    private fun putUserInfoEditResponse() {
        val nickname : String = fragment_mypage_setting_memberinfo_et_nickname.text.toString()
        val password : String =  fragment_mypage_setting_memberinfo_et_password.text.toString()
        val newPassword : String = fragment_mypage_setting_memberinfo_et_new_password.text.toString()

        var jsonObject = JsonObject()
        jsonObject.addProperty("nickname", nickname)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("newPassword", newPassword)

        val putUserInfoEditResponse: Call <PutSmatchingEdit> =
                networkService.putUserInfoEditResponse("application/json", SharedPreferenceController.getAuthorization(activity!!), jsonObject)

        putUserInfoEditResponse.enqueue(object : Callback < PutSmatchingEdit > {
            override fun onFailure(call: Call<PutSmatchingEdit>, t: Throwable) {
                Log.e("Sign Up Fail", t.toString())
            }

            override fun onResponse(call: Call<PutSmatchingEdit>, response: Response<PutSmatchingEdit>) {
                if (response.isSuccessful) {
                    if(response.body()!!.status == 403)
                        toast("입력하신 패스워드가 틀렸습니다.")
                    else if(response.body()!!.status == 204){
                        toast("회원 정보가 수정되었습니다.")
                        replaceFragment(MyPageSettingFragment())
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