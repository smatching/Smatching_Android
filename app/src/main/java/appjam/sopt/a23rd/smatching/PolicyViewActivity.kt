package appjam.sopt.a23rd.smatching

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_policy_view.*
import org.jetbrains.anko.toast

class PolicyViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy_view)
        val toolbar = act_policy_view_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)

        val intent: Intent = getIntent()
        if (intent.getIntExtra("policyData", 1) == 0) {
            supportActionBar!!.setTitle("이용약관")
            act_policy_view_tv_text.visibility = View.VISIBLE
            act_policy_view_iv_text.visibility = View.GONE
        } else {
            supportActionBar!!.setTitle("개인정보 처리방침")
            act_policy_view_tv_text.visibility = View.GONE
            act_policy_view_iv_text.visibility = View.VISIBLE
            act_policy_view_iv_text.setImageResource(R.drawable.txt_personaldeatail)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}