package com.example.vitesse.ui.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vitesse.R
import com.example.vitesse.data.database.AppDatabase
import com.example.vitesse.databinding.ActivityCandidateDetailBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CandidateDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCandidateDetailBinding
    private var candidateId: Long =  (-1).toLong()  // Replace with your candidate's ID type if it's different
    private var isFavorite: Boolean = false  // Assuming you have a way to determine if it's a favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCandidateDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Retrieve the candidate data passed from the previous activity
        candidateId =
            intent.getLongExtra("candidateId", -1) // Ensure the candidate ID is passed
        val candidateName = intent.getStringExtra("candidateName")
        val candidateEmail = intent.getStringExtra("candidateEmail")
        val candidatePhone = intent.getStringExtra("candidatePhone")
        val candidateNote = intent.getStringExtra("candidateNote")

        // Bind data to views
        binding.textName.text = candidateName
        binding.textEmail.text = candidateEmail
        binding.textPhone.text = candidatePhone
        binding.textNote.text = candidateNote
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_candidate_detail, menu)
        // Set the favorite star state
        val favoriteItem = menu?.findItem(R.id.action_favorite)
        updateFavoriteIcon(favoriteItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_favorite -> {
                toggleFavorite(item)
                true
            }
            R.id.action_edit -> {
                editCandidate()
                true
            }
            R.id.action_delete -> {
                deleteCandidate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleFavorite(item: MenuItem?) {
        isFavorite = !isFavorite
        updateFavoriteIcon(item)
        // Here, you would update the favorite status in your database or model
        Toast.makeText(this, if (isFavorite) "Marked as favorite" else "Unmarked as favorite", Toast.LENGTH_SHORT).show()
    }

    private fun updateFavoriteIcon(item: MenuItem?) {
        item?.setIcon(if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }

    private fun editCandidate() {
        // Launch edit activity or show edit dialog
        Toast.makeText(this, "Edit candidate", Toast.LENGTH_SHORT).show()
    }

    private fun deleteCandidate() {
        if (candidateId != (-1).toLong()) {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val database = AppDatabase.getDatabase(applicationContext, lifecycleScope)  // Pass the CoroutineScope here
                    val candidateDao = database.candidatDtoDao()
                    candidateDao.deleteCandidateById(candidateId)
                }
                Toast.makeText(this@CandidateDetailActivity, "Candidate deleted", Toast.LENGTH_SHORT).show()
                finish()  // Close the activity and go back
            }
        } else {
            Toast.makeText(this, "Candidate not found $candidateId", Toast.LENGTH_SHORT).show()

        }
    }
}
