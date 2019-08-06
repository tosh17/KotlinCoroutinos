package ru.thstdio.study.coroutinos.example3.ui.main

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ru.thstdio.study.coroutinos.R
import ru.thstdio.study.coroutinos.example3.Ex3Fragment1
import ru.thstdio.study.coroutinos.example3.Ex3Fragment2


private val TAB_TITLES = arrayOf(
    R.string.tab_text_ex3_1,
    R.string.tab_text_2
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return when (position) {
            0 -> Ex3Fragment1()
            1 -> Ex3Fragment2()
            else -> PlaceholderFragment.newInstance(position + 1)
        }

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}