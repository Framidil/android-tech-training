package com.rst.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rst.bottomsheet.databinding.FragmentFirstBinding
import kotlin.properties.Delegates.notNull

class FirstFragment : Fragment(R.layout.fragment_first) {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var dialog: BottomSheetDialog by notNull()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        dialog =
            BottomSheetDialog(requireContext(), R.style.osnova_theme_MediaPickerBottomSheetDialog)
        dialog.run {
            setContentView(R.layout.dialog_first)
            dismissWithAnimation = true
                WindowPreferencesManager(requireContext()).applyEdgeToEdgePreference(dialog.window)

            setOnShowListener {
                updateHeight()
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)

        binding.startButton.setOnClickListener {
            dialog.show()
        }
    }

    private fun updateHeight() {
        dialog.findViewById<View>(R.id.dialogFirstParent)!!.updateLayoutParams {
            height = getWindowHeight()
        }
    }

    private fun getWindowHeight(): Int {
        return requireContext().resources.displayMetrics.heightPixels + 200
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}