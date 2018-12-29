package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_custom_condition_click.*
import kotlinx.android.synthetic.main.fragment_custom_condition_notclick.*

class CustomConditionNotClickFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_custom_condition_notclick, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_custom_condition_notclick_iv_back.setOnClickListener {
            replaceFragment(CustomConditionClickFragment())
        }
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_custom_fl, fragment)
        transaction.commit()
    }
}