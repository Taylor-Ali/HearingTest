package com.leaf.hearingtest.ui.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.leaf.hearingtest.databinding.FragmentWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.continueBtn.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToUserDetailsFragment()
            it.findNavController().navigate(action)
        }

        binding.viewResultsBtn.setOnClickListener {
            val action = WelcomeFragmentDirections.actionWelcomeFragmentToResultFragment()
            it.findNavController().navigate(action)
        }
    }
}