package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_start_login.*

class StartLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_login)

        val toolbar = findViewById<Toolbar>(R.id.my_toolbar)
        val email = findViewById<EditText>(R.id.act_start_login_et_email)
        val password = findViewById<EditText>(R.id.act_start_login_et_password)
        //툴바 부분
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.btn_back)
        supportActionBar!!.setTitle("")
        //
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(edit: Editable) {
                if (email.getText().toString().isEmpty() && password.getText().toString().isEmpty())
                    act_start_login_btn_login.setImageResource(R.drawable.btn_experience)
                else
                    act_start_login_btn_login.setImageResource(R.drawable.btn_experience_canclick)
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //텍스트의 길이가 변경되었을 경우 발생할 이벤트를 작성.
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //텍스트가 변경될때마다 발생할 이벤트를 작성.
            }

        }
        email.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)
        email.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (!hasFocus && email.getText().toString().length == 0)
                    act_start_login_iv_email.setImageResource(R.drawable.et_email)
                else
                    act_start_login_iv_email.setImageResource(R.drawable.et_email_click)
            }
        })
        password.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus: Boolean) {
                if (!hasFocus && password.getText().toString().length == 0)
                    act_start_login_iv_password.setImageResource(R.drawable.et_password)
                else
                    act_start_login_iv_password.setImageResource(R.drawable.et_password_click)
            }
        })

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
}
