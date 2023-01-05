package com.example.win26.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.win26.MainActivity
import com.example.win26.R
import com.example.win26.adapter.RecyclerAdapter
import com.example.win26.model.BetModel
import com.example.win26.room.BetDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeFragment : Fragment(), RecyclerAdapter.OnItemClickListener {

    private var betList = ArrayList<BetModel>()
    private lateinit var roomDatabase: BetDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (getBank()!!.isEmpty()) {
            val showDialog = WriteBankFragment()
            showDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        }
        roomDatabase = BetDatabase.getDatabase(requireContext())
        val tvBank = view.findViewById<TextView>(R.id.textView_home_bank)
        tvBank.text = "Bank: ${getBank()}"
        view.findViewById<FloatingActionButton>(R.id.floating_button).setOnClickListener {
            val showDialog = AddBetFragment()
            showDialog.show((activity as AppCompatActivity).supportFragmentManager, "")
        }
        getData()
    }

    private fun getBank(): String? {
        val sharedPreference = requireActivity().getSharedPreferences("bank", Context.MODE_PRIVATE)
        return sharedPreference.getString("bank", "")
    }

    private fun setBank(sum: String) {
        val sharedPrefCapital = requireContext().getSharedPreferences("bank", Context.MODE_PRIVATE)
        sharedPrefCapital.edit {
            putString("bank", sum)
        }
    }

    fun getData(){
        betList.clear()
        CoroutineScope(Dispatchers.IO).launch{
            betList.addAll(roomDatabase.betDao().getAllBet())
            launch(Dispatchers.Main){
                val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                val adapter = RecyclerAdapter(betList, this@HomeFragment)
                recyclerView.adapter = adapter
            }
        }

    }

    override fun onClick(
        position: Int,
        databaseModel: BetModel,
        status: String,
        tvStatus: TextView,
        tvResult: TextView
    ) {
        val tvBank = requireView().findViewById<TextView>(R.id.textView_home_bank)
        val amountBet = betList[position].betAmount
        val oddBet = betList[position].betOdd
        when (status) {
            "loss" -> {
                tvStatus.text = status
                tvResult.text = (amountBet.toDouble() * -1).toString()
                tvBank.text = getBank()
                CoroutineScope(Dispatchers.IO).launch {
                    roomDatabase.betDao().updateBet("loss", position)
                    getData()
                }
            }
            "win" -> {
                val result = oddBet.toDouble() * amountBet.toDouble()
                setBank((getBank()!!.toDouble() + result).toString())
                tvResult.text = result.toString()
                tvStatus.text = status
                tvBank.text = getBank()
                CoroutineScope(Dispatchers.IO).launch {
                    roomDatabase.betDao().updateBet("win", position)
                    getData()
                }
            }
            "delete" -> {
                CoroutineScope(Dispatchers.IO).launch {
                    betList = roomDatabase.betDao().getAllBet() as ArrayList<BetModel>
                    launch(Dispatchers.Main) {
                        if (betList[position].betStatus == "loss") {
                            var summfinal = getBank()!!.toDouble()
                            summfinal += betList[position].betAmount.toDouble()
                            setBank(summfinal.toString())
                            tvBank.text = "$summfinal"
                            launch(Dispatchers.IO) {
                                roomDatabase.betDao().deleteBet(betList[position])
                                betList = roomDatabase.betDao().getAllBet() as ArrayList<BetModel>
                                launch(Dispatchers.Main) {
                                    getData()
                                }
                            }
                        } else if (betList[position].betStatus == "win") {
                            var summfinal = getBank()!!.toDouble()
                            summfinal -= betList[position].betAmount.toDouble()
                            setBank(summfinal.toString())
                            tvBank.text = "$summfinal"
                            CoroutineScope(Dispatchers.IO).launch {
                                roomDatabase.betDao().deleteBet(betList[position])
                                betList = roomDatabase.betDao().getAllBet() as ArrayList<BetModel>
                                launch(Dispatchers.Main) {
                                    getData()
                                }
                            }
                        } else {
                            var summfinal = getBank()!!.toDouble()
                            summfinal += betList[position].betAmount.toDouble()
                            setBank(summfinal.toString())
                            tvBank.text = "$summfinal"
                            launch(Dispatchers.IO) {
                                roomDatabase.betDao().deleteBet(betList[position])
                                betList = roomDatabase.betDao().getAllBet() as ArrayList<BetModel>
                                launch(Dispatchers.Main) {
                                    getData()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}