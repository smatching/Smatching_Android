package appjam.sopt.a23rd.smatching.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import appjam.sopt.a23rd.smatching.R

class HomeFragment : Fragment(){
    var mInstace : HomeFragment? = null

    //다른 스레드에서 인스턴스 생성해줘서 하나만 생성됨
    @Synchronized
    fun getInstance(): HomeFragment{
        if(mInstace == null){
            mInstace = HomeFragment()
        }
        return mInstace!!
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}