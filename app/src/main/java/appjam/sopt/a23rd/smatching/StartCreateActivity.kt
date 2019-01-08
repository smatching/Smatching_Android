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

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(edit: Editable) {
                if (nickName.getText().toString().isEmpty() || email.getText().toString().isEmpty()
                        || password.getText().toString().isEmpty() || password_confirm.getText().toString().isEmpty())
                    act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount)
                else
                    act_start_create_iv_createaccount.setImageResource(R.drawable.btn_createaccount_canclick)

                if(!nickName.text.toString().isEmpty())
                    act_start_create_iv_nickname_delete.setVisibility(View.VISIBLE)
                if(!password.text.toString().isEmpty())
                    act_start_create_iv_password_delete.setVisibility(View.VISIBLE)
                if(!password_confirm.text.toString().isEmpty())
                    act_start_create_iv_passwordagain_delete.setVisibility(View.VISIBLE)

            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
        }
        nickName.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus || nickName.getText().toString().length != 0) {
                    act_start_create_iv_nickname.setImageResource(R.drawable.et_nickname_click)
                    if(nickName.getText().toString().length != 0)
                        act_start_create_iv_nickname_delete.setVisibility(View.VISIBLE)
                }
                    else {
                    act_start_create_iv_nickname_delete.setVisibility(View.INVISIBLE)
                    act_start_create_iv_nickname.setImageResource(R.drawable.et_nickname)
                }
            }
        })

        //체킹
        email.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //여기서 받기
                if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
                        && email.getText().toString().length != 0){
                    act_start_create_iv_email_delete.setVisibility(View.VISIBLE)
                    act_start_create_iv_email.setImageResource(R.drawable.et_email_error)
                    email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
                        override fun onFocusChange(v: View, hasFocus: Boolean) {
                            if (!hasFocus)
                                act_start_create_iv_email.setImageResource(R.drawable.et_email_error)
                        }
                    })
                }
                else if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
                        && email.getText().toString().length != 0) {
                    email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
                        override fun onFocusChange(v: View, hasFocus: Boolean) {
                            if (!hasFocus)
                                act_start_create_iv_email.setImageResource(R.drawable.et_email_click)
                            if(!email.text.toString().isEmpty())
                                act_start_create_iv_email_delete.setVisibility(View.VISIBLE)
                        }
                    })

                    act_start_create_iv_email.setImageResource(R.drawable.et_email_click)
                }
                else if(email.text.toString().length == 0) {
                    act_start_create_iv_email_delete.setVisibility(View.INVISIBLE)
                    act_start_create_iv_email.setImageResource(R.drawable.et_email)
                    email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
                        override fun onFocusChange(v: View, hasFocus: Boolean) {
                            if (!hasFocus)
                                act_start_create_iv_email.setImageResource(R.drawable.et_email_click)
                            if(!email.text.toString().isEmpty())
                                act_start_create_iv_email_delete.setVisibility(View.VISIBLE)
                        }
                    })
                }
            }
        })
        email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (hasFocus)
                    act_start_create_iv_email.setImageResource(R.drawable.et_email_click)
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
                        && email.getText().toString().length != 0)
                        act_start_create_iv_email.setImageResource(R.drawable.et_email_error)
                else if(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
                        && email.getText().toString().length != 0)
                    act_start_create_iv_email.setImageResource(R.drawable.et_email_click)
                else if(email.text.toString().length == 0)
                    act_start_create_iv_email.setImageResource(R.drawable.et_email)
            }
        })
        password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                password.addTextChangedListener(textWatcher)
                if (hasFocus || password.getText().toString().length != 0) {
                    act_start_create_iv_password.setImageResource(R.drawable.et_password_click)
                    act_start_create_tv_password.setVisibility(View.GONE)
                }
                else {
                    act_start_create_iv_password.setImageResource(R.drawable.et_password)
                    act_start_create_tv_password.setVisibility(View.VISIBLE)
                }
            }
        })
        password_confirm.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                password_confirm.addTextChangedListener(textWatcher)
                if (hasFocus) {
                    act_start_create_tv_passwordagain.setVisibility(View.GONE)
                    act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain_click)
                } else if (password_confirm.getText().toString().length != 0) {
                    act_start_create_tv_passwordagain.setVisibility(View.GONE)
                    if (password.getText().toString() != password_confirm.getText().toString())
                        act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain_error)
                    else
                        act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain_click)
                } else {
                    act_start_create_iv_passwordagain.setImageResource(R.drawable.et_passwordagain)
                    act_start_create_tv_passwordagain.setVisibility(View.VISIBLE)
                    }
             }
          }
        )


        nickName.addTextChangedListener(textWatcher)
        email.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        password_confirm.addTextChangedListener(textWatcher)
    }
    private fun setOnBtnClickListenter(){
        act_start_create_iv_createaccount.setOnClickListener {
            getSignUpResponse()
        }
        act_start_create_iv_nickname_delete.setOnClickListener{
            act_start_create_et_nickname.setText("")
        }
        act_start_create_iv_email_delete.setOnClickListener{
            act_start_create_et_email.setText("")
        }
        act_start_create_iv_password_delete.setOnClickListener{
            act_start_create_et_password.setText("")
        }
        act_start_create_iv_passwordagain_delete.setOnClickListener{
            act_start_create_et_passwordagain.setText("")
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
        if (act_start_create_et_nickname.text.toString().isNotEmpty() && act_start_create_et_email.text.toString().isNotEmpty()
                && act_start_create_et_password.text.toString().isNotEmpty() && act_start_create_et_passwordagain.text.toString().isNotEmpty()
                && act_start_create_et_password.text.toString() == act_start_create_et_passwordagain.text.toString()) {

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
