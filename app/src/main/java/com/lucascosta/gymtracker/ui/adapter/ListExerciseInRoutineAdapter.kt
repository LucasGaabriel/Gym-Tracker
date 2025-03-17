package com.lucascosta.gymtracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.ExerciseModel
import com.lucascosta.gymtracker.databinding.ExerciseInRoutineLineBinding
import com.lucascosta.gymtracker.ui.listener.OnExerciseListener
import com.lucascosta.gymtracker.ui.viewHolder.ListExerciseInRoutineViewHolder

/**
 * Adapter para exibir uma lista de exercícios em uma rotina de treino no [RecyclerView].
 *
 * Este adapter é responsável por criar, vincular e gerenciar as visualizações de cada exercício na lista. Ele
 * usa o [ExerciseInRoutineLineBinding] para inflar as visualizações e o [ListExerciseInRoutineViewHolder] para vincular os dados aos elementos da UI.
 * O listener [OnExerciseListener] é usado para manipular eventos de interação com os itens da lista.
 */
class ListExerciseInRoutineAdapter : RecyclerView.Adapter<ListExerciseInRoutineViewHolder>() {

    private var exerciseList: List<ExerciseModel> = listOf()
    private lateinit var listener: OnExerciseListener

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ListExerciseInRoutineViewHolder {
        val item = ExerciseInRoutineLineBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ListExerciseInRoutineViewHolder(item, listener)
    }

    override fun onBindViewHolder(
        holder: ListExerciseInRoutineViewHolder, position: Int
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
