package com.example.vitesse.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesse.R
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.ui.candidat.CandidateAdapter
import com.example.vitesse.ui.candidat.FavoriteCandidatesViewModel

class FavoriteCandidatesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CandidateAdapter

    // Instantiate the ViewModel
    private val viewModel: FavoriteCandidatesViewModel by viewModels()

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

        // Observe the ViewModel
        observeViewModel()

        // Observe the database population and refresh the UI when complete
        AppDatabase.dataPopulationComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete) {
                viewModel.refreshCandidates()
            }
        })

        return view
    }

    private fun observeViewModel() {
        viewModel.favorites.observe(viewLifecycleOwner) { favorites ->
            adapter = CandidateAdapter(favorites)
            recyclerView.adapter = adapter
        }
    }
}
