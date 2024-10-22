package com.example.mazaadytask.fregments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.core.base.BaseFragment
import com.example.core.base.BaseViewModel
import com.example.mazaadytask.databinding.FragmentHomeBinding
import com.example.mazaadytask.fregments.home.adapters.StoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var storyAdapter: StoryAdapter

    override val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        initView()
        viewModel.fetchStory()
        observers()
        return binding.root
    }

    private fun initView() {
        storyAdapter = StoryAdapter()
        binding.rvSotry.adapter = storyAdapter
    }
    private fun observers() {
        viewModel.story.observe(viewLifecycleOwner) {
            storyAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}