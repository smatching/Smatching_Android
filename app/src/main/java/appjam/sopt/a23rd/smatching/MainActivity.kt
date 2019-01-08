package appjam.sopt.a23rd.smatching

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import appjam.sopt.a23rd.smatching.Fragment.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.textColor


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener
{
    var pageNum: Int = 0
    var isSearch: Int = 0
    var time: Long = 0
    lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_view.setNavigationItemSelectedListener(this)


        act_main_loading.setOnTouchListener(View.OnTouchListener { v, event -> true })

        //act_main_loading.visibility = View.VISIBLE
        //act_main_anim.playAnimation()

        //툴바 부분
        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        val titleText = findViewById<TextView>(R.id.act_bottom_navi_tv_title)
        val titleImage = findViewById<ImageView>(R.id.act_bottom_navi_iv_title)
        var pageState = 0
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

        val intent = getIntent()
        if (intent.getIntExtra("view", 0) == 1)
        {
            replaceFragment(SmatchingCustom())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            pageNum = 1
            act_main_iv_home.isSelected = false
            act_main_iv_smatching.isSelected = true
            act_main_iv_talk.isSelected = false
            act_main_iv_my_page.isSelected = false
            titleText.setText("맞춤지원")
            titleImage.visibility = View.INVISIBLE
            if (intent.getIntExtra("page", 0) == 0)
            {
                intent.removeExtra("page")
                val fragIntent = intent
                fragIntent.putExtra("page", 0)
            }
            else if (intent.getIntExtra("page", 0) == 1)
            {
                intent.removeExtra("page")
                val fragIntent = intent
                fragIntent.putExtra("page", 1)
            }
        }

        //configureBottomNavigation()


        act_main_rl_home.setOnClickListener{


            //act_main_rl_home.isEnabled = false
            replaceFragment(HomeFragment())
            /*
            Handler().postDelayed(Runnable {
                // TODO
            }, 500)
            act_main_rl_home.isEnabled = true*/


            //replaceFragment(CorporateDetailFragment())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            pageNum = 0
            act_main_iv_home.isSelected = true
            act_main_iv_smatching.isSelected = false
            act_main_iv_talk.isSelected = false
            act_main_iv_my_page.isSelected = false
            titleText.setText("")
            titleImage.visibility = View.VISIBLE

        }
        act_main_rl_smatching.setOnClickListener{
            //replaceFragment(CustomFirstFragment())
            replaceFragment(SmatchingCustom())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
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
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
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
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBlue))
            titleText.setTextColor(Color.WHITE)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = true
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
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
    @SuppressLint("ResourceAsColor")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        when(item.getItemId())
        {
            android.R.id.home-> {
                if (isSearch == 1)
                {
                    val mEditText = findViewById<TextView>(R.id.fragment_search_et_search)
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(mEditText.windowToken, 0)
                    isSearch = 0
                }
                replaceFragmentNum(pageNum)
                return true
            }
            R.id.menu_search-> {
                isSearch = 1
/*
                val fr = SearchFragment()// Fragment Instance 설정
                val fm = supportFragmentManager
                val fragmentTransaction = fm.beginTransaction()// FragmentManager와 FragmentTransaction 얻어옴
                fragmentTransaction.replace(R.id.act_bottom_navi_fl, fr)// 위에서 만든 Fragment Instance를 붙여줌
                fragmentTransaction.commit()// 실행*/
                replaceFragment(SearchFragment())
                findViewById<TextView>(R.id.act_bottom_navi_tv_title).run {
                    setTextColor(R.color.colorText)
                    setText("검색")
                }
                findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)

                act_bottom_navi_iv_title.visibility = View.INVISIBLE
                return true
            }
            R.id.menu_setting_white-> {
              /*  isSearch = 1

                val fr = SearchFragment()// Fragment Instance 설정
                val fm = supportFragmentManager
                val fragmentTransaction = fm.beginTransaction()// FragmentManager와 FragmentTransaction 얻어옴
                fragmentTransaction.replace(R.id.act_bottom_navi_fl, fr)// 위에서 만든 Fragment Instance를 붙여줌
                fragmentTransaction.commit()// 실행*/
                replaceFragment(MyPageSettingFragment())
                findViewById<TextView>(R.id.act_bottom_navi_tv_title).setText("설정")
                findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(R.color.colorText)
                findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
                findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBackground))
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)

                act_bottom_navi_iv_title.visibility = View.INVISIBLE
                return true
            }

            /*R.id.menu_notice-> {
                if (drawer_layout.isDrawerOpen(GravityCompat.END))
                {
                    drawer_layout.closeDrawer(GravityCompat.END)
                }
                else
                {
                    drawer_layout.openDrawer(GravityCompat.END)
                }
                nav_header_main_iv_delete.setOnClickListener {
                    drawer_layout.closeDrawer(GravityCompat.END)
                }
                return true
            }*/
            /*
            R.id.menu_notice_white-> {
                if (drawer_layout.isDrawerOpen(GravityCompat.END))
                {
                    drawer_layout.closeDrawer(GravityCompat.END)
                }
                else
                {
                    drawer_layout.openDrawer(GravityCompat.END)
                }
                nav_header_main_iv_delete.setOnClickListener {
                    drawer_layout.closeDrawer(GravityCompat.END)
                }
                return true
            }*/
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
    private fun addFragment(fragment : Fragment)
    {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment : Fragment)
    {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
    private fun replaceFragmentNum(int : Int)
    {
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        if (int == 0)
        {
            fragment = HomeFragment()
            act_bottom_navi_tv_title.setText("")
            act_bottom_navi_iv_title.visibility = View.VISIBLE
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        else if (int == 1)
        {
            fragment = SmatchingCustom()
            act_bottom_navi_tv_title.setText("맞춤지원")
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        }
        else if (int == 2)
        {
            fragment = TalkFragment()
            act_bottom_navi_tv_title.setText("창업토크")
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        else if (int == 3)
        {
            fragment = MyPageFragment()
            act_bottom_navi_tv_title.setText("마이페이지")
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = true
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(Color.WHITE)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when(item.itemId)
        {
            R.id.a-> {
                // Handle the camera action
            }
            R.id.b-> {

            }
            R.id.c-> {

            }
            R.id.d-> {

            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed()
    {
        //if (findViewById<ImageView>(R.id.home).visibility == View.VISIBLE)
        //     replaceFragmentNum(pageNum)
        //else {
        if (System.currentTimeMillis() - time >= 2000)
        {
            time = System.currentTimeMillis()
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show()
        }
        else if (System.currentTimeMillis() - time < 2000)
        {
            finish()
            finishAffinity()
            //   }
        }
    }
    public fun setpageNum(num:Int)
    {
        pageNum = num
    }
}