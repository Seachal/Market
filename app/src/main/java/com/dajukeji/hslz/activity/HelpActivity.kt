package com.dajukeji.hslz.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.dajukeji.hslz.R

class HelpActivity: Activity() , View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        findViewById<TextView>(R.id.title_bar_title).setText("帮助")

        findViewById<RelativeLayout>(R.id.title_bar_return).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        finish()
    }

}