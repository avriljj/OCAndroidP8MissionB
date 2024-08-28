package com.example.vitesse.ui.candidat


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesse.databinding.ItemCandidateBinding
import com.example.vitesse.domain.model.Candidat
import com.example.vitesse.ui.detail.CandidateDetailActivity

class CandidateAdapter(private var candidates: List<Candidat>) : RecyclerView.Adapter<CandidateAdapter.CandidateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidateViewHolder {
        val binding = ItemCandidateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CandidateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CandidateViewHolder, position: Int) {
        holder.bind(candidates[position])
    }

    override fun getItemCount(): Int = candidates.size

    fun updateData(newCandidates: List<Candidat>) {
        candidates = newCandidates
        notifyDataSetChanged()
    }

    inner class CandidateViewHolder(private val binding: ItemCandidateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(candidate: Candidat) {
            binding.textName.text = candidate.name
            binding.textEmail.text = candidate.email

            // Set an onClickListener to open CandidateDetailActivity
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, CandidateDetailActivity::class.java).apply {
                    putExtra("candidateName", candidate.name)
                    putExtra("candidateEmail", candidate.email)
                    putExtra("candidatePhone", candidate.phone)
                    putExtra("candidateNote", candidate.note)
                }
                context.startActivity(intent)
            }
        }
    }
}
