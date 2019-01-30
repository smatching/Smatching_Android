package appjam.sopt.a23rd.smatching.Class

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent


class CustomViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var mEnabled: Boolean = false

    init {
        this.mEnabled = false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.mEnabled) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.mEnabled) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    fun setPagingEnabled(enabled: Boolean) {
        this.mEnabled = enabled
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        super.setCurrentItem(item, false)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false)
    }
}