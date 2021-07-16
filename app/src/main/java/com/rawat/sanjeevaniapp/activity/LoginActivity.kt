package com.rawat.sanjeevaniapp.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.google.android.material.textfield.TextInputLayout
import com.rawat.sanjeevaniapp.R
import com.rawat.sanjeevaniapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var sharedPref:SharedPreferences
    private lateinit var continueBtn:Button
    private lateinit var editInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        sharedPref = getSharedPreferences("NameSharedPref",Context.MODE_PRIVATE)

        if(sharedPref.getBoolean("loggedIn",false)){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        continueBtn = binding.continueBtn
        editInputLayout = binding.nameEditText
        goToMainActivity()

    }

    private fun goToMainActivity() {
        val nameEditor = sharedPref.edit()
        continueBtn.setOnClickListener{
            val name = editInputLayout.editText!!.text.toString()
            when {
                name.isEmpty() -> {
                    editInputLayout.error="Name cannot be empty."
                }
                name.length < 4 -> {
                    editInputLayout.error = "Name should be of length greater than 3"
                }
                else -> {
                    editInputLayout.error = null
                    nameEditor.putString("name", name)
                    nameEditor.putBoolean("loggedIn",true)
                    nameEditor.apply()
                    val i = Intent(this, MainActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

        }
    }
}