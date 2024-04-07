package com.leaf.hearingtest.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.leaf.hearingtest.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: ResultViewModel by viewModels()
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var resultAdapter: ResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        binding.refresh.setOnRefreshListener(this)
        viewModel.getResults()

        viewModel.resultsLiveData.observe(viewLifecycleOwner) {
            showResultLayout(it)
        }

        viewModel.showErrorLiveData.observe(viewLifecycleOwner) {
            if (it) {
                showError()
            }
        }
    }

    private fun setupRecyclerView() {
        binding.resultsList.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        resultAdapter = ResultAdapter()
        binding.resultsList.adapter = resultAdapter
    }

    private fun showResultLayout(results: List<Result>) {
        resultAdapter.setItems(results)

        binding.loader.loadingLayout.visibility = View.GONE
        binding.refresh.visibility = View.VISIBLE

    }

    private fun showError() {
        binding.error.errorLayout.visibility = View.VISIBLE
        binding.loader.loadingLayout.visibility = View.GONE
        binding.refresh.visibility = View.GONE
    }

    override fun onRefresh() {
        viewModel.getResults()
        binding.refresh.isRefreshing = false
    }
}