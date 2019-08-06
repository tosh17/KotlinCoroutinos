package ru.thstdio.study.coroutinos.example3

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_ex3.*
import ru.thstdio.study.coroutinos.R
import ru.thstdio.study.coroutinos.example3.ui.main.SectionsPagerAdapter

class ActivityEx3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex3)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
       anim()
    }
    private fun anim() {
        val set = AnimatorInflater.loadAnimator(this, R.animator.start_roteded) as AnimatorSet
        set.setTarget(imageView_ex3)
        set.start()
    }
}