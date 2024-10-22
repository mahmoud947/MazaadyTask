package com.example.mazaadytask.fregments.form

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.core.base.BaseFragment
import com.example.core.utils.Resource
import com.example.domain.models.*
import com.example.mazaadytask.R
import com.example.mazaadytask.databinding.CustomDropdownItemBinding
import com.example.mazaadytask.databinding.FragmentFormBinding
import com.example.mazaadytask.dialogs.model_bottom_sheet.ModalBottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FormFragment"

@AndroidEntryPoint
class FormFragment : BaseFragment() {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!

    override val viewModel: FormViewModel by viewModels()

    private val mainCategory = mutableListOf<Category>()
    private val supCategory = mutableListOf<Children>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        viewModel.getCategories()

        setupMainCategoryClickListener()
        setupSupCategoryClickListener()
        observers()
        binding.btnSubmit.setOnClickListener {
            val map = viewModel.map
            val json = Gson().toJson(map)
            findNavController().navigate(
                FormFragmentDirections.actionFormFragmentToResultFragment(
                    json
                )
            )
        }
        return binding.root
    }

    private fun setupMainCategoryClickListener() {
        viewModel.addMap("Main Category", mainCategory.firstOrNull()?.name ?: "")
        binding.mainCategoryEditText.setOnClickListener {
            val modal = ModalBottomSheetDialog(
                onItemClicked = { item ->
                    supCategory.clear()
                    binding.supCategoryEditText.setText("")
                    binding.llProperties.removeAllViews()
                    mainCategory.find { it.id == item.id }?.children?.let { children ->
                        supCategory.apply {
                            clear()
                            addAll(children.mapNotNull { it })
                        }
                        binding.mainCategoryEditText.setText(item.name)
                    }
                    viewModel.addMap("Main Category", item.name ?: "")
                },
                items = mainCategory
            )
            modal.show(childFragmentManager, ModalBottomSheetDialog.TAG)
        }
    }

    private fun setupSupCategoryClickListener() {
        binding.supCategoryEditText.setOnClickListener {
            if (supCategory.isEmpty()) {
                mainCategory.first()?.children?.let { children ->
                    supCategory.apply {
                        clear()
                        addAll(children.mapNotNull { it })
                    }
                }
            }
            val modal = ModalBottomSheetDialog(
                onItemClicked = { item ->
                    binding.supCategoryEditText.setText(item.name)
                    viewModel.getSubCategories(item.id ?: 0)
                    viewModel.addMap("Sub Category", item.name ?: "")
                },
                items = supCategory
            )
            modal.show(childFragmentManager, ModalBottomSheetDialog.TAG)
        }
    }

    private fun observers() {
        viewModel.categories.observe(viewLifecycleOwner) { items ->
            mainCategory.apply {
                clear()
                addAll(items)
            }
            binding.mainCategoryEditText.setText(mainCategory.firstOrNull()?.name ?: "")
        }
        viewModel.subCategories.observe(viewLifecycleOwner) { items ->
            addSubCategories(items)
        }
    }


    private fun addSubCategories(properties: List<Properties>) {
        binding.llProperties.removeAllViews()
        properties.forEach { property ->
            val customView = CustomDropdownItemBinding.inflate(layoutInflater).apply {
                supCategoryInputLayout.hint = property.name
                root.tag = property.slug
            }
            binding.llProperties.addView(customView.root)
            setupPropertyClickListener(customView, property)
            viewModel.addMap(property.name ?: "", "")
        }
    }

    private fun setupPropertyClickListener(
        customView: CustomDropdownItemBinding,
        property: Properties
    ) {
        customView.supCategoryEditText.setOnClickListener {
            val options = property.options ?: emptyList()
            if (options.isNotEmpty()) {
                val modal = ModalBottomSheetDialog(
                    onItemClicked = { selectedItem ->
                        if (selectedItem.id == -1) {
                            customView.tilOtherOptions.visibility = View.VISIBLE
                            customView.supCategoryEditText.setText(selectedItem.name)
                            customView.tilOtherOptions.hint = selectedItem.name
                        } else {
                            customView.supCategoryEditText.setText(selectedItem.name)
                            fetchAndDisplaySubOptions(selectedItem.id ?: 0, customView)

                            selectedItem.slug?.let { slug ->
                                viewModel.addMap(property.name ?: "", selectedItem.name ?: "")
                            }
                        }

                    },
                    items = options.mapNotNull { it }.toMutableList().apply {
                        add(
                            Option(
                                name = "اختر خيار اخر",
                                id = -1,
                                slug = null,
                                child = false,
                                parent = -1
                            )
                        )
                    }
                )
                modal.show(childFragmentManager, ModalBottomSheetDialog.TAG)
            } else {
                Toast.makeText(requireContext(), "No options available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchAndDisplaySubOptions(id: Int, customView: CustomDropdownItemBinding) {
        viewModel.getOptionChild(id)
        viewModel.optionChild.removeObservers(viewLifecycleOwner)
        viewModel.optionChild.observe(viewLifecycleOwner) { items ->

            customView.llProperties.removeViews(1, customView.llProperties.childCount - 1)
            items.forEach { option ->
                if (option.id == -1) {
                    customView.tilOtherOptions.visibility = View.VISIBLE
                    customView.supCategoryEditText.setText(option.name)
                    customView.tilOtherOptions.hint = option.name
                    return@forEach
                } else {
                    val childView =
                        CustomDropdownItemBinding.inflate(layoutInflater).apply {
                            root.tag = option.slug
                            supCategoryInputLayout.hint = option.name
                        }
                    customView.llProperties.addView(childView.root)
                    setupPropertyClickListener(childView, option)
                }
                viewModel.addMap(option.name ?: "", "")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}