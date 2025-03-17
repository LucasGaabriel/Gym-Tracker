package com.lucascosta.gymtracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.databinding.ExerciseLineBinding
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener
import com.lucascosta.gymtracker.ui.viewHolder.ListExerciseViewHolder

/**
 * Adapter para exibir uma lista de exercícios em um [RecyclerView].
 *
 * Este adapter é responsável por criar, vincular e gerenciar as visualizações de cada exercício na lista. Ele
 * usa o [ExerciseLineBinding] para inflar as visualizações e o [ListExerciseViewHolder] para vincular os dados aos elementos da UI.
 * O listener [OnExerciseListener] é usado para manipular eventos de interação com os itens da lista.
 */
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