package com.framidil.jetpack_navigation.screens

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.framidil.jetpack_navigation.R
import com.framidil.jetpack_navigation.databinding.FragmentSuperDetailsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SuperDetailsFragment : Fragment(R.layout.fragment_super_details) {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("", "SuperDetailsFragment event: ON_ATTACH")

        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                Log.d("", "SuperDetailsFragment event: $event")
            }
        })
    }

    override fun onDetach() {
        Log.d("", "SuperDetailsFragment event: ON_DETACH")
        super.onDetach()
    }

    lateinit var binding: FragmentSuperDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuperDetailsBinding.inflate(inflater, container, false)
        Log.d("", "SuperDetailsFragment event: ON_CREATE_VIEW, binding: $binding")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("", "SuperDetailsFragment event: ON_VIEW_CREATED")
        super.onViewCreated(view, savedInstanceState)

        val list = listOf("AAA", "BBB", "CCC").let { it + it + it }.let {it + it + it}
        lifecycleScope.launch {
            delay(100)
            binding.rv1.adapter = object : RecyclerView.Adapter<RvViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
                    return RvViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(
                                android.R.layout.simple_list_item_1,
                                parent, false
                            )
                    )
                }

                override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
                    holder.view.findViewById<TextView>(android.R.id.text1).apply {
                        text = list[position]
                        setOnClickListener {
                            findNavController().navigate(R.id.action_superDetailsFragment_to_detailsFragment2)
                        }
                    }
                }

                override fun getItemCount(): Int {
                    return list.size
                }
            }
            binding.rv1.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    class RvViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onDestroyView() {
        Log.d("", "SuperDetailsFragment event: ON_DESTROY_VIEW")
        super.onDestroyView()
    }
}
