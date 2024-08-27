package com.example.vitesse.ui.candidat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesse.R
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.domain.model.Candidat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope

class FavoriteCandidatesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CandidateAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite_candidates, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_favorite)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with an empty list
        adapter = CandidateAdapter(emptyList())
        recyclerView.adapter = adapter

        // Fetch favorite candidates
        fetchFavoriteCandidates()

        return view
    }

    private fun fetchFavoriteCandidates() {
        lifecycleScope.launch {
            // Get the database instance
            val db = AppDatabase.getDatabase(requireContext(), lifecycleScope)

            // Fetch favorite candidates from the database
            val favoriteDtos = withContext(Dispatchers.IO) {
                db.candidatDtoDao().getFavoriteCandidats()
            }

            // Convert the list of CandidatDto to a list of Candidat
            val favorites = favoriteDtos.map { Candidat.fromDto(it) }

            // Update the adapter with the fetched data
            adapter = CandidateAdapter(favorites)
            recyclerView.adapter = adapter
        }
    }
}
