package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import org.jetbrains.anko.toast
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import appjam.sopt.a23rd.smatching.Adapter.MyFragmentStatePagerAdapter
import appjam.sopt.a23rd.smatching.Fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var pageCount: Int = 0
    var pageNum: Int = 0
    lateinit var fragment: Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setNavigationItemSelectedListener(this)

        //툴바 부분
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        val titleText = findViewById<TextView>(R.id.act_bottom_navi_tv_title)
        val titleImage = findViewById<ImageView>(R.id.act_bottom_navi_iv_title)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setTitle("")
        titleText.setText("")
        /*
        val toggle = EndDrawerToggle(
                this, drawer_layout, my_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()*/

        addFragment(HomeFragment())
        act_main_iv_home.isSelected = true
        act_main_iv_smatching.isSelected = false
        act_main_iv_talk.isSelected = false
        act_main_iv_my_page.isSelected = false

        val navi = ll_bottom_navi_act_main


        //configureBottomNavigation()


        act_main_rl_home.setOnClickListener{
            replaceFragment(HomeFragment())
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            toolbar.menu.findItem(R.id.menu_search).isVisible = true
            pageNum = 0
            act_main_iv_home.isSelected = true
            act_main_iv_smatching.isSelected = false
            act_main_iv_talk.isSelected = false
            act_main_iv_my_page.isSelected = false
            titleText.setText("")
            titleImage.visibility = View.VISIBLE
        }
        act_main_rl_smatching.setOnClickListener{
            replaceFragment(CustomFragment())
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            toolbar.menu.findItem(R.id.menu_search).isVisible = true
            pageNum = 1
            act_main_iv_home.isSelected = false
            act_main_iv_smatching.isSelected = true
            act_main_iv_talk.isSelected = false
            act_main_iv_my_page.isSelected = false
            titleText.setText("맞춤지원")
            titleImage.visibility = View.INVISIBLE
        }
        act_main_rl_talk.setOnClickListener{
            replaceFragment(TalkFragment())
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            toolbar.menu.findItem(R.id.menu_search).isVisible = true
            pageNum = 2
            act_main_iv_home.isSelected = false
            act_main_iv_smatching.isSelected = false
            act_main_iv_talk.isSelected = true
            act_main_iv_my_page.isSelected = false
            titleText.setText("창업토크")
            titleImage.visibility = View.INVISIBLE
        }
        act_main_rl_my_page.setOnClickListener{
            replaceFragment(MyPageFragment())
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            toolbar.menu.findItem(R.id.menu_search).isVisible = false
            pageNum = 3
            act_main_iv_home.isSelected = false
            act_main_iv_smatching.isSelected = false
            act_main_iv_talk.isSelected = false
            act_main_iv_my_page.isSelected = true
            titleText.setText("마이페이지")
            titleImage.visibility = View.INVISIBLE
        }



        //configureBottomNavigation()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                replaceFragmentNum(pageNum)

                return true
            }
            R.id.menu_search -> {
/*
                val fr = SearchFragment()// Fragment Instance 설정
                val fm = supportFragmentManager
                val fragmentTransaction = fm.beginTransaction()// FragmentManager와 FragmentTransaction 얻어옴
                fragmentTransaction.replace(R.id.act_bottom_navi_fl, fr)// 위에서 만든 Fragment Instance를 붙여줌
                fragmentTransaction.commit()// 실행*/
                replaceFragment(SearchFragment())
                findViewById<TextView>(R.id.act_bottom_navi_tv_title).setText("검색")
                findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)

                act_bottom_navi_iv_title.visibility = View.INVISIBLE
                return true
            }
            R.id.menu_notice -> {
                if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
                    drawer_layout.closeDrawer(GravityCompat.END)
                } else {
                    drawer_layout.openDrawer(GravityCompat.END)
                }
                nav_header_main_iv_delete.setOnClickListener {
                    drawer_layout.closeDrawer(GravityCompat.END)
                }
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
    private fun addFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment : Fragment) {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
    private fun replaceFragmentNum(int : Int) {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        if(int == 0) {
            fragment = HomeFragment()
            act_bottom_navi_tv_title.setText("")
            act_bottom_navi_iv_title.visibility = View.VISIBLE
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        else if(int == 1) {
            fragment = CustomFragment()
            act_bottom_navi_tv_title.setText("맞춤지원")
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        else if(int == 2) {
            fragment = TalkFragment()
            act_bottom_navi_tv_title.setText("창업토크")
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        else if(int == 3) {
            fragment = MyPageFragment()
            act_bottom_navi_tv_title.setText("마이페이지")
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.a -> {
                // Handle the camera action
            }
            R.id.b -> {

            }
            R.id.c -> {

            }
            R.id.d -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


}
