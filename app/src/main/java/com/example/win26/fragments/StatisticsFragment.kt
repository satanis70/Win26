package com.example.win26.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.win26.R
import com.example.win26.model.BetModel
import com.example.win26.room.BetDatabase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.internal.bind.ArrayTypeAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsFragment : Fragment() {

    private lateinit var roomDatabase: BetDatabase
    var listBet = ArrayList<BetModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomDatabase = BetDatabase.getDatabase(requireContext())
        val chartView = view.findViewById<LineChart>(R.id.chart_view)
        //showChart(chartView)
    }

    fun showChart(chartView: LineChart) {
        listBet.clear()
        CoroutineScope(Dispatchers.IO).launch{
            listBet.addAll(roomDatabase.betDao().getAllBet())
            Log.i("STAT", listBet.toString())
            launch(Dispatchers.Main) {
                val listChart = ArrayList<Entry>()
                for (i in listBet.indices){
                    listChart.add(Entry(i.toFloat(), listBet[i].bankCapital.toFloat()))
                }
                listChart.add(Entry(listBet.size.toFloat(), getBank()!!.toFloat()))
                val dataset = LineDataSet(listChart, "")
                dataset.lineWidth = 5f
                dataset.color = Color.BLUE
                dataset.setDrawCircles(true)
                dataset.setDrawCircleHole(false)
                val iLineArray = ArrayList<ILineDataSet>()
                iLineArray.add(dataset)
                val lData = LineData(iLineArray)
                chartView.setBackgroundColor(Color.LTGRAY)
                chartView.axisLeft.textColor = Color.BLACK
                chartView.axisLeft.textSize = 14F
                chartView.axisRight.textColor = Color.BLACK
                chartView.axisRight.textSize = 14F
                chartView.data = lData
                chartView.invalidate()
            }
        }
    }

    private fun getBank(): String? {
        val sharedPreference = requireActivity().getSharedPreferences("bank", Context.MODE_PRIVATE)
        return sharedPreference.getString("bank", "")
    }

    override fun onResume() {
        super.onResume()
        roomDatabase = BetDatabase.getDatabase(requireContext())
        val chartView = requireView().findViewById<LineChart>(R.id.chart_view)
        showChart(chartView)
    }
}