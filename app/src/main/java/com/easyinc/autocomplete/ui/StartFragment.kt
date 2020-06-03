package com.easyinc.autocomplete.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.easyinc.autocomplete.R
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        next.setOnClickListener {
            goToNext()
        }
    }

    private fun goToNext(){
        val text = text_source.text.toString()
        if (text.isEmpty() || text.isBlank()){
            Toast.makeText(context,resources.getString(R.string.enter_big_text_warning),Toast.LENGTH_LONG).show()
        }else{
            val bundle = bundleOf("text" to text)
            navController.navigate(R.id.action_startFragment_to_autoCompleteFragment,bundle)
        }
    }
}