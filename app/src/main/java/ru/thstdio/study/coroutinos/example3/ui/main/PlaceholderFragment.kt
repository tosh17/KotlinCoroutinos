package ru.thstdio.study.coroutinos.example3.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_activity_ex3.*
import kotlinx.android.synthetic.main.fragment_activity_ex3.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.thstdio.study.coroutinos.R
import ru.thstdio.study.coroutinos.util.end


/**
 * A placeholder fragment containing a simple view.
 */
open class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_activity_ex3, container, false)
        root.apply {
            btn_ex3_send.setOnClickListener {
                btnSendClick()
            }
            initView(this)
        }
        return root
    }

    open  fun initView(view: View) {

    }


    open  fun btnSendClick() {

    }

    fun printMes(str: String) {
        view?.let {
            it.textView_ex3_result.apply {
                text = str.end() + text
            }
        }
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}