package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LogoutActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage_setting_logout)

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        var btnNo = findViewById<View>(R.id.act_mypage_setting_logout_no)
        var btnOk = findViewById<View>(R.id.act_mypage_setting_logout_ok)

        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle("")

        btnNo.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                finish()
            }
        })
        btnOk.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                SharedPreferenceController.setAuthorization(this@LogoutActivity, "")
                toast("로그아웃이 정상적으로 처리 되었습니다.")
                finish()
            }
        })
    }
}
