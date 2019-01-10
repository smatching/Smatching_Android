package appjam.sopt.a23rd.smatching


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import appjam.sopt.a23rd.smatching.db.SharedPreferenceController
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import appjam.sopt.a23rd.smatching.post.PostLogInResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_start_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartLoginActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_login)
        setOnBtnClickListener()

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        val email = findViewById<EditText>(R.id.act_start_login_et_email)
        val password = findViewById<EditText>(R.id.act_start_login_et_password)
        val password_hint = findViewById<TextView>(R.id.act_start_login_tv_password_hint)

        var completeNickEmail = false
        var completePassword = false

        //툴바 부분
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar!!.setTitle("")
        //


        email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?)
            {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                //여기서 받기
                if (email.text.toString().length == 0)
                {
                    act_start_login_iv_email_delete.setVisibility(View.INVISIBLE)
                    completeNickEmail = false
                    if (completeNickEmail && completePassword)
                        act_start_login_canclick.setImageResource(R.drawable.btn_login_canclick)
                    else
                        act_start_login_canclick.setImageResource(R.drawable.btn_login)
                }
                else if (email.getText().toString().length != 0)
                {
                    act_start_login_iv_email_delete.setVisibility(View.VISIBLE)
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())
                    {
                        act_start_login_iv_email.setImageResource(R.drawable.et_email_error)
                        completeNickEmail = false
                        if (completeNickEmail && completePassword)
                            act_start_login_canclick.setImageResource(R.drawable.btn_login_canclick)
                        else
                            act_start_login_canclick.setImageResource(R.drawable.btn_login)
                    }
                    else if (android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())
                    {
                        act_start_login_iv_email.setImageResource(R.drawable.et_email_click)
                        completeNickEmail = true
                        if (completeNickEmail && completePassword)
                            act_start_login_canclick.setImageResource(R.drawable.btn_login_canclick)
                        else
                            act_start_login_canclick.setImageResource(R.drawable.btn_login)
                    }
                }
            }
        })
        email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean)
            {
                if (hasFocus && email.getText().toString().length == 0)
                {
                    act_start_login_iv_email.setImageResource(R.drawable.et_email_click)
                }
                else if (!hasFocus && email.text.toString().length == 0)
                    act_start_login_iv_email.setImageResource(R.drawable.et_email)
            }
        })
        password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?)
            {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
            {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
            {
                //여기서 받기
                if (password.getText().toString().length != 0)
                {
                    act_start_login_iv_password_delete.setVisibility(View.VISIBLE)
                    password_hint.visibility = View.INVISIBLE
                    if (password.getText().toString().length >= 8)
                    {
                        act_start_login_iv_password.setImageResource(R.drawable.et_password_click)
                        completePassword = true
                        if (completeNickEmail && completePassword)
                            act_start_login_canclick.setImageResource(R.drawable.btn_login_canclick)
                        else
                            act_start_login_canclick.setImageResource(R.drawable.btn_login)
                    }
                    else
                    {
                        act_start_login_iv_password.setImageResource(R.drawable.et_password_error)
                        completePassword = false
                        if (completeNickEmail && completePassword)
                            act_start_login_canclick.setImageResource(R.drawable.btn_login_canclick)
                        else
                            act_start_login_canclick.setImageResource(R.drawable.btn_login)
                    }
                }
                else if (password.text.toString().length == 0)
                {
                    act_start_login_iv_password_delete.setVisibility(View.INVISIBLE)
                    password_hint.visibility = View.VISIBLE
                    completePassword = false
                    if (completeNickEmail && completePassword)
                        act_start_login_canclick.setImageResource(R.drawable.btn_login_canclick)
                    else
                        act_start_login_canclick.setImageResource(R.drawable.btn_login)
                }
            }
        })
        password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean)
            {
                if (hasFocus && password.getText().toString().length == 0)
                    act_start_login_iv_password.setImageResource(R.drawable.et_password_click)
                else if (!hasFocus && password.getText().toString().length == 0)
                    act_start_login_iv_password.setImageResource(R.drawable.et_password)
            }
        })
    }
    private fun setOnBtnClickListener(){
        act_start_login_canclick.setOnClickListener{
            getLoginResponse()
        }
        act_start_login_iv_email_delete.setOnClickListener{
            act_start_login_et_email.setText("")
            act_start_login_iv_email.setImageResource(R.drawable.et_email)
            act_start_login_iv_email_delete.setVisibility(View.INVISIBLE)
        }
        act_start_login_iv_password_delete.setOnClickListener{
            act_start_login_et_password.setText("")
            act_start_login_iv_password.setImageResource(R.drawable.et_password)
            act_start_login_iv_password_delete.setVisibility(View.INVISIBLE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun getLoginResponse(){
        if (act_start_login_et_email.text.toString().isNotEmpty() && act_start_login_et_password.text.toString().isNotEmpty() ){
            val input_email = act_start_login_et_email.text.toString()
            val input_pw = act_start_login_et_password.text.toString()
            val jsonObject : JSONObject = JSONObject()
            jsonObject.put("email", input_email)
            jsonObject.put("password", input_pw)
            val gsonObject : JsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val postLogInResponse = networkService.postLoginResponse("application/json", gsonObject)
            postLogInResponse.enqueue(object : Callback<PostLogInResponse> {
                override fun onFailure(call: Call<PostLogInResponse>, t: Throwable) {
                    Log.e("Login fail", t.toString())
                }

                override fun onResponse(call: Call<PostLogInResponse>, response: Response<PostLogInResponse>) {
                    if (response.isSuccessful) {
                        Log.d("login data : status", response.body()!!.status.toString())
                        Log.d("login data : message", response.body()!!.message)
                        if (response.body()!!.status == 400) {
                            toast("로그인 실패!\n이메일과 비밀번호를 다시 한번 확인해주세요!")
                        }
                        else if (response.body()!!.status == 200) {
                            val token = response.body()!!.data.token
                            //저번 시간에 배웠던 SharedPreference에 토큰을 저장! 왜냐하면 토큰이 필요한 통신에 사용하기 위해서!!
                            SharedPreferenceController.setAuthorization(this@StartLoginActivity, token)
                            //toast(SharedPreferenceController.getNickname(this@StartLoginActivity))
                            //toast(SharedPreferenceController.getUserNickName(this@StartLoginActivity))
                            startActivity<MainActivity>()
                            finish()
                        }
                    }
                }
            })
        }
    }
}
