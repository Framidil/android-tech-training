package com.framidil.jetpack_navigation.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.framidil.jetpack_navigation.R
import com.framidil.jetpack_navigation.databinding.FragmentDetailsBinding
import com.framidil.jetpack_navigation.databinding.FragmentSuperDetailsBinding

class DetailsFragment : Fragment(R.layout.fragment_details) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("", "DetailsFragment event: ON_ATTACH")

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.d("", "DetailsFragment event: $event")
            }
        })
    }

    override fun onDetach() {
        Log.d("", "DetailsFragment event: ON_DETACH")
        super.onDetach()
    }

    var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        Log.d("", "DetailsFragment event: ON_CREATE_VIEW, binding: $binding")
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("", "DetailsFragment event: ON_VIEW_CREATED")
        super.onViewCreated(view, savedInstanceState)

        binding!!.detailsTextView.setOnClickListener {
            findNavController().navigate(R.id.action_detailsFragment2_to_superDetailsFragment)
        }
    }

    override fun onDestroyView() {
        Log.d("", "DetailsFragment event: ON_DESTROY_VIEW")
        super.onDestroyView()
    }
}