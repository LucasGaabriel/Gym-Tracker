package com.lucascosta.gymtracker.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.databinding.ExerciseLineBinding
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener

class ListExerciseViewHolder(
    private val binding: ExerciseLineBinding,
    private val listener: OnExerciseListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindVH(exercise: ExerciseModel) {
        binding.name.text = exercise.name
        binding.primaryMuscleGroup.text = exercise.primaryMuscle.toString()

        binding.name.setOnClickListener({
            listener.onClick(exercise)
        })
    }
}