package appjam.sopt.a23rd.smatching.db

import android.content.Context
import android.provider.ContactsContract

object SharedPreferenceController{
    private val USER_NAME = "MYKEY"
    private val myAuth = "myAuth"
    private val workthroughs = "false"

    fun setAuthorization(context: Context, authorization : String){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(myAuth, authorization)
        editor.commit()
    }
    fun getAuthorization(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(myAuth, "")
    }

    fun setWorkthroughs(context: Context, v : String){
        val pref = context.getSharedPreferences(workthroughs, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(workthroughs, v)
        editor.commit()
    }
    fun getWorkthroughs(context: Context) : String {
        val pref = context.getSharedPreferences(workthroughs, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(workthroughs, "false")
    }

    fun clearSPC(context: Context){
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.clear()
        editor.commit()
    }
}