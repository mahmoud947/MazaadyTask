package com.example.mazaadytask.fregments.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModel
import com.example.domain.models.ResultModel
import com.example.mazaadytask.activities.HomeActivity
import com.example.mazaadytask.adapters.ResultAdapter
import com.example.mazaadytask.databinding.FragmentResultBinding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ResultFragment"

@AndroidEntryPoint
class ResultFragment : BaseFragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    override val viewModel: ResultViewModel by viewModels()
    private lateinit var adapter: ResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        val args: ResultFragmentArgs by navArgs()
        viewModel.extractArgs(args.map)
        initViews()
        observers()

        return binding.root
    }

    private fun initViews() {
        adapter = ResultAdapter()
        binding.rvResult.adapter = adapter

        binding.btnGoToUi.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        }
    }

    private fun observers() {
        viewModel.result.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

}