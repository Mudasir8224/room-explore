package com.example.roomexplore

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var wordAdapter: WordAdapter
    private val newWordActivityRequestCode = 1

    private val myViewModel: MyViewModel by viewModels {
        MyViewModelFactory(
            (application as MyApplication).repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rv)
        fab = findViewById(R.id.fab)
        setAdapter()
        addObserver()
        click()
    }

    fun setAdapter() {
        wordAdapter = WordAdapter()
        recyclerView.adapter = wordAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun addObserver() {
        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        myViewModel.allWords.observe(this) {
            it.let { wordAdapter.submitList(it) }
        }
    }

    fun click(){
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK){
            data?.getStringExtra(AddWordActivity.EXTRA_REPLY)?.let {
                val word = Word(it)
                myViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
            }
        }
}