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
import androidx.room.CoroutinesRoom
import androidx.room.RoomDatabase
import com.example.win26.MainActivity
import com.example.win26.R
import com.example.win26.model.BetModel
import com.example.win26.room.BetDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddBetFragment : DialogFragment() {

    private lateinit var roomDatabase: BetDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_bet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nameBet = view.findViewById<EditText>(R.id.edit_Text_nameBet).text
        val oddBet = view.findViewById<EditText>(R.id.edit_Text_oddBet).text
        val amountBet = view.findViewById<EditText>(R.id.editText_amountBet).text
        roomDatabase = BetDatabase.getDatabase(requireContext())
        view.findViewById<AppCompatButton>(R.id.validate_button).setOnClickListener {
            if (nameBet.isNotEmpty()&&oddBet.isNotEmpty()&&amountBet.isNotEmpty()){
                CoroutineScope(Dispatchers.IO).launch {
                    roomDatabase.betDao().insertBet(BetModel(
                        null, roomDatabase.betDao().getAllBet().size, nameBet.toString(), oddBet.toString(), amountBet.toString(), "wait", getPrefBank()!!)
                    )
                    launch(Dispatchers.Main){
                        val currentBank =
                            getPrefBank()!!.toDouble() - amountBet.toString().toDouble()
                        setPrefBank(currentBank.toString())
                        requireActivity().startActivity(Intent(context, MainActivity::class.java))
                    }
                }
            }
        }
    }



    private fun getPrefBank(): String? {
        val sharedPreference = requireActivity().getSharedPreferences("bank", Context.MODE_PRIVATE)
        return sharedPreference.getString("bank", "")
    }

    private fun setPrefBank(sum: String) {
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum)
        }
    }
}