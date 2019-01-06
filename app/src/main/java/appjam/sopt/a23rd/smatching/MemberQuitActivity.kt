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

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        var btnNo = findViewById<View>(R.id.act_mypage_setting_memberquit_no)
        var btnOk = findViewById<View>(R.id.act_mypage_setting_memberquit_ok)



        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle("")

        btnNo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish()
            }
        })
        btnOk.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                getDeleteUserInfoResponse()
                finish()
            }
        })
    }
    private fun getDeleteUserInfoResponse() {
        val getDeleteUserInfoResponse = networkService.deleteUserInfoResponse(
                SharedPreferenceController.getAuthorization(this@MemberQuitActivity!!))
        getDeleteUserInfoResponse.enqueue(object : Callback<DeleteSmatchingCondsResponse>{
            override fun onFailure(call: Call<DeleteSmatchingCondsResponse>, t : Throwable){
                Log.e("Member Quit fail", t.toString())
            }
            override fun onResponse(call: Call<DeleteSmatchingCondsResponse>, response: Response<DeleteSmatchingCondsResponse>){
                if (response.isSuccessful) {
                    SharedPreferenceController.setAuthorization(this@MemberQuitActivity, "")
                    toast("회원 탈퇴 되었습니다.\n스메칭을 이용해 주셔서 감사합니다:)")
                    }
                }
            })
        }
    }

