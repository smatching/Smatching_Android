package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import appjam.sopt.a23rd.smatching.Adapter.FragmentStatePagerAdapter
import appjam.sopt.a23rd.smatching.Fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_bottom_navi.*
import org.jetbrains.anko.toast

class BottomNaviActivity : AppCompatActivity() {
    val fr = SearchFragment()// Fragment Instance 설정
    val fm = supportFragmentManager
    val fragmentTransaction = fm.beginTransaction()// FragmentManager와 FragmentTransaction 얻어옴
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_navi)

        //툴바 부분
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        val titleText = findViewById<TextView>(R.id.act_bottom_navi_tv_title)
        val titleImage = findViewById<TextView>(R.id.act_bottom_navi_tv_title)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setTitle("")
        titleText.setText("홈")

        val pager = findViewById<ViewPager>(R.id.vp_bottom_navi_act_frag_pager)
        pager.adapter = FragmentStatePagerAdapter(supportFragmentManager, 4)


        tl_bottom_navi_act_bottom_menu.setupWithViewPager(pager)

        val bottomNaviLayout  : View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)//inflate뷰를 붙여줌



        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageSelected(position: Int) {
                when(position) {
                    0 -> {
                        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                        toolbar.menu.findItem(R.id.menu_search).isVisible = true
                        titleText.setText("홈")
                    }
                    1 -> {
                        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                        toolbar.menu.findItem(R.id.menu_search).isVisible = true
                        titleText.setText("맞춤지원")
                    }
                    2 -> {
                        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                        toolbar.menu.findItem(R.id.menu_search).isVisible = true
                        titleText.setText("창업토크")
                    }
                    3 -> {
                        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                        toolbar.menu.findItem(R.id.menu_search).isVisible = false
                        titleText.setText("마이페이지")
                        //val item = findViewById<View>(R.id.menu_search)
                    }
                }
            }
        })
0
        tl_bottom_navi_act_bottom_menu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_home) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_smatching) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_talk) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_my_page) as RelativeLayout

        //configureBottomNavigation()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.menu_search -> {
/*
                val fr = SearchFragment()// Fragment Instance 설정
                val fm = supportFragmentManager
                val fragmentTransaction = fm.beginTransaction()// FragmentManager와 FragmentTransaction 얻어옴
                fragmentTransaction.replace(R.id.act_bottom_navi_fl, fr)// 위에서 만든 Fragment Instance를 붙여줌
                fragmentTransaction.commit()// 실행*/
                fragmentTransaction.replace(R.id.act_bottom_navi_fl, fr)// 위에서 만든 Fragment Instance를 붙여줌
                fragmentTransaction.commit()// 실행
                findViewById<TextView>(R.id.act_bottom_navi_tv_title).setText("검색")
                findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
                return true
            }
            R.id.menu_notice -> {
                toast("notice버튼")
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //return super.onCreateOptionsMenu(menu);
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    /*
    private fun configureBottomNavigation(){
        vp_bottom_navi_act_frag_pager.adapter = FragmentStatePagerAdapter(supportFragmentManager, 4)

        tl_bottom_navi_act_bottom_menu.setupWithViewPager(vp_bottom_navi_act_frag_pager)

        val bottomNaviLayout : View = this.layoutInflater.inflate(R.layout.bottom_navigation_tab, null, false)//inflate뷰를 붙여줌

        tl_bottom_navi_act_bottom_menu.getTabAt(0)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_home) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(1)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_smatching) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(2)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_talk) as RelativeLayout
        tl_bottom_navi_act_bottom_menu.getTabAt(3)!!.customView = bottomNaviLayout.findViewById(R.id.bottom_navi_btn_tab_my_page) as RelativeLayout
    }*/
}