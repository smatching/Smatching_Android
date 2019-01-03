package appjam.sopt.a23rd.smatching.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import appjam.sopt.a23rd.smatching.R

class ImageAdapter(var context: Context?, var layout: Int, var img: ArrayList<Int>) : BaseAdapter() {
    var inf: LayoutInflater? = null

    init {
        inf = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return img!!.size
    }

    override fun getItem(position: Int): Any {
        return img!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null)
            convertView = inf!!.inflate(layout, null)
        val iv = convertView!!.findViewById<View>(R.id.imageView) as ImageView
        //iv.setImageResource(img!![position])

        return convertView
    }
}
