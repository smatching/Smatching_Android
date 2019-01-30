package appjam.sopt.a23rd.smatching.Class

import android.os.SystemClock
import android.view.View


abstract class OnSingleClickListener : View.OnClickListener {

    private var mLastClickTime: Long = 0

    abstract fun onSingleClick(v: View)

    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime

        // 중복 클릭인 경우
        if (elapsedTime <= MIN_CLICK_INTERVAL) {
            return
        }

        // 중복 클릭아 아니라면 추상함수 호출
        onSingleClick(v)
    }

    companion object {
        // 중복 클릭 방지 시간 설정
        private val MIN_CLICK_INTERVAL: Long = 600
    }

}