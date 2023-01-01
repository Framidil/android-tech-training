package com.rst.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rst.bottomsheet.databinding.DialogFirstBinding
import com.rst.bottomsheet.databinding.FragmentFirstBinding
import kotlin.properties.Delegates

class SecondFragment : Fragment(R.layout.fragment_first) {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    private var dialog: DialogFragment by Delegates.notNull()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        dialog = DialogFragment()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)

        binding.startButton.setOnClickListener {
            dialog.show(parentFragmentManager, null)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}

class DialogFragment : BottomSheetDialogFragment() {
    private var _binding: DialogFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFirstBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun getTheme(): Int {
        return R.style.osnova_theme_MediaPickerBottomSheetDialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.run {
            dismissWithAnimation = true
            WindowPreferencesManager(requireContext()).applyEdgeToEdgePreference(dialog.window)
            setOnShowListener {
                updateHeight(dialog)
            }
        }

        return dialog
    }

    private fun updateHeight(dialog: BottomSheetDialog) {
        dialog.findViewById<View>(R.id.dialogFirstParent)!!.updateLayoutParams {
            height = getWindowHeight()
        }
    }

    private fun getWindowHeight(): Int {
        return requireContext().resources.displayMetrics.heightPixels + 10
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}