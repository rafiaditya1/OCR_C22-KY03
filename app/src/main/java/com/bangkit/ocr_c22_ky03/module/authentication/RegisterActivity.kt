package com.bangkit.ocr_c22_ky03.module.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.bangkit.ocr_c22_ky03.R
import com.bangkit.ocr_c22_ky03.databinding.ActivityRegisterBinding
import com.bangkit.ocr_c22_ky03.utils.AuthCallbackString
import com.bangkit.ocr_c22_ky03.utils.showLoading

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        binding.btnRegister.setOnClickListener{
            val name = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val confPassword = binding.edtConfPassword.text.toString()

            userRegister(name, email, password, confPassword)
        }

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun userRegister(name: String, email: String, password: String, confPassword: String) {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            val message = getString(R.string.filled)
            Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
        } else {
            viewModel.register(name, email, password, confPassword, object : AuthCallbackString{
                override fun onResponse(status: String, msg: String) {
                    Toast.makeText(this@RegisterActivity, msg, Toast.LENGTH_LONG).show()
                }

            })
//            Toast.makeText(this@RegisterActivity, getString(R.string.register_success), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}