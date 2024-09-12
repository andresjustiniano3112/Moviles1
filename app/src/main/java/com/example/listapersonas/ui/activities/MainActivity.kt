package com.example.listapersonas.ui.activities
import ContactAdapter
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.listapersonas.R
import com.example.listapersonas.models.Contact

import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), ContactAdapter.OnContactClickListener {
    private val datalist = arrayListOf(
        Contact("Primer practico de moviles 1 "),

    )
    private lateinit var rvContactList: RecyclerView
    private lateinit var fabAddContact: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fabAddContact = findViewById(R.id.fabAddContact)
        rvContactList = findViewById(R.id.rvContactList)
        setupRecyclerView()
        setupEventListeners()
    }

    private fun setupEventListeners() {
        fabAddContact.setOnClickListener {
            buildAlertDialog()
        }
    }

    private fun setupRecyclerView() {
        rvContactList.adapter = ContactAdapter(datalist, this)
        rvContactList.layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
    }

    private fun buildAlertDialog(contact: Contact? = null) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        if (contact != null) {
            builder.setTitle("Editar Nota")
        } else {
            builder.setTitle("Agregar Nota")
        }

        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.form_layout, null, false)

        val txtNewContactPhone: EditText = viewInflated.findViewById(R.id.txtNewContactPhone)
        txtNewContactPhone.setText(contact?.phone) // Rellena el texto si es edición
        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
            val phone = txtNewContactPhone.text.toString()

            if (contact != null) {
                contact.phone = phone
                editContactFromList(contact) // Edita el contacto existente
            } else {
                addContactToList(phone) // Agrega un nuevo contacto
            }
        }
        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }


    private fun editContactFromList(contact: Contact) {
        val adapter = rvContactList.adapter as ContactAdapter
      adapter.itemUpdated(contact)
    }

    private fun addContactToList(phone: String) {
        val contact = Contact(phone)
        val adapter = rvContactList.adapter as ContactAdapter
        adapter.itemAdded(contact)
    }



    override fun onContactEditClickListener(contact: Contact) {
        buildAlertDialog(contact)
    }

    override fun onContactDeleteClickListener(contact: Contact) {
        val adapter = rvContactList.adapter as ContactAdapter
        adapter.itemDeleted(contact)
    }



}