package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_my_page_user.*

class MyPageFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_my_page_user, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_my_page_user_smatchingscrap.setOnClickListener {
            fragment_my_page_user_smatchingscrap.setTextColor(resources.getColor(R.color.colorText))
            fragment_my_page_user_talkscrap.setTextColor(resources.getColor(R.color.colorTextshallow))
        }
        fragment_my_page_user_talkscrap.setOnClickListener {
            fragment_my_page_user_talkscrap.setTextColor(resources.getColor(R.color.colorText))
            fragment_my_page_user_smatchingscrap.setTextColor(resources.getColor(R.color.colorTextshallow))
        }
    }
}