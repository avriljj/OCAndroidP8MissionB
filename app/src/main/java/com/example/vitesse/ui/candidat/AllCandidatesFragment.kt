package com.example.vitesse.ui.candidat

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vitesse.R
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.ui.add.AddCandidateActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllCandidatesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CandidateAdapter

    private val viewModel: AllCandidatesViewModel by viewModels()


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_candidates, container, false)
        recyclerView = view.findViewById(R.id.recycler_view_all)
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = CandidateAdapter(emptyList())
        recyclerView.adapter = adapter

        observeViewModel()
        // Set up the FAB to launch AddCandidateActivity
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(requireContext(), AddCandidateActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun observeViewModel() {
        viewModel.candidates.observe(viewLifecycleOwner) { candidates ->
            if (candidates != null) {
                adapter.updateData(candidates)
            }
        }
    }


}
