package appjam.sopt.a23rd.smatching.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import appjam.sopt.a23rd.smatching.Fragment.MyPageSmatchingScrapFragment
import appjam.sopt.a23rd.smatching.Fragment.MyPageTalkFragment


class MyPageAdapter(fm: FragmentManager, var fragmentCount : Int): FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when (position) {//자바에서의 switch
            0 -> return MyPageSmatchingScrapFragment()
            1 -> return MyPageTalkFragment()
            else -> return null
        }
    }
    override fun getCount(): Int = fragmentCount //리턴시켜준다고 생각
}