package appjam.sopt.a23rd.smatching.Fragment


import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import appjam.sopt.a23rd.smatching.Adapter.PagerAdapter
import appjam.sopt.a23rd.smatching.Adapter.PagerSmatchingAdapter
import appjam.sopt.a23rd.smatching.R
import com.airbnb.lottie.LottieAnimationView

class SmatchingCustom : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_smatching, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mViewPager = view.findViewById<ViewPager>(R.id.fragment_smatching_vp)
        val mTabLayout = view.findViewById<TabLayout>(R.id.fragment_smatching_tab)
        val bottomNaviLayout : View = activity!!.layoutInflater.inflate(R.layout.home_navigation_tab, null, false)//inflate뷰를 붙여줌

        mViewPager.adapter = PagerSmatchingAdapter(childFragmentManager, 2)
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.home_navigation_tab_first) as ImageView
        mTabLayout.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.home_navigation_tab_second) as ImageView

        val intent = activity!!.getIntent()
        if(intent.getIntExtra("page", 0) == 0) {
            mViewPager.setCurrentItem(0)
            intent.removeExtra("page")
        }  else if(intent.getIntExtra("page", 0) == 1) {
            mViewPager.setCurrentItem(1)
            intent.removeExtra("page")
        }

    }
}