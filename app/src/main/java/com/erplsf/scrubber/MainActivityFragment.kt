package com.erplsf.scrubber

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main_activity.*


/**
 * A simple [Fragment] subclass.
 *
 */

class MainActivityFragment : Fragment() {
    private val viewModel: MainActivityViewModel by viewModels()

    private fun mapWordToView(wordDefinition: WordDefinition) {
        val existingTextView: TextView = activity!!.findViewById(R.id.wordTextView)
        val mapLayout: LinearLayout = activity!!.findViewById(R.id.mapLayout)

        // add word
        existingTextView.text = wordDefinition.word

        // map grammarGroup/meanings
        val mgGenerator = meaningGroupGenerator(mapLayout).iterator()
        var childrenCount = mapLayout.childCount

        for (grammarGroup in wordDefinition.meanings) {
            (mgGenerator.next() as TextView).text = grammarGroup.key
            (mgGenerator.next() as TextView).text = grammarGroup.value
        }

        while (childrenCount > wordDefinition.meanings.size * 2) {
            mapLayout.removeView(mgGenerator.next())
            mapLayout.removeView(mgGenerator.next())
            childrenCount -= 2
        }
    }

    private fun wipeView() {
        val existingTextView: TextView = activity!!.findViewById(R.id.wordTextView)
        val mapLayout: LinearLayout = activity!!.findViewById(R.id.mapLayout)

        existingTextView.text = ""
        mapLayout.removeAllViews()
    }

    private fun meaningGroupGenerator(layout: LinearLayout): Sequence<View> = sequence {
        var existingMeanings = layout.children

        while (existingMeanings.count() > 0) {
            val returnSeq = existingMeanings.take(2)
            existingMeanings = existingMeanings.drop(2)
            yieldAll(returnSeq.toList())
        }

        while (true) {
            val meaningTextView = TextView(activity)
            meaningTextView.setTextAppearance(R.style.meaning)
            layout.addView(meaningTextView)

            val grammarClassTextView = TextView(activity)
            grammarClassTextView.setTextAppearance(R.style.grammarClass)
            layout.addView(grammarClassTextView)

            yieldAll(listOf(meaningTextView, grammarClassTextView))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchForm.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val inputManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    activity?.currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
                viewModel.fetchWord(searchForm.text.toString()).observe(this, Observer<WordDefinition?> { data ->
                    if (data != null) {
                        if (data.isValid) {
                            mapWordToView(data)
                        }
                    } else {
                        wipeView()
                    }
                })

                return@OnEditorActionListener true
            }
            return@OnEditorActionListener false
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_main_activity, container, false)
    }
}
