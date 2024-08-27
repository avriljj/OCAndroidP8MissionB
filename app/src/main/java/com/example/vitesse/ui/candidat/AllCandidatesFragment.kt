package com.example.vitesse.ui.candidat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import com.example.vitesse.R
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.domain.model.Candidat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllCandidatesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CandidateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_candidates, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_all)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter
        adapter = CandidateAdapter(emptyList())
        recyclerView.adapter = adapter

        // Fetch data from database
        fetchCandidates()

        return view
    }

    private fun fetchCandidates() {
        // Use lifecycleScope to launch a coroutine
        lifecycleScope.launch {
            // Get the database instance
            val db = AppDatabase.getDatabase(requireContext(), lifecycleScope)

            // Fetch the candidates from the database
            val candidateDtos = withContext(Dispatchers.IO) {
                db.candidatDtoDao().getAllCandidats() // This should return a list of CandidatDto
            }

            // Convert the list of CandidatDto to a list of Candidat
            val candidates = candidateDtos.map { dto -> Candidat.fromDto(dto) }

            // Update the adapter with the fetched data
            adapter = CandidateAdapter(candidates)
            recyclerView.adapter = adapter
        }
    }

}

