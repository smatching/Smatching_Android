package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toolbar
import appjam.sopt.a23rd.smatching.*
import appjam.sopt.a23rd.smatching.Delete.DeleteSmatchingCondsResponse
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_my_page_user.*
import kotlinx.android.synthetic.main.fragment_mypage_setting.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageSettingFragment : Fragment(){
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mypage_setting, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        frag_my_page_setting_tv_version.text = BuildConfig.VERSION_NAME


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
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_dont_do_that).visibility = View.VISIBLE
        }
        fragment_mypage_setting_rl_logout.setOnClickListener {
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_rl_logout).visibility = View.VISIBLE


        }
        /*
        act_mypage_setting_memberquit_no.setOnClickListener{
            (activity as AppCompatActivity).findViewById<RelativeLayout>(R.id.act_main_dont_do_that).visibility = View.GONE
        }
        act_mypage_setting_memberquit_ok.setOnClickListener{
            getDeleteUserInfoResponse()
        }*/
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }

}
