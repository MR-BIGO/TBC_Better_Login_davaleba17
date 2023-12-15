package com.example.tbc_better_login_davaleba17.login

import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_better_login_davaleba17.BaseFragment
import com.example.tbc_better_login_davaleba17.common.Resource
import com.example.tbc_better_login_davaleba17.databinding.FragmentLoginBinding
import com.example.tbc_better_login_davaleba17.datastore.PreferencesDataStore
import com.example.tbc_better_login_davaleba17.models.UserRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginFragmentViewModel by viewModels()
    //not being used yet.

    override fun setUp() {
        checkCurrentUser()
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
                val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
                findNavController().navigate(action)
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

    private fun checkCurrentUser(){
        viewLifecycleOwner.lifecycleScope.launch {
            if (PreferencesDataStore.getToken().first().isNotEmpty()){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }
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
                            //A quick fix that probably shouldn't remain like this
                            val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            findNavController().navigate(action)
                        }

                        is Resource.Error -> {
                            Toast.makeText(context, "error: ${it.errorMessage}", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is Resource.Loading -> {}
                        else -> {}
                    }
                }
            }
        }
    }
}