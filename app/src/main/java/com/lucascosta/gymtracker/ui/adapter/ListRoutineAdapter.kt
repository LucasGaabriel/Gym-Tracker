package com.lucascosta.gymtracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucascosta.gymtracker.data.model.RoutineWithExercises
import com.lucascosta.gymtracker.databinding.RoutineLineBinding
import com.lucascosta.gymtracker.ui.listener.OnRoutineListener
import com.lucascosta.gymtracker.ui.viewHolder.ListRoutineViewHolder

/**
 * Adapter para exibir uma lista de rotinas de treino com seus respectivos exercícios no [RecyclerView].
 *
 * Este adapter é responsável por criar, vincular e gerenciar as visualizações de cada rotina de treino e seus exercícios.
 * Ele usa o [RoutineLineBinding] para inflar as visualizações e o [ListRoutineViewHolder] para vincular os dados aos elementos da UI.
 * O listener [OnRoutineListener] é usado para manipular eventos de interação com os itens da lista de rotinas.
 */
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