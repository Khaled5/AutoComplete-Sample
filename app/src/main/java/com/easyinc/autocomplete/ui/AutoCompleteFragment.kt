package com.easyinc.autocomplete.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyinc.autocomplete.presentaion.AutoCompleteViewModel
import com.easyinc.autocomplete.R
import com.easyinc.autocomplete.utils.extentions.convertToList
import com.easyinc.autocomplete.ui.adapter.AutoCompleteAdapter
import kotlinx.android.synthetic.main.fragment_auto_complete.*


class AutoCompleteFragment : Fragment() {

    private lateinit var navController: NavController

    private lateinit var viewModel: AutoCompleteViewModel

    private val adapter = AutoCompleteAdapter()

    private var text: String? = null
    private var list: List<String> = listOf()

    private lateinit var textWatcher: TextWatcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AutoCompleteViewModel::class.java)

        text = arguments?.getString("text")
        list = text!!.convertToList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auto_complete, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        initRecycler()

        initAutoCompleteEditText()

        observeText()

        observeMatchWords()
    }

    private fun initRecycler(){
        auto_complete_recycler.adapter = adapter
        auto_complete_recycler.setHasFixedSize(true)
        auto_complete_recycler.layoutManager = LinearLayoutManager(context)

        adapter.submitList(list)
    }

    private fun initAutoCompleteEditText(){

        textWatcher = object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                viewModel.observeMatchesWords(s.toString().trimEnd().split(' ').last())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val watcher = s.toString()
                viewModel.updateText(watcher,list)

            }

        }

        auto_complete_et.addTextChangedListener(textWatcher)
    }

    private fun observeText(){
        viewModel.watcherLiveData.observe(viewLifecycleOwner, Observer {
            when(it.empty){
                true -> updateWithEmptyIndex(it.text)
                false -> updateText(it.text)
            }
        })
    }

    private fun observeMatchWords(){
        viewModel.matchWordsLiveData.observe(viewLifecycleOwner, Observer {
            adapter.filterAutoComplete(it)
        })
    }

    private fun updateText(newText: String){
        auto_complete_et.removeTextChangedListener(textWatcher)
        auto_complete_et.setText(newText + " ")
        auto_complete_et.setSelection(newText.length + 1)
        auto_complete_et.addTextChangedListener(textWatcher)
    }

    private fun updateWithEmptyIndex(newText: String){
        auto_complete_et.setText(newText)
        auto_complete_et.setSelection(newText.length)
    }
}