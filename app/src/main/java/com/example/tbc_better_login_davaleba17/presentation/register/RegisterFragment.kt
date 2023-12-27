package com.example.tbc_better_login_davaleba17.presentation.register

import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_better_login_davaleba17.presentation.BaseFragment
import com.example.tbc_better_login_davaleba17.data.common.Resource
import com.example.tbc_better_login_davaleba17.databinding.FragmentRegisterBinding
import com.example.tbc_better_login_davaleba17.data.common.UserRequest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterFragmentViewModel by viewModels()

    override fun setUp() {
        listeners()
        bindObservers()
    }

    private fun listeners() {
        with(binding) {
            btnBack.setOnClickListener {
                findNavController().popBackStack()
            }

            btnRegister.setOnClickListener {
                if (checkFields()) {
                    viewModel.registerUser(
                        UserRequest(
                            editTxtEmail.text.toString(),
                            editTxtPassword.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun checkFields(): Boolean {
        with(binding) {
            if (editTxtEmail.text!!.isEmpty() ||
                editTxtPassword.text!!.isEmpty() ||
                editTxtRepeatPassword.text!!.isEmpty()
            ) {
                setErrors(editTxtEmail, "Please, Fill out all of the fields")
                setErrors(editTxtEmail, "Please, Fill out all of the fields")
                setErrors(editTxtEmail, "Please, Fill out all of the fields")
                return false
            } else if (editTxtPassword.text.toString() != editTxtRepeatPassword.text.toString()) {
                setErrors(editTxtPassword, "Passwords do not match!")
                setErrors(editTxtRepeatPassword, "Passwords do not match!")
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

    private fun navigateToLogin(){
        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemFlow.collect {
                    when (it) {
                        is Resource.Success -> {
                            Toast.makeText(
                                context,
                                "Successfully registered ${it.data.token}",
                                Toast.LENGTH_SHORT
                            ).show()
                            setFragmentResult(
                                "RegisterResult",
                                bundleOf(
                                    "email" to binding.editTxtEmail.text.toString(),
                                    "password" to binding.editTxtPassword.text.toString()
                                )
                            )
                            navigateToLogin()
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