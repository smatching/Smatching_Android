package appjam.sopt.a23rd.smatching.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import appjam.sopt.a23rd.smatching.R
import appjam.sopt.a23rd.smatching.StartActivity
import appjam.sopt.a23rd.smatching.WorkthroughsPagerActivity
import kotlinx.android.synthetic.main.activity_workthroughs_pager.*
import kotlinx.android.synthetic.main.fragment_workthroughs_3.*
import org.jetbrains.anko.support.v4.toast

class WorkthroughsFragment_3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_workthroughs_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragment_workthroughs_start.setOnClickListener {
            val intent = Intent(activity, StartActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            (activity as WorkthroughsPagerActivity).findViewById<TextView>(R.id.fragment_workthroughs_tv).visibility = View.INVISIBLE
        }
    }
}