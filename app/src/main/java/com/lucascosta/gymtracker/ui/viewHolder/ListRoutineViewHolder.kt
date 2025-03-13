package com.lucascosta.gymtracker.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.databinding.RoutineLineBinding
import com.lucascosta.gymtracker.ui.listener.OnRoutineListener

class ListRoutineViewHolder(
    private val binding: RoutineLineBinding,
    private val listener: OnRoutineListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindVH(routine: RoutineWithExercises) {
        binding.title.text = routine.routine.name
        binding.description.text = routine.routine.description

        binding.title.setOnClickListener {
            listener.onClick(routine)
        }
    }
}