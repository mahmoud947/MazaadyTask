package com.example.mazaadytask.fregments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.core.base.BaseFragment
import com.example.mazaadytask.databinding.FragmentHomeBinding
import com.example.mazaadytask.adapters.StoryAdapter
import com.example.mazaadytask.adapters.TagsAdapter
import com.example.mazaadytask.adapters.ViewPagerAdapter
import com.example.mazaadytask.transformer.DepthPageTransformer
import com.example.mazaadytask.transformer.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import com.example.mazaadytask.R

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var storyAdapter: StoryAdapter
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var tagsAdapter: TagsAdapter
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
        tagsAdapter = TagsAdapter(interaction = object : TagsAdapter.Interaction {
            override fun onItemSelected(position: Int, item: String) {
                tagsAdapter.selectItem(position)
            }
        })
        binding.rvSotry.adapter = storyAdapter

        pagerAdapter = ViewPagerAdapter()
        val list = listOf(
            R.drawable.iv_card_1,
            R.drawable.iv_card_2,
            R.drawable.iv_card_1,
            R.drawable.iv_card_2
        )
        binding.vp.apply {
            adapter = pagerAdapter
            offscreenPageLimit = 5
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(ZoomOutPageTransformer())
        }
        pagerAdapter.submitList(list)
        binding.dotsIndicator.attachTo(binding.vp)

        val tags = listOf("All", "UI/UX", "Illustration", "3D Animation", "Branding", "Photography")
        binding.rvTags.adapter = tagsAdapter
        tagsAdapter.submitList(tags)
        tagsAdapter.selectItem(1)

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