package com.example.win26.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import androidx.fragment.app.DialogFragment
import com.example.win26.MainActivity
import com.example.win26.R

class WriteBankFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_write_bank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bankSum = view.findViewById<EditText>(R.id.editText_writeBank)
        view.findViewById<AppCompatButton>(R.id.button_writeBank).setOnClickListener {
            if (bankSum.text.isNotEmpty()){
                setPrefBank(bankSum.text.toString())
                startActivity(Intent(requireContext(), MainActivity::class.java))
            }
        }
    }

    private fun setPrefBank(sum: String) {
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum)
        }
    }
}