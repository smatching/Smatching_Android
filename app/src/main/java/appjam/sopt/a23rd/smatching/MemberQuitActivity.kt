package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import appjam.sopt.a23rd.smatching.Delete.DeleteSmatchingCondsResponse
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.activity_mypage_setting_memberquit.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberQuitActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_setting_memberquit)

        act_mypage_setting_memberquit_no.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish()
            }
        })
        act_mypage_setting_memberquit_ok.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                getDeleteUserInfoResponse()
                finish()
            }
        })
    }

    private fun getDeleteUserInfoResponse() {
        val getDeleteUserInfoResponse = networkService.deleteUserInfoResponse(
                SharedPreferenceController.getAuthorization(this@MemberQuitActivity!!))
        getDeleteUserInfoResponse.enqueue(object : Callback<DeleteSmatchingCondsResponse> {
            override fun onFailure(call: Call<DeleteSmatchingCondsResponse>, t: Throwable) {
                Log.e("Member Quit fail", t.toString())
            }

            override fun onResponse(call: Call<DeleteSmatchingCondsResponse>, response: Response<DeleteSmatchingCondsResponse>) {
                if (response.isSuccessful) {
                    SharedPreferenceController.setAuthorization(this@MemberQuitActivity, "")
                    startActivity<StartActivity>()
                    toast("회원 탈퇴 되었습니다.\n스메칭을 이용해 주셔서 감사합니다:)")
                }
            }
        })
    }
}

