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
import appjam.sopt.a23rd.smatching.R.id.act_start_login_iv_email
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService
import appjam.sopt.a23rd.smatching.post.PostSignUpResponse
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_start_create.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartCreateActivity : AppCompatActivity() {

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    var completeNickName = false
    var completeNickEmail = false
    var completePassword = false
    var completePassword_confirm = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_create)

        setOnBtnClickListenter()

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        val nickName = findViewById<EditText>(R.id.act_start_create_et_nickname)
        val email = findViewById<EditText>(R.id.act_start_create_et_email)
        val password = findViewById<EditText>(R.id.act_start_create_et_password)
        val password_confirm = findViewById<EditText>(R.id.act_start_create_et_passwordagain)
        val password_hint = findViewById<TextView>(R.id.act_start_create_tv_password)
        val passwordagain_hint = findViewById<TextView>(R.id.act_start_create_tv_passwordagain)

        //툴바 부분
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar!!.setTitle("")

        nickName.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //여기서 받기
                if(nickName.getText().toString().length != 0){
                    act_start_create_iv_nickname_delete.setVisibility(View.VISIBLE)
                    act_start_create_iv_nickname.setImageResource(R.drawable.et_nickname_click)
                    completeNickName = true
                    if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                    else
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                } else if(nickName.text.toString().length == 0) {
                    act_start_create_iv_nickname_delete.setVisibility(View.INVISIBLE)
                    completeNickName = false
                    if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                    else
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                }
            }
        })
        nickName.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus && nickName.getText().toString().length == 0) {
                    act_start_create_iv_nickname.setImageResource(R.drawable.et_nickname_click)
                } else if(!hasFocus && nickName.text.toString().length == 0)
                    act_start_create_iv_nickname.setImageResource(R.drawable.et_nickname)
            }
        })
        email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //여기서 받기
                if(email.text.toString().length == 0) {
                    act_start_create_iv_email_delete.setVisibility(View.INVISIBLE)
                    completeNickEmail = false
                    if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                    else
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                } else if(email.getText().toString().length != 0){
                    act_start_create_iv_email_delete.setVisibility(View.VISIBLE)
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                        act_start_create_iv_email.setImageResource(R.drawable.et_email_error)
                        completeNickEmail = false
                        if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                        else
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                    } else if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
                        act_start_create_iv_email.setImageResource(R.drawable.et_email_click)
                        completeNickEmail = true
                        if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                        else
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                    }
                }
            }
        })
        email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus && email.getText().toString().length == 0) {
                    act_start_create_iv_email.setImageResource(R.drawable.et_email_click)
                } else if(!hasFocus && email.text.toString().length == 0)
                    act_start_create_iv_email.setImageResource(R.drawable.et_email)
            }
        })
        password.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //여기서 받기
                if(password.getText().toString().length != 0){
                    act_start_create_iv_password_delete.setVisibility(View.VISIBLE)
                    password_hint.visibility = View.INVISIBLE
                    if(password.getText().toString().length >= 8) {
                        act_start_create_iv_password.setImageResource(R.drawable.et_password_click)
                        completePassword = true
                        if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                        else
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                    } else{
                        act_start_create_iv_password.setImageResource(R.drawable.et_password_error)
                        completePassword = false
                        if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                        else
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                    }
                } else if(password.text.toString().length == 0) {
                    act_start_create_iv_password_delete.setVisibility(View.INVISIBLE)
                    password_hint.visibility = View.VISIBLE
                    completePassword = false
                    if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                    else
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                }
            }
        })
        password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus && password.getText().toString().length == 0)
                    act_start_create_iv_password.setImageResource(R.drawable.et_password_click)
                else if(!hasFocus && password.getText().toString().length == 0)
                    act_start_create_iv_password.setImageResource(R.drawable.et_password)
            }
        })
        password_confirm.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //여기서 받기
                if(password_confirm.getText().toString().length != 0){
                    act_start_create_iv_passwordagain_delete.setVisibility(View.VISIBLE)
                    passwordagain_hint.visibility = View.INVISIBLE
                    if(password_confirm.getText().toString().length >= 8 && password_confirm.text.toString() == password.text.toString()) {
                        act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain_click)
                        completePassword_confirm = true
                        if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                        else
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                    } else{
                        act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain_error)
                        completePassword_confirm = false
                        if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                        else
                            act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                    }
                } else if(password_confirm.text.toString().length == 0) {
                    act_start_create_iv_passwordagain_delete.setVisibility(View.INVISIBLE)
                    passwordagain_hint.visibility = View.VISIBLE
                    completePassword_confirm = false
                    if(completeNickName && completeNickEmail && completePassword && completePassword_confirm)
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)
                    else
                        act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                }
            }
        })
        password_confirm.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus && password_confirm.getText().toString().length == 0)
                    act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain_click)
                else if(!hasFocus && password_confirm.getText().toString().length == 0)
                    act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain)
            }
        })

    }
    private fun setOnBtnClickListenter(){
        act_start_create_iv_createaccount.setOnClickListener {
            getSignUpResponse()
        }
        act_start_create_iv_nickname_delete.setOnClickListener{
            act_start_create_et_nickname.setText("")
            act_start_create_iv_nickname.setImageResource(R.drawable.et_nickname)
            act_start_create_iv_nickname_delete.setVisibility(View.INVISIBLE)
        }
        act_start_create_iv_email_delete.setOnClickListener{
            act_start_create_et_email.setText("")
            act_start_create_iv_email.setImageResource(R.drawable.et_email)
            act_start_create_iv_email_delete.setVisibility(View.INVISIBLE)
        }
        act_start_create_iv_password_delete.setOnClickListener{
            act_start_create_et_password.setText("")
            act_start_create_iv_password.setImageResource(R.drawable.et_password)
            act_start_create_iv_password_delete.setVisibility(View.INVISIBLE)
        }
        act_start_create_iv_passwordagain_delete.setOnClickListener{
            act_start_create_et_passwordagain.setText("")
            act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain)
            act_start_create_iv_passwordagain_delete.setVisibility(View.INVISIBLE)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun getSignUpResponse() {

        //EditText에 있는 값 받기
        if (completeNickName && completeNickEmail && completePassword && completePassword_confirm) {

            val input_nickname: String = act_start_create_et_nickname.text.toString()
            val input_email: String = act_start_create_et_email.text.toString()
            val input_pw: String = act_start_create_et_password.text.toString()
            //Json 형식의 객체 만들기
            var jsonObject = JSONObject()
            jsonObject.put("nickname", input_nickname)
            jsonObject.put("email", input_email)
            jsonObject.put("password", input_pw)

            // jsonObject.put("part", "안드로이드")
            //Gson 라이브러리의 Json Parser을 통해 객체를 Json으로!
            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            //통신 시작
            val postSignUpResponse: Call<PostSignUpResponse> =
                    networkService.postSignUpResponse("application/json", gsonObject)

            postSignUpResponse.enqueue(object : Callback<PostSignUpResponse> {
                override fun onFailure(call: Call<PostSignUpResponse>, t: Throwable) {
                    Log.e("Sign Up Fail", t.toString())
                }

                override fun onResponse(call: Call<PostSignUpResponse>, response: Response<PostSignUpResponse>) {
                    if (response.isSuccessful) {
                        if(response.body()!!.status == 405){
                            toast(response.body()!!.message)
                        }
                       else if(response.body()!!.status == 201){
                            toast("스매칭의 회원이 되신걸 환영합니다:)")
                            startActivity<StartLoginActivity>()
                        }
                    }
                }
            })
        }
    }
}
