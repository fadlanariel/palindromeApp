package com.fadlan.palindromeapp.view.main

import android.app.ProgressDialog.show
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.fadlan.palindromeapp.databinding.ActivityMainBinding
import com.fadlan.palindromeapp.view.home.HomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentPalindromeText = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBtn.setOnClickListener { checkPalindrome() }
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra(HomeActivity.EXTRA_NAME, binding.nameEditText.text.toString())
            startActivity(intent)
        }
    }

    private fun checkPalindrome() {
        currentPalindromeText = binding.palindromeEditText.text.toString()
        var message = if (isPalindrome(currentPalindromeText)) "isPalindrome" else "notPalindrome"

        AlertDialog.Builder(this).apply {
            setTitle("Check Palindrome Result")
            setMessage(message)
            setPositiveButton("Kembali", null)
            create()
            show()
        }

    }

    private fun isPalindrome(str: String): Boolean {
        val noSpaces = str.filter { it.isLetterOrDigit() }
        val reversed = noSpaces.reversed()
        return noSpaces.equals(reversed, ignoreCase = true)
    }
}