package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.LogoutActivity
import appjam.sopt.a23rd.smatching.MemberQuitActivity
import appjam.sopt.a23rd.smatching.PolicyViewActivity
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_my_page_user.*
import kotlinx.android.synthetic.main.fragment_mypage_setting.*

class MyPageSettingFragment : Fragment(){
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_my_page_user, container, false)
}
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    fragment_mypage_setting_rl_memberinfo_setting.setOnClickListener {
        replaceFragment(MyPageSettingMemberInfoFragment())
    }
    fragment_mypage_setting_rl_ask.setOnClickListener {
        replaceFragment(AskFragment())
    }
    fragment_mypage_setting_rl_policyData.setOnClickListener{
        val intent = Intent(activity, PolicyViewActivity::class.java)
        intent.putExtra("policyData", 0)
        startActivity(intent)
    }
    fragment_mypage_setting_rl_policyPrivate.setOnClickListener{
        val intent = Intent(activity, PolicyViewActivity::class.java)
        intent.putExtra("policyData", 1)
        startActivity(intent)
    }
    fragment_mypage_setting_rl_memberquit.setOnClickListener {
        val intent = Intent(activity, MemberQuitActivity::class.java)
        startActivity(intent)
    }
    fragment_mypage_setting_rl_logout.setOnClickListener {
        val intent = Intent(activity, LogoutActivity::class.java)
        startActivity(intent)
    }
}
    private fun replaceFragment(fragment: Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
}
