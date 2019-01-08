package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.Test2Activity
import appjam.sopt.a23rd.smatching.TestActivity
import kotlinx.android.synthetic.main.fragment_first_custom_empty.*
import kotlinx.android.synthetic.main.fragment_second_custom_empty.*
import org.jetbrains.anko.support.v4.startActivity

class FirstCustomEmptyFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_first_custom_empty, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        frag_first_custom_empty_rl.setOnClickListener {
            startActivity<TestActivity>() //테슷흐
        }
    }
}