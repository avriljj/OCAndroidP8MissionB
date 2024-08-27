package com.example.vitesse.ui.candidat

import android.os.Bundle
import android.util.Log
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

        // Initialize the adapter with an empty list
        adapter = CandidateAdapter(emptyList())
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Populate and fetch data when the view is ready
        populateAndFetchCandidates()
    }

    private fun populateAndFetchCandidates() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext(), lifecycleScope)

            // Populate the database if it's empty
            withContext(Dispatchers.IO) {
                val candidatesCount = db.candidatDtoDao().getAllCandidats().size
                if (candidatesCount == 0) {
                    Log.d("AllCandidatesFragment", "Populating the database with initial data")
                    AppDatabase.populateDatabase(db.candidatDtoDao())
                }
            }

            // Fetch candidates after population
            fetchCandidates()
        }
    }

    private fun fetchCandidates() {
        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext(), lifecycleScope)

            // Fetch the candidates from the database
            val candidateDtos = withContext(Dispatchers.IO) {
                db.candidatDtoDao().getAllCandidats()
            }

            // Convert the list of CandidatDto to a list of Candidat
            val candidates = candidateDtos.map { dto -> Candidat.fromDto(dto) }

            // Log the size of the list to verify how many candidates were fetched
            Log.d("AllCandidatesFragment", "Number of candidates fetched: ${candidates.size}")

            // Ensure the adapter is updated on the main thread
            withContext(Dispatchers.Main) {
                adapter = CandidateAdapter(candidates)
                recyclerView.adapter = adapter
            }
        }
    }
}
