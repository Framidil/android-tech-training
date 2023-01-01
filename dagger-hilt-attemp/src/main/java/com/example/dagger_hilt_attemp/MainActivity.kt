package com.example.dagger_hilt_attemp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dagger_hilt_attemp.classes.OkHttpClient
import com.example.dagger_hilt_attemp.databinding.ActivityMainBinding
import com.example.dagger_hilt_attemp.fragments.FragmentTwo
import com.example.dagger_hilt_attemp.fragments.fragmentOne.FragmentOne
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addInitFragment()
        setupViews()
    }

    private fun addInitFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(binding.fragmentContainerView.id, FragmentOne())
        transaction.commit()
    }

    private fun setupViews() {
        binding.textView.text = okHttpClient.version

        binding.button.setOnClickListener {
            val transaction = supportFragmentManager.beginTransaction()
            val fragment =
                if (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) is FragmentOne) {
                    FragmentTwo()
                } else {
                    FragmentOne()
                }
            transaction.replace(binding.fragmentContainerView.id, fragment)
            transaction.commit()
        }
    }
}