package com.fadlan.palindromeapp.view.user

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadlan.palindromeapp.ViewModelFactory
import com.fadlan.palindromeapp.adapter.UserAdapter
import com.fadlan.palindromeapp.databinding.ActivityUserBinding
import com.fadlan.palindromeapp.view.home.HomeActivity

class UserActivity : AppCompatActivity() {
    private val viewModel by viewModels<UserViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = UserAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.users.observe(this) { pagingData ->
            adapter.submitData(lifecycle, pagingData)

            binding.emptyStateTextView.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
        }

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(name: String) {
                val resultIntent = Intent()
                resultIntent.putExtra(HomeActivity.EXTRA_SELECTED_NAME, name)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}