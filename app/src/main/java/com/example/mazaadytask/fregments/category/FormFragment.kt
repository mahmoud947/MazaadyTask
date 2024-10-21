package com.example.mazaadytask.fregments.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.utils.Resource
import com.example.mazaadytask.R
import com.example.mazaadytask.databinding.FragmentFormBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FormFragment"
@AndroidEntryPoint
class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    val viewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        viewModel.getCategories()
        observers()
        return binding.root
    }

    private fun observers() {
        viewModel.categories.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    Log.d(TAG, "observers: ${resource.exception}")
                }
                Resource.Idle -> {

                }
                Resource.Loading -> {

                }
                is Resource.Success -> {
                    Log.d(TAG, "observers: ${resource.data}")
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}