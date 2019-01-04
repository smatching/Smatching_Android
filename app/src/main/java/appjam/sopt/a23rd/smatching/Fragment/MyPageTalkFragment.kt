package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_my_page_user.*

class MyPageTalkFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_page_user_talk, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_my_page_user_rl_profile.setOnClickListener{
            replaceFragment(MyPageSettingMemberInfoFragment())
        }
        fragment_my_page_user_talkscrap.setOnClickListener {
            replaceFragment(MyPageTalkFragment())
        }
        fragment_my_page_user_smatchingscrap.setOnClickListener{
            replaceFragment(MyPageFragment())
        }
    }

        private fun replaceFragment(fragment: Fragment) {
            val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.act_bottom_navi_fl, fragment)
            transaction.commit()
        }
}