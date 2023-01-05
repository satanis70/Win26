package com.example.win26

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.win26.adapter.AdapterViewPager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.onesignal.OneSignal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadBackgroundImage()
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(this)
        OneSignal.setAppId("714b9f14-381d-4fc4-a93c-28d480557381")
        val tab = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        val adapterViewPager = AdapterViewPager(supportFragmentManager, lifecycle)
        viewPager.adapter = adapterViewPager
        TabLayoutMediator(tab, viewPager) { tabItem, position ->
            when (position) {
                0 -> {
                    tabItem.text = "Home"
                    tabItem.icon = ContextCompat.getDrawable(this, R.drawable.home)
                }
                1 -> {
                    tabItem.text = "Statistics"
                    tabItem.icon = ContextCompat.getDrawable(this, R.drawable.chart)
                }
                2 -> {
                    tabItem.text = "Account"
                    tabItem.icon = ContextCompat.getDrawable(this, R.drawable.settings)
                }
            }
        }.attach()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun loadBackgroundImage() {
        Glide.with(this).load("http://49.12.202.175/win26/background.jpg")
            .into(object : SimpleTarget<Drawable?>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    val constraint = findViewById<ConstraintLayout>(R.id.main_constraint)
                    constraint.background = resource
                }
            })
    }
}