package com.example.win26.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit
import com.example.win26.MainActivity
import com.example.win26.R
import com.example.win26.room.BetDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingFragment : Fragment() {

    private lateinit var roomDatabase: BetDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val clearBtn = view.findViewById<AppCompatButton>(R.id.button_clearData)
        roomDatabase = BetDatabase.getDatabase(requireContext())
        clearBtn.setOnClickListener {
            clearBank()
            CoroutineScope(Dispatchers.IO).launch{
                roomDatabase.betDao().deleteDatabase()
                launch(Dispatchers.Main){
                    requireActivity().startActivity(Intent(context, MainActivity::class.java))
                }
            }
        }
    }

    private fun clearBank() {
        val sharedPrefCapital =
            requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            remove("bank")
        }
    }
}