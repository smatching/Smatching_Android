package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.WorkthroughsPagerActivity
import kotlinx.android.synthetic.main.fragment_my_page_user.*

class WorkthroughsFragment_2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workthroughs_2, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            (activity as WorkthroughsPagerActivity).findViewById<TextView>(R.id.fragment_workthroughs_tv).visibility = View.VISIBLE
        }
    }
}
