package com.example.dagger_second_attemp.fragments.second

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.dagger_second_attemp.R
import com.example.dagger_second_attemp.classes.FirebaseClient
import com.example.dagger_second_attemp.databinding.FragmentSecondBinding
import javax.inject.Inject

class SecondFragment : Fragment(R.layout.fragment_second) {

    lateinit var binding: FragmentSecondBinding

    @Inject
    lateinit var firebaseClient: FirebaseClient

    @Inject
    lateinit var magicInt: Integer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val component: SecondComponent =
            (requireContext().applicationContext as SecondComponentProvider).provideSecondComponent()
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSecondBinding.bind(view)

        binding.textView.text = firebaseClient.getKey() + "$magicInt"
    }
}