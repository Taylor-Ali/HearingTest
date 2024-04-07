package com.leaf.hearingtest.ui.hearing

import android.app.AlertDialog
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.leaf.hearingtest.R
import com.leaf.hearingtest.databinding.FragmentHearingTestBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HearingTestFragment : Fragment() {
    private val viewModel: HearingTestViewModel by viewModels()
    private lateinit var binding: FragmentHearingTestBinding
    val args: HearingTestFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hearing_test, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        hideLoading()
        viewModel.loadQuestion()

        binding.submitBtn.setOnClickListener {
            viewModel.onSubmitClicked()
            binding.secretCodeInput.editText?.text?.clear()
        }

        binding.exitBtn.setOnClickListener {
            findNavController().popBackStack(R.id.welcomeFragment, false)
        }
        viewModel.fetchUserDetails(args?.userModel)

        viewModel.setupAudioPlayer(requireContext(), MediaPlayer())

        setupObservers()

    }

    private fun setupObservers() {

        viewModel.questionLiveData.observe(viewLifecycleOwner) {
            viewModel.playAudio(it.noise, it.triplet.digitList)
        }

        viewModel.scoreLiveData.observe(viewLifecycleOwner) {
            binding.score.text = getString(R.string.score) + " ${it} "
        }

        viewModel.difficultyAnsweredLiveData.observe(viewLifecycleOwner) {
            binding.difficulty.text =
                getString(R.string.difficulty) + " ${it} " + getString(R.string.of_10)
        }
        viewModel.totalQuestionsAnsweredLiveData.observe(viewLifecycleOwner) {
            binding.questionIndex.text =
                getString(R.string.questions_answered_correctly) + " ${it} " + getString(R.string.of_10)
        }

        viewModel.showLoadingLiveData.observe(viewLifecycleOwner) {
            if (it)
                showLoading()
        }

        viewModel.showErrorLiveData.observe(viewLifecycleOwner) {
            if (it)
                showError()
        }

        viewModel.showAlertDialogLiveData.observe(viewLifecycleOwner) {
            createDialog(requireContext(), it)
        }
    }


    private fun showError() {
        binding.error.errorLayout.visibility = View.VISIBLE
        binding.loader.loadingLayout.visibility = View.GONE
        binding.hearingTestLayout.visibility = View.GONE
    }

    private fun showLoading() {
        binding.error.errorLayout.visibility = View.GONE
        binding.loader.loadingLayout.visibility = View.VISIBLE
        binding.hearingTestLayout.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.error.errorLayout.visibility = View.GONE
        binding.loader.loadingLayout.visibility = View.GONE
        binding.hearingTestLayout.visibility = View.VISIBLE
    }

    private fun createDialog(context: Context, data: DialogData) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(getString(R.string.dialog_title_congratulations))
        builder.setMessage(getString(R.string.message) +"${data.score}")
        builder.setPositiveButton(getString(R.string.dialog_button_results)) { dialog, which ->
            findNavController().popBackStack()
            findNavController().navigate(R.id.resultFragment)
        }
        builder.setNegativeButton(getString(R.string.dialog_button_cancel)) { dialog, which ->
            findNavController().popBackStack(R.id.welcomeFragment, false)
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.destroyAudioPlayer()
    }
}