package com.example.tbc_better_login_davaleba17.presentation.login

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import com.example.tbc_better_login_davaleba17.databinding.FragmentLoginBinding
import com.example.tbc_better_login_davaleba17.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginFragmentViewModel by viewModels()

    override fun setUp() {
        listeners()
        bindObservers()
        resultListener()
    }

    private fun resultListener() {
        setFragmentResultListener("RegisterResult") { _, bundle ->
            val email = bundle.getString("email")
            val password = bundle.getString("password")
            binding.editTxtEmail.setText(email)
            binding.editTxtPassword.setText(password)
        }
    }

    private fun listeners() {
        with(binding) {
            btnRegister.setOnClickListener {
                navigateToRegister()
            }

            btnLogin.setOnClickListener {
                if (checkFields()) {
                    viewModel.loginUser(
                        UserRequest(
                            editTxtEmail.text.toString(),
                            editTxtPassword.text.toString()
                        ), checkRemember.isChecked
                    )
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        with(binding) {
            if (editTxtEmail.text!!.isEmpty() || editTxtPassword.text!!.isEmpty()) {
                setErrors(editTxtEmail, "Please, fill out all of the fields")
                setErrors(editTxtPassword, "Please, fill out all of the fields")
                return false
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editTxtEmail.text.toString())
                    .matches()
            ) {
                setErrors(editTxtEmail, "Please, enter a valid email address")
                return false
            }
        }
        return true
    }

    private fun setErrors(field: AppCompatEditText, error: String) {
        field.error = error
    }

    private fun navigateToHome(email: String) {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment(email))
    }

    private fun navigateToRegister() {
        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(
                                context,
                                "Successfully logged in ${it.data.token}",
                                Toast.LENGTH_SHORT
                            ).show()
                            //very bad implementation, I know. will fix these days.
                            navigateToHome(binding.editTxtEmail.text.toString())
                        }

                        is Resource.Error -> {
                            Toast.makeText(context, "error: ${it.errorMessage}", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is Resource.Loading -> {
                            binding.progressBar.visibility = if(it.loading) View.VISIBLE else View.INVISIBLE
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}