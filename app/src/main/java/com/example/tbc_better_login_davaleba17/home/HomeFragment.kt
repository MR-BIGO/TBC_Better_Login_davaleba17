package com.example.tbc_better_login_davaleba17.home

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.tbc_better_login_davaleba17.BaseFragment
import com.example.tbc_better_login_davaleba17.databinding.FragmentHomeBinding
import com.example.tbc_better_login_davaleba17.datastore.PreferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override fun setUp() {
        listeners()
        showEmail()
    }

    private fun listeners(){
        binding.btnLogOut.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                PreferencesDataStore.clearSession()
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
    }

    private fun showEmail(){
        viewLifecycleOwner.lifecycleScope.launch {
            binding.tvEmail.text =  PreferencesDataStore.getEmail().first()
        }
    }
}