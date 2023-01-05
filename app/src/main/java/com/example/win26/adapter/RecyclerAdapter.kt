package com.example.win26.adapter

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.win26.R
import com.example.win26.model.BetModel

class RecyclerAdapter(val list: List<BetModel>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.BetHolder>() {
    inner class BetHolder(itemView: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val tvNameBet = itemView.findViewById<TextView>(R.id.text_view_name_Bet)
        val tvAmountBet = itemView.findViewById<TextView>(R.id.text_view_bet_amount)
        val tvStatusBet = itemView.findViewById<TextView>(R.id.text_view_bet_amount)
        val tvResultBet = itemView.findViewById<TextView>(R.id.text_view_result)
        val btnWin = itemView.findViewById<AppCompatImageButton>(R.id.win_button)
        val btnLoss = itemView.findViewById<AppCompatImageButton>(R.id.loss_button)
        val btnDelete = itemView.findViewById<AppCompatImageButton>(R.id.delete_button)
        val layout = itemView.findViewById<ConstraintLayout>(R.id.constraint_item)
        fun bind(
            nameBet: String,
            amountBet: String,
            oddBet: String,
            statusBet: String,
            bankBet: String
        ) {
            tvNameBet.text = nameBet
            tvAmountBet.text = amountBet
            tvStatusBet.text = statusBet
            when (statusBet) {
                "win" -> {
                    btnWin.visibility = View.INVISIBLE
                    btnLoss.visibility = View.INVISIBLE
                    tvResultBet.text = (amountBet.toDouble() * oddBet.toDouble()).toString()
                    layout.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.round_corner_layout_win,
                        null
                    )
                }
                "loss" -> {
                    btnWin.visibility = View.INVISIBLE
                    btnLoss.visibility = View.INVISIBLE
                    tvResultBet.text = (amountBet.toDouble() * -1).toString()
                    layout.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.round_corner_layout_loss,
                        null
                    )
                }
                else -> {
                    layout.background = ResourcesCompat.getDrawable(
                        itemView.resources,
                        R.drawable.round_corner_layout,
                        null
                    )
                }
            }
            btnWin.setOnClickListener {
                onItemClickListener.onClick(
                    adapterPosition,
                    BetModel(
                        adapterPosition,
                        adapterPosition,
                        nameBet,
                        oddBet,
                        amountBet,
                        statusBet,
                        bankBet
                    ),
                    "win",
                    tvStatusBet,
                    tvResultBet
                )
                btnWin.visibility = View.INVISIBLE
                btnLoss.visibility = View.INVISIBLE
            }
            btnLoss.setOnClickListener {
                onItemClickListener.onClick(
                    adapterPosition,
                    BetModel(
                        adapterPosition,
                        adapterPosition,
                        nameBet,
                        oddBet,
                        amountBet,
                        statusBet,
                        bankBet
                    ),
                    "loss",
                    tvStatusBet,
                    tvResultBet
                )
                btnWin.visibility = View.INVISIBLE
                btnLoss.visibility = View.INVISIBLE
            }
            btnDelete.setOnClickListener {
                onItemClickListener.onClick(
                    adapterPosition,
                    BetModel(
                        adapterPosition,
                        adapterPosition,
                        nameBet,
                        oddBet,
                        amountBet,
                        statusBet,
                        bankBet
                    ),
                    "delete",
                    tvStatusBet,
                    tvResultBet
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BetHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bet_item, parent, false)
        return BetHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: BetHolder, position: Int) {
        holder.bind(
            list[position].betName,
            list[position].betAmount,
            list[position].betOdd,
            list[position].betStatus,
            list[position].bankCapital
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnItemClickListener {
        fun onClick(
            position: Int,
            databaseModel: BetModel,
            status: String,
            tvStatus: TextView,
            tvResult: TextView
        ) {
        }
    }
}