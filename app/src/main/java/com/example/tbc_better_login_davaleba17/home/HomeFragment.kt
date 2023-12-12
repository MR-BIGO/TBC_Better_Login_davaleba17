package com.example.tbc_better_login_davaleba17.home

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tbc_better_login_davaleba17.BaseFragment
import com.example.tbc_better_login_davaleba17.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val args: HomeFragmentArgs by navArgs()

    override fun setUp() {
        listeners()
        binding.tvEmail.text = args.email
    }

    private fun listeners(){
        binding.btnLogOut.setOnClickListener {
            //cant really log out at the moment
            findNavController().popBackStack()
        }
    }
}