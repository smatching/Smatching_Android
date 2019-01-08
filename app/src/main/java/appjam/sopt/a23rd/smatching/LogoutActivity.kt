package appjam.sopt.a23rd.smatching

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.activity_mypage_setting_logout.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LogoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_setting_logout)

        act_mypage_setting_logout_no.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish()
            }
        })
        act_mypage_setting_logout_ok.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if(SharedPreferenceController.getAuthorization(this@LogoutActivity).isNotEmpty()) {
                    SharedPreferenceController.setAuthorization(this@LogoutActivity, "")
                    toast("로그아웃이 정상적으로 처리 되었습니다.")
                    startActivity<StartActivity>()
                }
            }
        })
    }
}
