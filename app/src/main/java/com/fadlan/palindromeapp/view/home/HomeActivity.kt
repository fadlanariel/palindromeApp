package com.fadlan.palindromeapp.view.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.fadlan.palindromeapp.databinding.ActivityHomeBinding
import com.fadlan.palindromeapp.view.main.MainActivity
import com.fadlan.palindromeapp.view.user.UserActivity


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.name.text = intent.getStringExtra(EXTRA_NAME)

        activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                if (data != null) {
                    val resultData = data.getStringExtra(EXTRA_SELECTED_NAME)
                    binding.selectedUserName.text = resultData
                }
            }
        }

        binding.chooseUserBtn.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()  // Closes the current activity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val TAG = "HomeActivity"
        var EXTRA_NAME = "extra_name"
        var EXTRA_SELECTED_NAME = "Selected User Name"
    }
}