package com.example.vitesse.ui.add

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import com.example.vitesse.ui.theme.VitesseTheme
import com.example.vitesse.ui.utils.DatePickerField
import androidx.fragment.app.setFragmentResult



class AddCandidateActivity : ComponentActivity() {

    private val viewModel: AddCandidateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VitesseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddCandidateScreen(
                        onBackClick = { onBackPressedDispatcher.onBackPressed() },
                        onSaveClick = { name, surname, phone, email, birthDate, salary, note, isFav ->
                            viewModel.addCandidate(name, surname, phone, email, birthDate, salary, note, isFav)

                            // Set result to indicate that a new candidate was added and finish the activity
                            val resultIntent = Intent().apply {
                                putExtra("refreshNeeded", true)
                            }
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()  // Close the AddCandidateActivity and go back

                            onBackPressedDispatcher.onBackPressed()  // Go back after saving
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCandidateScreen(
    onBackClick: () -> Unit,
    onSaveClick: (name: String, surname: String, phone: String, email: String, birthDate: Date, salary: Int, note: String, isFav: Boolean) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var salary by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isFav by remember { mutableStateOf(false) }

    // Context for DatePickerDialog
    val context = LocalContext.current

    // Initialize calendar
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    // DatePickerDialog
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            calendar.set(year, month, dayOfMonth)
            birthDate = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Candidate") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Candidate Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = surname,
                    onValueChange = { surname = it },
                    label = { Text("Candidate Surname") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )

                DatePickerField(
                    label = "Birth Date (YYYY-MM-DD)",
                    selectedDate = birthDate,
                    onDateSelected = { birthDate = it }
                )

                OutlinedTextField(
                    value = salary,
                    onValueChange = { salary = it },
                    label = { Text("Expected Salary") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
                )

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    maxLines = 5
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mark as Favorite")
                    Checkbox(
                        checked = isFav,
                        onCheckedChange = { isFav = it }
                    )
                }

                Button(
                    onClick = {
                        val birthDateParsed = try {
                            dateFormat.parse(birthDate)
                        } catch (e: Exception) {
                            null
                        }

                        val salaryInt = salary.toIntOrNull() ?: 0

                        if (birthDateParsed != null) {
                            onSaveClick(name, surname, phone, email, birthDateParsed, salaryInt, note, isFav)
                            Log.e("saved","save clicked")
                        } else {
                            // Handle the error for incorrect date format if necessary
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Save")
                }
            }
        }
    )
}
