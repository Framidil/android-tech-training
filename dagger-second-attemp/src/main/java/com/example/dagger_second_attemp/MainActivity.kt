package com.example.dagger_second_attemp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.dagger_second_attemp.classes.OkHttpClient
import com.example.dagger_second_attemp.databinding.ActivityMainBinding
import com.example.dagger_second_attemp.fragments.first.FirstFragmentDirections
import com.example.dagger_second_attemp.fragments.second.SecondFragmentDirections
import javax.inject.Inject

@SuppressLint("SetTextI18n")
class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController
        get() = (supportFragmentManager
            .findFragmentById(R.id.mainFragmentContainerView) as NavHostFragment)
            .navController

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MainApplication).appComponent.inject(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() = with(binding) {
        button.setOnClickListener {
            val action = if (navController.currentDestination?.id == R.id.firstFragment) {
                FirstFragmentDirections.actionFirstFragmentToSecondFragment()
            } else {
                SecondFragmentDirections.actionSecondFragmentToFirstFragment()
            }
            navController.navigate(action)
        }

        textView.text = "OkHttpClient version: ${okHttpClient.number}"
    }
}