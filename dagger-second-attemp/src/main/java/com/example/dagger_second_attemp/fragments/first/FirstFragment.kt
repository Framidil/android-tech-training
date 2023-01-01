package com.example.dagger_second_attemp.fragments.first

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.dagger_second_attemp.R
import com.example.dagger_second_attemp.appComponent
import com.example.dagger_second_attemp.classes.FirstFragmentToy
import com.example.dagger_second_attemp.databinding.FragmentFirstBinding
import javax.inject.Inject

class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var binding: FragmentFirstBinding

    @Inject
    lateinit var toy: FirstFragmentToy

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        toy.use()
        binding.textView.text = toy.name
    }
}
