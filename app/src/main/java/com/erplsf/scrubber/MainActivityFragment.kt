package com.erplsf.scrubber

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_main_activity.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService




/**
 * A simple [Fragment] subclass.
 *
 */

class MainActivityFragment : Fragment() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchForm.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.fetchWord(searchForm.text.toString()).observe(this, Observer<WordDefinition> { data ->
                    if (data!!.isValid) {
                        word.text = data.word
                        grammarClass.text = data.grammarGroup
                        meaning.text = data.meaning
                    }
                })

                val inputManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    activity?.currentFocus?.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )

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
