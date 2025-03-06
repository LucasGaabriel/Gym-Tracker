package com.lucascosta.gymtracker.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.RoutineModel
import com.lucascosta.gymtracker.databinding.RoutineLineBinding

class ListRoutineViewHolder(private val binding: RoutineLineBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindVH(routine: RoutineModel){
        binding.title.text = routine.name
        binding.description.text = routine.description
    }
}