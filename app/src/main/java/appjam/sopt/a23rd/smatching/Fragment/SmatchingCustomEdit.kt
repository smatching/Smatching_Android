package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_smatching_custom.*
import android.content.Intent
import android.content.Intent.getIntent
import org.jetbrains.anko.support.v4.toast


class SmatchingCustomEdit : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_smatching_custom, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_smatching_custom_rl_needless.setOnClickListener {
            replaceFragment(SmatchingCustomPopupNeedlessFragment())
        }
    }
    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = fragmentManager!!.beginTransaction()
        transaction.replace(R.id.fragment_smatching_custom_fl_popup, fragment)
        transaction.commit()
    }

}