package com.lucascosta.gymtracker.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.databinding.ExerciseInRoutineLineBinding
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener

class ListExerciseInRoutineViewHolder(
    private val binding: ExerciseInRoutineLineBinding,
    private val listener: OnExerciseListener
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindVH(exercise: ExerciseModel) {
        binding.name.text = exercise.name
        binding.primaryMuscleGroup.text = exercise.primaryMuscle.toString()
        binding.sets.text = exercise.sets.toString()
        binding.reps.text = exercise.reps.toString()
        binding.weight.text = exercise.weight.toString()

        binding.exercise.setOnClickListener {
            listener.onClick(exercise)
        }
    }
}