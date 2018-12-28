package appjam.sopt.a23rd.smatching.Fragment

import android.graphics.Paint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.Adapter.HomeFragmentRecyclerViewAdapter
import appjam.sopt.a23rd.smatching.Data.NoticeData
import appjam.sopt.a23rd.smatching.R
import kotlinx.android.synthetic.main.fragment_second.*
import android.widget.TextView


class SecondFragment : Fragment(){

    lateinit var homeFragmentFragmentRecyclerViewAdapter: HomeFragmentRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        var dataList: ArrayList<NoticeData> = ArrayList()
        var noticeCnt: TextView = view!!.findViewById(R.id.fragment_second_tv_cnt)
        noticeCnt.setPaintFlags(noticeCnt.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        homeFragmentFragmentRecyclerViewAdapter = HomeFragmentRecyclerViewAdapter(activity!!, dataList)
        fragment_second_rv.adapter = homeFragmentFragmentRecyclerViewAdapter
        fragment_second_rv.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUserData() {

    }
}