package appjam.sopt.a23rd.smatching

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import appjam.sopt.a23rd.smatching.network.ApplicationController
import appjam.sopt.a23rd.smatching.network.NetworkService

class TestActivity : AppCompatActivity() {
    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

//    alert dialog 구현 부분(in java)
//    final CharSequence[] items = {"Red", "Green", "Blue"};
//
//    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//    builder.setTitle("Colors");
//    builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
//        public void onClick(DialogInterface dialog, int item) {
//            Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
//        }
//    });
//    AlertDialog alert = builder.create();
//    alert.show();
}
