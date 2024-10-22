package com.example.mazaadytask.fregments.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.mazaadytask.activities.HomeActivity
import com.example.mazaadytask.databinding.FragmentResultBinding
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

private const val TAG = "ResultFragment"
class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        val args: ResultFragmentArgs by navArgs()
        val mapDataJson = args.map

        val mapType = object : TypeToken<Map<String, String>>() {}.type
        val mapData: Map<String, String> = Gson().fromJson(mapDataJson, mapType)

        Log.d(TAG, "onCreateView: $mapData")
        binding.btnGoToUi.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
            requireActivity().finishAffinity()
        }
        return binding.root
    }

}