package com.firebaseisawesome.firestore

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.firebaseisawesome.R
import com.firebaseisawesome.ui.adapter.MsgAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_firestore.*

class FirestoreActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var mAuth: FirebaseAuth

    private lateinit var lstMsg : MutableList<Map<String, Any>>
    private lateinit var adapter: MsgAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firestore)

        db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        lstMsg = mutableListOf()
        adapter = MsgAdapter(this, lstMsg)

        firestoreListView.adapter = adapter

        initSnapshot()
    }

    private fun initSnapshot() {
        db.collection("chat").document("mensajes")
            .addSnapshotListener{snapshot, e ->
            if (e != null) {
                Log.w("", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                snapshot.data?.let { lstMsg.add(it) }
                adapter.notifyDataSetChanged()
            } else {
                Log.d("", "Current data: null")
            }
        }
    }

    fun onClickSaveData(view: View) {
        val mensaje = hashMapOf(
            "email" to (mAuth.currentUser?.email ?: ""),
            "mensaje" to firestoreEtMsj.text.toString()
        )

        db.collection("chat").document("mensajes")
            .set(mensaje)
            .addOnSuccessListener { documentReference ->
                firestoreEtMsj.setText("")
            }
            .addOnFailureListener { e ->
                Log.w("", "Error adding document", e)
            }
    }
}