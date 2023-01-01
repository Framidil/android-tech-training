package com.framidil.jetpack_navigation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.framidil.jetpack_navigation.R
import com.framidil.jetpack_navigation.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        const val userNameKey = "userNameKey"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        val navController = (childFragmentManager.findFragmentById(R.id.mainFragmentContainerView)
                as NavHostFragment).navController

        binding.mainBottomNavigationView.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}