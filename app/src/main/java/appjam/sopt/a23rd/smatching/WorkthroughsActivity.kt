package appjam.sopt.a23rd.smatching

import android.app.FragmentTransaction
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import appjam.sopt.a23rd.smatching.Fragment.WorkthroughsFragment_1
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.activity_mypage_setting_logout.*
import kotlinx.android.synthetic.main.activity_workthroughs.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class WorkthroughsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workthroughs)

        // 초기 사용자에게만 보여지는 화면으로 설정
//        if(SharedPreferenceController.getWorkthroughs(this).equals("true"))
//            startActivity<StartActivity>()

        act_workthroughs.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(this@WorkthroughsActivity, WorkthroughsPagerActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        act_workthroughs_tv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(this@WorkthroughsActivity, StartActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        SharedPreferenceController.setWorkthroughs(this@WorkthroughsActivity, "true")
    }
}
