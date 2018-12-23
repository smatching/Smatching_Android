package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var isEmpty: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var nick_name = findViewById<EditText>(R.id.et_nickname)
        var email = findViewById<EditText>(R.id.et_email)
        var password = findViewById<EditText>(R.id.et_password)
        var password_again = findViewById<EditText>(R.id.et_passwordagain)

    }
    fun onClick(view: View){

        nick_name.setOnClickListener() {
            img_nickname.setImageResource(R.drawable.et_nickname_click)
            if(nick_name.toString().trim().length==0){
                img_nickname.setImageResource(R.drawable.et_email_error)
            }
            else
                img_nickname.setImageResource(R.drawable.et_nickname_click)
        }
    }

}
