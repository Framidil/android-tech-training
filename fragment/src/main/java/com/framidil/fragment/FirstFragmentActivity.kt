package com.framidil.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.lifecycle.lifecycleScope
import com.framidil.fragment.databinding.ActivityFirstFragmentBinding
import com.framidil.fragment.databinding.LayoutFragment1Binding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG = "Lifecycle"


open class Fragment1(val id1: Int) : Fragment() {
    private var binding: LayoutFragment1Binding? = null

    var j = 0

    init {
        Log.d(TAG, "fragment$id1 init")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "fragment$id1 onAttach")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutFragment1Binding.inflate(inflater, container, false)
        Log.d(TAG, "fragment$id1 onCreateView")

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "fragment$id1 onViewCreated")

        binding?.fragment1value?.text = j.toString()
        binding?.fragment1SetButton?.setOnClickListener {
            binding?.fragment1value?.text = (++j).toString()
        }
    }

    override fun onStart() {
        Log.d(TAG, "fragment$id1 onStart")

        super.onStart()
    }

    override fun onResume() {
        Log.d(TAG, "fragment$id1 onResume")

        super.onResume()
    }

    override fun onPause() {
        Log.d(TAG, "fragment$id1 onPause")

        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "fragment$id1 onStop")

        super.onStop()
    }

    override fun onDetach() {
        Log.d(TAG, "fragment$id1 onDetach")

        super.onDetach()
    }

    override fun onDestroyView() {
        Log.d(TAG, "fragment$id1 onDestroyView")

        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        Log.d(TAG, "fragment$id1 onDestroy")

        super.onDestroy()
    }
}


class FirstFragmentActivity : AppCompatActivity() {
    companion object {
        val needSaveLocalVariable = false
        var s = true
    }

    lateinit var binding: ActivityFirstFragmentBinding

    var i = 0

    lateinit var myFragment: Fragment1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "activity onCreate")

        binding = ActivityFirstFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (needSaveLocalVariable) {
            if (savedInstanceState != null) {
                i = savedInstanceState.getInt("A")
                binding.localVariableTextView.text = i.toString()
            }
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                my1 = Fragment1(1)
                add(binding.fragmentContainer1.id, my1!!, "1")
            }
        }

        binding.setLocalVariableButton.setOnClickListener {
            binding.localVariableTextView.text = (++i).toString()
        }

        binding.nextFragmentButton.setOnClickListener {
            supportFragmentManager.commit {
                replace(binding.fragmentContainer1.id, Fragment1(1))
                addToBackStack("?")
//                if (s) {
//                    val fragment = supportFragmentManager.findFragmentByTag("2") ?: Fragment1(2)
//
//                    replace(binding.fragmentContainer1.id, fragment, "2")
//                    addToBackStack("2")
//                } else {
//                    val fragment = supportFragmentManager.findFragmentByTag("1") ?: Fragment1(1)
//
//                    replace(binding.fragmentContainer1.id, fragment, "1")
//                    addToBackStack("1")
//                }
            }
            s = !s
            GlobalScope.launch {
                delay(1000)

                Log.d(TAG, "Fragment count: ${supportFragmentManager.fragments.size}")
                Log.d(TAG, "Fragment backstack count: ${supportFragmentManager.backStackEntryCount}")
            }
        }
    }

    var my1: Fragment1? = null
    var my2: Fragment1? = null
    override fun onBackPressed() {
        super.onBackPressed()
//        supportFragmentManager.popBackStack()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "activity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "activity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "activity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "activity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "activity onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (needSaveLocalVariable) {
            outState.putInt("A", i)
        }

//        supportFragmentManager.putFragment(outState, "a", myFragment)
    }
}