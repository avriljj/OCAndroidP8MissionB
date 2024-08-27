package com.example.vitesse.ui.candidat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesse.domain.model.Candidat
import com.example.vitesse.databinding.ItemCandidateBinding

class CandidateAdapter(private val candidates: List<Candidat>) : RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val binding = ItemCandidateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CandidateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        val candidate = candidates[position]
        holder.bind(candidate)
    }

    override fun getItemCount(): Int = candidates.size

    class CandidateViewHolder(private val binding: ItemCandidateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(candidate: Candidat) {
            binding.textName.text = candidate.name
            binding.textEmail.text = candidate.email
            // Bind other fields as necessary
        }
    }
}
