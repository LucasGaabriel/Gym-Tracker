package com.lucascosta.gymtracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.databinding.ExerciseLineBinding
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener
import com.lucascosta.gymtracker.ui.viewHolder.ListExerciseViewHolder


class ListExerciseAdapter : RecyclerView.Adapter<ListExerciseViewHolder>() {

    private var exerciseList: List<ExerciseModel> = listOf()
    private lateinit var listener: OnExerciseListener

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ListExerciseViewHolder {
        val item = ExerciseLineBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListExerciseViewHolder(item, listener)
    }

    override fun onBindViewHolder(
        holder: ListExerciseViewHolder, position: Int
    ) {
        holder.bindVH(exerciseList[position])
    }

    override fun getItemCount(): Int {
        return exerciseList.count()
    }

    fun updateExerciseList(list: List<ExerciseModel>) {
        exerciseList = list
        notifyDataSetChanged()
    }

    fun setListener(exerciseListener: OnExerciseListener) {
        listener = exerciseListener
    }
}