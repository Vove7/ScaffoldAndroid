package cn.daqinjia.android.scaffold.demo.activities

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import cn.daqinjia.android.scaffold.demo.R

/**
 * # IndexActivity
 * Created on 2019/12/13
 *
 * @author Vove
 */
class IndexActivity : AppCompatActivity() {
    val activities: List<ActivityInfo> by lazy {
        packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).let {
            it.activities.filter { i -> i.name != IndexActivity::class.java.name }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(buildContentView())
    }

    private fun buildContentView(): View = ListView(this).apply {
        adapter = object : BaseAdapter() {
            val context = this@IndexActivity
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val item = getItem(position)

                return ((convertView as TextView?) ?: TextView(context).apply {
                    layoutParams = AbsListView.LayoutParams(-1, -2).apply {
                        setPadding(10, 10, 10, 10)
                    }
                    val tv = TypedValue()
                    theme.resolveAttribute(R.attr.selectableItemBackground, tv, true)
                    ViewCompat.setBackground(
                        this,
                        ContextCompat.getDrawable(context, tv.resourceId)
                    )
                }).apply {
                    val cls = Class.forName(item.name)
                    text = cls.simpleName
                    setOnClickListener {
                        context.startActivity(Intent(context, cls))
                    }
                }
            }

            override fun getItem(position: Int): ActivityInfo = activities[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getCount(): Int = activities.size
        }
    }

}