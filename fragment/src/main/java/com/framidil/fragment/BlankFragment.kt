package com.framidil.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.framidil.fragment.databinding.FragmentBlankBinding

class BlankFragment : Fragment(R.layout.fragment_blank) {
    private var binding: FragmentBlankBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentBlankBinding.inflate(inflater, container, false)
        binding = newBinding

        newBinding.textView.text = "AAAA"
        return newBinding.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}