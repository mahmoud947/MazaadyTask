package com.example.mazaadytask.dialogs.model_bottom_sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.domain.models.BottomSheetItem
import com.example.mazaadytask.databinding.ModalsheetdialogBinding
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ModalBottomSheetDialog(
     val onItemClicked: (BottomSheetItem) -> Unit,
     val items: List<BottomSheetItem>
): BottomSheetDialogFragment() {
    private var _binding: ModalsheetdialogBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetAdapter: BottomSheetAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ModalsheetdialogBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun initView() {

        binding.rvItems.setHasFixedSize(true)
        bottomSheetAdapter = BottomSheetAdapter(object : BottomSheetAdapter.Interaction{
            override fun onItemSelected(
                position: Int,
                item: BottomSheetItem
            ) {
                onItemClicked(item)
                dismiss()
            }

        })
        binding.rvItems.adapter = bottomSheetAdapter
        bottomSheetAdapter.submitList(items)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    companion object{
        const val TAG = "ModalBottomSheetDialog"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}