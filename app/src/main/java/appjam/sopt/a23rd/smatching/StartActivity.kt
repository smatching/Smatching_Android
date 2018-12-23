package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import org.jetbrains.anko.startActivity

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        //var btn_login = findViewById<View>(R.id.act_main_iv_login)
        var btn_login = findViewById<View>(R.id.act_main_iv_login)



        setSupportActionBar(toolbar)

        supportActionBar!!.setTitle("")

        btn_login.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                startActivity<StartLoginActivity>()
            }
        })
    }
}
