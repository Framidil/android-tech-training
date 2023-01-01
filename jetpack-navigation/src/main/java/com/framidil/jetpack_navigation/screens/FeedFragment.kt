package com.framidil.jetpack_navigation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.framidil.jetpack_navigation.R
import com.framidil.jetpack_navigation.databinding.FragmentDetailsBinding
import com.framidil.jetpack_navigation.databinding.FragmentFeedBinding

class FeedFragment : Fragment(R.layout.fragment_feed) {
    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFeedBinding.bind(view)

        binding.feedTextView.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment2_to_detailsFragment2)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}