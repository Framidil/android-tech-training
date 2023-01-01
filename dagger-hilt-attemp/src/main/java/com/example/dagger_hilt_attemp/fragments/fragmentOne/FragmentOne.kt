package com.example.dagger_hilt_attemp.fragments.fragmentOne

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dagger_hilt_attemp.R
import com.example.dagger_hilt_attemp.classes.BoxOne
import com.example.dagger_hilt_attemp.databinding.FragmentOneBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentOne : Fragment(R.layout.fragment_one) {
    lateinit var binding: FragmentOneBinding

    @Inject
    lateinit var boxOne: BoxOne

    @Inject
    lateinit var factory: HardViewModel.Factory

    private val hardViewModel: HardViewModel by viewModels(factoryProducer = {
        HardViewModel.provideFactory(factory, 111)
    })

    private val easyViewModel: EasyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOneBinding.bind(view)

        binding.textView.text = "${boxOne.name} ${hardViewModel.suffix}"

        easyViewModel.ping()
    }
}