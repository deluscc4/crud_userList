package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var pos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userList = ArrayList<Users>()
        userList.add(Users("pedro", "gremio123"))
        userList.add(Users("elen", "inter123"))
        userList.add(Users("gato", "miau123"))

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        binding.listView.adapter = adapter

        binding.listView.setOnItemClickListener { _, _, position, _ ->
            binding.editUsername.setText(userList.get(position).username)
            binding.editPassword.setText(userList.get(position).password)
            pos = position
        }

        binding.buttonAdd.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if (username.isEmpty() && password.isEmpty()) {
                Toast.makeText(applicationContext, "No username and/or password informed.", Toast.LENGTH_LONG)
                    .show()
            } else {
                userList.add(Users(username, password))
                adapter.notifyDataSetChanged()
                cleanFields()
                pos = -1
            }
        }

        binding.buttonEdit.setOnClickListener {
            val username = binding.editUsername.text.toString().trim()
            val password = binding.editPassword.text.toString().trim()

            if (pos >= 0) {
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    userList.get(pos).username = username
                    userList.get(pos).password = password
                    adapter.notifyDataSetChanged()
                    cleanFields()
                    pos = -1
                }
            }

        }

        binding.buttonDelete.setOnClickListener {
            if (pos >= 0) {
                userList.removeAt(pos)
                adapter.notifyDataSetChanged()
                cleanFields()
                pos = -1
            }
        }

        binding.buttonClear.setOnClickListener {
                userList.clear()
                adapter.notifyDataSetChanged()
                cleanFields()
        }
    }

    private fun cleanFields() {
        binding.editUsername.setText("")
        binding.editPassword.setText("")
    }
}