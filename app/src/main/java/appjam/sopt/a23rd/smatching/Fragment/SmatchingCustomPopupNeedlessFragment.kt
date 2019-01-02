package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_smatching_custom_popup_needless.*

class SmatchingCustomPopupNeedlessFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_smatching_custom_popup_needless, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fragment_smatching_custom_popup_needless_rl_exit.setOnClickListener{
        }
    }
}