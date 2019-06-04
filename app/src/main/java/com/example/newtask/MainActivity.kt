package com.example.newtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var titleTextName : EditText
    lateinit var dateTextName : EditText
    lateinit var locationTextName : EditText
    lateinit var peopleTextName : EditText
    lateinit var noteTextName : EditText
    lateinit var buttonSave : Button
    lateinit var buttonTravail : Button
    lateinit var buttonPersonnel : Button
    lateinit var buttonDivers : Button
    lateinit var taskType : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTravail = findViewById(R.id.button_travail)
        buttonPersonnel = findViewById(R.id.button_personnel)
        buttonDivers = findViewById(R.id.button_divers)

        titleTextName= findViewById(R.id.title_field)
        dateTextName= findViewById(R.id.date_field)
        locationTextName= findViewById(R.id.location_field)
        peopleTextName= findViewById(R.id.people_field)
        noteTextName= findViewById(R.id.note_field)
        buttonSave = findViewById(R.id.buttonSave)

        taskType = "travail"

        buttonSave.setOnClickListener {
            saveData()
            val intent = Intent(this,SecondActivity::class.java)
            startActivity(intent)
        }

        buttonTravail.setOnClickListener {
            taskType = "travail"
        }

        buttonPersonnel.setOnClickListener {
            taskType = "personnel"
        }

        buttonDivers.setOnClickListener {
            taskType = "divers"
        }

    }

    private fun saveData()
    {
        val title = titleTextName.text.toString().trim()
        val date = dateTextName.text.toString()
        val location = locationTextName.text.toString().trim()
        val people = peopleTextName.text.toString().trim()
        val note = noteTextName.text.toString().trim()
        val type = taskType


        if(title.isEmpty()) {
            titleTextName.error = "Please enter a title"
            return
        }

        if(date.isEmpty()) {
            dateTextName.error = "Please enter a date"
            return
        }

        if(location.isEmpty()) {
            locationTextName.error = "Please enter a location"
            return
        }

        if(people.isEmpty()) {
            peopleTextName.error = "Please enter a people"
            return
        }

        if(note.isEmpty()) {
            noteTextName.error = "Please enter a note"
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("datas")
        val dataId = ref.push().key

        val data = Data(dataId.toString(), type, title, date, location, people, note)

        ref.child(dataId.toString()).setValue(data).addOnCompleteListener {
            Toast.makeText(applicationContext, "Data saved successfully", Toast.LENGTH_LONG).show()
        }
    }
}
