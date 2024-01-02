package com.example.tbc_better_login_davaleba17.presentation.splash

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.tbc_better_login_davaleba17.databinding.FragmentSplashBinding
import com.example.tbc_better_login_davaleba17.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {

    private val viewModel: SplashFragmentViewModel by viewModels()
    override fun setUp() {
        bindObservers()
    }

    private fun bindObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.itemFlow.collect {
                    delay(1000)
                    findNavController().navigate(
                        when (it) {
                            is SplashFragmentViewModel.SplashNavigationEvent.NavigationHome -> {
                                SplashFragmentDirections.actionSplashFragmentToHomeFragment(it.email)
                            }

                            is SplashFragmentViewModel.SplashNavigationEvent.NavigationLogin -> {
                                SplashFragmentDirections.actionSplashFragmentToLoginFragment()
                            }
                        }
                    )
                }
            }
        }
    }
}