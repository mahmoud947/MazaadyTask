package com.example.mazaadytask.fregments.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.core.utils.Resource
import com.example.domain.models.BottomSheetItem
import com.example.domain.models.Category
import com.example.domain.models.Children
import com.example.domain.models.Option
import com.example.domain.models.Properties
import com.example.mazaadytask.databinding.CustomDropdownItemBinding
import com.example.mazaadytask.databinding.FragmentFormBinding
import com.example.mazaadytask.dialogs.model_bottom_sheet.ModalBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FormFragment"

@AndroidEntryPoint
class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    val viewModel: CategoryViewModel by viewModels()

    private val mainCategory: MutableList<Category> = mutableListOf()
    private val supCategory: MutableList<Children> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        viewModel.getCategories()

        binding.mainCategoryEditText.setOnClickListener {
            val modal = ModalBottomSheetDialog(
                onItemClicked = { item ->
                    val supCategories =
                        mainCategory.find { it.id == item.id }?.children?.mapNotNull { it }
                    supCategory.clear()
                    supCategories?.let { elements -> supCategory.addAll(elements) }
                    binding.supCategoryEditText.setText(supCategories?.firstOrNull()?.name ?: "")
                    binding.mainCategoryEditText.setText(item.name)
                },
                items = mainCategory
            )
            childFragmentManager.let {
                modal.show(it, ModalBottomSheetDialog.TAG)
            }
        }

        binding.supCategoryEditText.setOnClickListener {
            val modal = ModalBottomSheetDialog(
                onItemClicked = { item ->
                    binding.supCategoryEditText.setText(item.name)
                    viewModel.getSubCategories(item.id ?: 0)
                },
                items = supCategory
            )
            childFragmentManager.let {
                modal.show(it, ModalBottomSheetDialog.TAG)
            }
        }
        observers()
        return binding.root
    }

    private fun observers() {
        viewModel.categories.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    Log.e(TAG, "Error fetching categories: ${resource.exception}")
                }

                Resource.Loading -> {
                    Log.d(TAG, "Fetching categories...")
                }

                is Resource.Success -> {
                    val categories = resource.data.mapNotNull { it }
                    binding.mainCategoryEditText.setText(categories.firstOrNull()?.name ?: "")
                    mainCategory.addAll(categories)
                }

                Resource.Idle -> {}
            }
        }

        viewModel.subCategories.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    Log.e(TAG, "Error fetching subcategories: ${resource.exception}")
                }

                Resource.Loading -> {
                    Log.d(TAG, "Fetching subcategories...")
                }

                is Resource.Success -> {
                    val properties = resource.data.map { it }
                    addSubCategories(properties)
                }

                Resource.Idle -> {}
            }
        }
    }

    private fun addSubCategories(list: List<Properties>) {
        binding.llProperties.removeAllViews()

        list.forEach { item ->
            val customView = CustomDropdownItemBinding.inflate(layoutInflater)
            customView.supCategoryInputLayout.hint = item.name
            customView.root.tag = item.slug
            binding.llProperties.addView(customView.root)

            customView.supCategoryEditText.setOnClickListener {
                val options: List<Option> = item.options?.mapNotNull { it } ?: emptyList()
                if (options.isNotEmpty()) {
                    val modal = ModalBottomSheetDialog(
                        onItemClicked = { selectedItem ->
                            customView.supCategoryEditText.setText(selectedItem.name)
                            fetchAndDisplaySubOptions(selectedItem.id ?: 0, customView)
                        },
                        items = options
                    )
                    childFragmentManager.let { modal.show(it, ModalBottomSheetDialog.TAG) }
                } else {
                    Toast.makeText(requireContext(), "No options available", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun fetchAndDisplaySubOptions(id: Int, customView: CustomDropdownItemBinding) {
        // Fetch sub-options based on the selected item
        viewModel.getOptionChild(id)

        // Remove previous observers before adding new ones
        viewModel.optionChild.removeObservers(viewLifecycleOwner)
        viewModel.optionChild.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Error -> {
                    Log.e(TAG, "Error fetching child options: ${resource.exception}")
                }

                Resource.Idle -> {
                    Log.d(TAG, "Status: Idle")
                }

                Resource.Loading -> {
                    Log.d(TAG, "Status: Loading")
                }

                is Resource.Success -> {
                    val options = resource.data.mapNotNull { it }

                    customView.llProperties.removeViews(1, customView.llProperties.childCount - 1)

                    if (options.isNotEmpty()) {
                        options.forEach { item ->
                            val childView = CustomDropdownItemBinding.inflate(layoutInflater)
                            childView.root.tag = item.slug.toString()

                            childView.supCategoryInputLayout.hint = item.name

                            customView.llProperties.addView(childView.root)

                            childView.supCategoryEditText.setOnClickListener {
                                val modal = ModalBottomSheetDialog(
                                    onItemClicked = { selectedItem ->
                                        childView.supCategoryEditText.setText(selectedItem.name)
                                        fetchAndDisplaySubOptions(selectedItem.id ?: 0, childView)
                                    },
                                    items = item.options?.mapNotNull { it } ?: emptyList()
                                )
                                modal.show(childFragmentManager, ModalBottomSheetDialog.TAG)
                            }
                        }
                    } else {
                        Log.d(TAG, "No options available for this item")
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}