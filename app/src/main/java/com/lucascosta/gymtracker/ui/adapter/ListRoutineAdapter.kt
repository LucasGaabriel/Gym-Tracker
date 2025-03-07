package com.lucascosta.gymtracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.databinding.RoutineLineBinding
import com.lucascosta.gymtracker.ui.listener.OnRoutineListener
import com.lucascosta.gymtracker.ui.viewHolder.ListRoutineViewHolder

class ListRoutineAdapter : RecyclerView.Adapter<ListRoutineViewHolder>() {

    private var routineList: List<RoutineWithExercises> = listOf()
    private lateinit var listener: OnRoutineListener

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ListRoutineViewHolder {
        val item = RoutineLineBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListRoutineViewHolder(item, listener)
    }

    override fun onBindViewHolder(
        holder: ListRoutineViewHolder, position: Int
    ) {
        holder.bindVH(routineList[position])
    }

    override fun getItemCount(): Int {
        return routineList.count()
    }

    fun updateRoutineList(list: List<RoutineWithExercises>) {
        routineList = list
        notifyDataSetChanged()
    }

    fun setListener(routineListener: OnRoutineListener) {
        listener = routineListener
    }
}