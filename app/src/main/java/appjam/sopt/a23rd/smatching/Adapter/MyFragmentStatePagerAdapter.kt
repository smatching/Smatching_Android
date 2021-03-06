package appjam.sopt.a23rd.smatching.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import appjam.sopt.a23rd.smatching.Fragment.*
import appjam.sopt.a23rd.smatching.R

class MyFragmentStatePagerAdapter(fm: FragmentManager, var fragmentCount : Int): FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment? {
            when(position) {//자바에서의 switch
                0 -> return FirstFragment()
                else -> return null
            }
    }

    override fun getCount(): Int = fragmentCount //리턴시켜준다고 생각

}