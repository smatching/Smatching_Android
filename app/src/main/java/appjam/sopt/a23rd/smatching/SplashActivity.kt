package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.lottie.LottieAnimationView
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.os.Handler
import android.view.Menu


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //val splash : LottieAnimationView = findViewById(R.id.act_splash_anim)

        Handler().postDelayed(Runnable {
            /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/
            val mainIntent = Intent(this@SplashActivity, WorkthroughsActivity::class.java)
            this@SplashActivity.startActivity(mainIntent)
            this@SplashActivity.finish()
        }, 4000)
    }
}
