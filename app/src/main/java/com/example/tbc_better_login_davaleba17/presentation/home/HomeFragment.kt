package com.example.tbc_better_login_davaleba17.presentation.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbc_better_login_davaleba17.databinding.FragmentHomeBinding
import com.example.tbc_better_login_davaleba17.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeFragmentViewModel by viewModels()
    private val args: HomeFragmentArgs by navArgs()
    override fun setUp() {
        listeners()
        showEmail()
    }

    private fun listeners() {
        binding.btnLogOut.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.clearSession()
                delay(200)
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
    }

    private fun showEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.tvEmail.text = args.email
        }
    }
}