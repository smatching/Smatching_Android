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

class StartActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        //자동로그인
        if(SharedPreferenceController.getAuthorization(this).isNotEmpty())
            startActivity<TestActivity>()
        //else if(SharedPreferenceController.getAuthorization(this).isEmpty())
        //    toast("null")

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        var btnCreateAccount = findViewById<View>(R.id.act_main_iv_create_account)
        var btnGuest = findViewById<View>(R.id.act_main_iv_experience)
        var btnLogin = findViewById<View>(R.id.act_start_login_btn_login)



        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle("")

        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                startActivity<StartLoginActivity>()
            }
        })
        btnGuest.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                toast("테스트")
            }
        })
        btnCreateAccount.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                startActivity<PolicyActivity>()
            }
        })
    }
}
