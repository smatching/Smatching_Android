package appjam.sopt.a23rd.smatching

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import appjam.sopt.a23rd.smatching.Adapter.WorkthroughsPagerAdapter
import kotlinx.android.synthetic.main.activity_workthroughs_pager.*

class WorkthroughsPagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workthroughs_pager)

        configureBottomNavigation()
    }
    private fun configureBottomNavigation(){
        val mViewPager = findViewById<ViewPager>(R.id.act_workthroughs_pager_viewpager)
        val mTabLayout = findViewById<TabLayout>(R.id.act_workthroughs_pager_tab)
        val bottomNaviLayout : View = this.layoutInflater.inflate(R.layout.workthroughs_tab, null, false)


        mViewPager.adapter = WorkthroughsPagerAdapter(supportFragmentManager, 3)
        mTabLayout.setupWithViewPager(mViewPager)
        mTabLayout.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.workthroughs_tab_first) as ImageView
        mTabLayout.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.workthroughs_tab_second) as ImageView
        mTabLayout.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.workthroughs_tab_third) as ImageView

        fragment_workthroughs_tv.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val intent = Intent(this@WorkthroughsPagerActivity, StartActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

    }
}