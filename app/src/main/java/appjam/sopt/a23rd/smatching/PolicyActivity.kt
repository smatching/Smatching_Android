package appjam.sopt.a23rd.smatching

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_policy.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_policy)
        val toolbar = act_policy_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)

        var isClick: Boolean = false

        act_policy_iv_all_agree.setOnClickListener {
            if(!isClick) {
                act_policy_iv_next.setImageResource(R.drawable.btn_next_click)
                act_policy_iv_all_agree.setImageResource(R.drawable.icn_checkbox_blue)
                isClick = true
            }
            else {
                act_policy_iv_next.setImageResource(R.drawable.btn_next)
                act_policy_iv_all_agree.setImageResource(R.drawable.icn_emptybox)
                isClick = false
            }

        }
        act_policy_ll_policy_in.setOnClickListener {
            val intent = Intent(this@PolicyActivity, PolicyViewActivity::class.java)
            intent.putExtra("policyData", 0)
            startActivity(intent)
        }
        act_policy_ll_private_in.setOnClickListener {
            val intent = Intent(this@PolicyActivity, PolicyViewActivity::class.java)
            intent.putExtra("policyData", 1)
            startActivity(intent)
        }
        act_policy_iv_next.setOnClickListener{
            if(isClick)
                startActivity<StartCreateActivity>()
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
