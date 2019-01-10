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
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import appjam.sopt.a23rd.smatching.Delete.DeleteSmatchingCondsResponse
import appjam.sopt.a23rd.smatching.Fragment.*
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_mypage_setting_logout.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.nav_header_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var index: Int = -1
    var pageNum: Int = 0
    var isSearch: Int = 0
    var time: Long = 0
    var backButtomVisibility = 0
    lateinit var fragment: Fragment
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        backButtomVisibility = 0
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

        act_main_dont_do_that

        act_mypage_setting_memberquit_no.setOnClickListener {
            act_main_dont_do_that.visibility = View.GONE
        }
        act_mypage_setting_memberquit_ok.setOnClickListener {
            getDeleteUserInfoResponse()
        }
        act_mypage_setting_logout_ok.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                if (SharedPreferenceController.getAuthorization(this@MainActivity).isNotEmpty()) {
                    SharedPreferenceController.setAuthorization(this@MainActivity, "")
                    toast("로그아웃이 정상적으로 처리 되었습니다.")
                    startActivity<StartActivity>()
                }
            }
        })
        act_mypage_setting_logout_no.setOnClickListener {
            act_main_rl_logout.visibility = View.GONE
        }


        val intent = getIntent()
        if (intent.getIntExtra("view", 0) == 1) {
            replaceFragment(SmatchingCustom())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            pageNum = 1
            act_main_iv_home.isSelected = false
            act_main_iv_smatching.isSelected = true
            act_main_iv_talk.isSelected = false
            act_main_iv_my_page.isSelected = false
            titleText.setText("맞춤지원")
            titleImage.visibility = View.INVISIBLE
            if (intent.getIntExtra("page", 0) == 0) {
                intent.removeExtra("page")
                val fragIntent = intent
                fragIntent.putExtra("page", 0)
            } else if (intent.getIntExtra("page", 0) == 1) {
                intent.removeExtra("page")
                val fragIntent = intent
                fragIntent.putExtra("page", 1)
            }
        }


        //configureBottomNavigation()


        act_main_rl_home.setOnClickListener {

            /*
            findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
            findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()
*/
            act_main_rl_home.isEnabled = false
            act_main_rl_smatching.isEnabled = false
            act_main_rl_talk.isEnabled = false
            act_main_rl_my_page.isEnabled = false

            replaceFragment(HomeFragment())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
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
            Handler().postDelayed({
                //findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
                act_main_rl_home.isEnabled = true
                act_main_rl_smatching.isEnabled = true
                act_main_rl_talk.isEnabled = true
                act_main_rl_my_page.isEnabled = true
            }, 1000)
        }
        act_main_rl_smatching.setOnClickListener {
            //
            /*findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
            findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()*/
            //
            act_main_rl_home.isEnabled = false
            act_main_rl_smatching.isEnabled = false
            act_main_rl_talk.isEnabled = false
            act_main_rl_my_page.isEnabled = false
            replaceFragment(SmatchingCustom())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
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
            Handler().postDelayed({
                //findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
                act_main_rl_home.isEnabled = true
                act_main_rl_smatching.isEnabled = true
                act_main_rl_talk.isEnabled = true
                act_main_rl_my_page.isEnabled = true
            }, 1000)

        }
        act_main_rl_talk.setOnClickListener {

            act_main_rl_home.isEnabled = false
            act_main_rl_smatching.isEnabled = false
            act_main_rl_talk.isEnabled = false
            act_main_rl_my_page.isEnabled = false
            replaceFragment(TalkFragment())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBackground))
            toolbar.setTitleTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
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

            act_main_rl_home.isEnabled = true
            act_main_rl_smatching.isEnabled = true
            act_main_rl_talk.isEnabled = true
            act_main_rl_my_page.isEnabled = true
        }
        act_main_rl_my_page.setOnClickListener {
            //
            /*findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.VISIBLE
            findViewById<LottieAnimationView>(R.id.act_main_anim).playAnimation()*/
            //
            act_main_rl_home.isEnabled = false
            act_main_rl_smatching.isEnabled = false
            act_main_rl_talk.isEnabled = false
            act_main_rl_my_page.isEnabled = false
            replaceFragment(MyPageFragment())
            toolbar.setBackgroundColor(resources.getColor(R.color.colorBlue))
            titleText.setTextColor(Color.WHITE)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = true
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search_white).isVisible = false
            pageNum = 3
            act_main_iv_home.isSelected = false
            act_main_iv_smatching.isSelected = false
            act_main_iv_talk.isSelected = false
            act_main_iv_my_page.isSelected = true

            titleText.setText("마이페이지")
            titleImage.visibility = View.INVISIBLE
            Handler().postDelayed({
                //findViewById<RelativeLayout>(R.id.act_main_loading).visibility = View.INVISIBLE
                act_main_rl_home.isEnabled = true
                act_main_rl_smatching.isEnabled = true
                act_main_rl_talk.isEnabled = true
                act_main_rl_my_page.isEnabled = true
            }, 1000)
        }

        //configureBottomNavigation()

    }

    @SuppressLint("ResourceAsColor")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        when (item.getItemId()) {
            android.R.id.home -> {
                if (isSearch == 1) {
                    val mEditText = findViewById<TextView>(R.id.fragment_search_et_search)
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(mEditText.windowToken, 0)
                    isSearch = 0
                }
                replaceFragmentNum(pageNum)
                return true
            }
            R.id.menu_search -> {
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
                backButtomVisibility = 1
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)

                act_bottom_navi_iv_title.visibility = View.INVISIBLE
                return true
            }
            R.id.menu_search_white -> {
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
                findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBackground))
                findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
                supportActionBar!!.setDisplayHomeAsUpEnabled(true)
                backButtomVisibility = 1
                supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)

                act_bottom_navi_iv_title.visibility = View.INVISIBLE
                return true
            }
            R.id.menu_setting_white -> {
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
                backButtomVisibility = 1
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
    private fun addFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()
    }

    private fun replaceFragmentNum(int: Int) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (int == 0) {
            fragment = HomeFragment()
            act_bottom_navi_tv_title.setText("")
            act_bottom_navi_iv_title.visibility = View.VISIBLE
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBackground))
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
        } else if (int == 1) {
            fragment = SmatchingCustom()
            act_bottom_navi_tv_title.setText("맞춤지원")
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBackground))
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0

        } else if (int == 2) {
            fragment = TalkFragment()
            act_bottom_navi_tv_title.setText("창업토크")
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBackground))
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(resources.getColor(R.color.colorText))
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
        } else if (int == 3) {
            fragment = MyPageFragment()
            act_bottom_navi_tv_title.setText("마이페이지")
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice_white).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_setting_white).isVisible = true
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search_white).isVisible = false
            //findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_notice).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_search).isVisible = false
            findViewById<Toolbar>(R.id.my_toolbar).menu.findItem(R.id.menu_smatching_delete).isVisible = false
            findViewById<TextView>(R.id.act_bottom_navi_tv_title).setTextColor(Color.WHITE)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            backButtomVisibility = 0
            findViewById<Toolbar>(R.id.my_toolbar).setBackgroundColor(resources.getColor(R.color.colorBlue))
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

    override fun onBackPressed() {
        //if (findViewById<ImageView>(R.id.home).visibility == View.VISIBLE)
        //     replaceFragmentNum(pageNum)
        //else {
        if (backButtomVisibility == 1) {
            replaceFragmentNum(pageNum)
        } else if (backButtomVisibility == 0) {
            if (System.currentTimeMillis() - time >= 2000) {
                time = System.currentTimeMillis()
                Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show()
            } else if (System.currentTimeMillis() - time < 2000) {
                finish()
                finishAffinity()
                //   }
            }
        }
    }

    public fun setpageNum(num: Int) {
        pageNum = num
    }

    public fun replaceFragment(fragment: Fragment, index: Int) {
        this.index = index
        val transaction: FragmentTransaction = supportFragmentManager!!.beginTransaction()
        transaction.replace(R.id.act_bottom_navi_fl, fragment)
        transaction.commit()

    }

    private fun getDeleteUserInfoResponse() {
        val getDeleteUserInfoResponse = networkService.deleteUserInfoResponse(
                SharedPreferenceController.getAuthorization(this@MainActivity))
        getDeleteUserInfoResponse.enqueue(object : Callback<DeleteSmatchingCondsResponse> {
            override fun onFailure(call: Call<DeleteSmatchingCondsResponse>, t: Throwable) {
                Log.e("Member Quit fail", t.toString())
            }

            override fun onResponse(call: Call<DeleteSmatchingCondsResponse>, response: Response<DeleteSmatchingCondsResponse>) {
                if (response.isSuccessful) {
                    SharedPreferenceController.setAuthorization(this@MainActivity, "")
                    startActivity<StartActivity>()
                    toast("회원 탈퇴 되었습니다.\n스메칭을 이용해 주셔서 감사합니다:)")
                }
            }
        })
    }


}
