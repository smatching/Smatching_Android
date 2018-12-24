package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import junit.framework.Test

class StartActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        //자동로그인
        /*
        if(SharedPreferenceController.getAuthorization(this).isNotEmpty())
            startActivity<TestActivity>()*/
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        var btnCreateAccount = findViewById<View>(R.id.act_main_iv_create_account)
        var btnLogin = findViewById<View>(R.id.act_main_iv_login)



        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle("")

        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                startActivity<StartLoginActivity>()
            }
        })
        btnCreateAccount.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                startActivity<StartCreateActivity>()
            }
        })
    }
}
