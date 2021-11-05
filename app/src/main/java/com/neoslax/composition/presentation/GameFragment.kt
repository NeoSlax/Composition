package com.neoslax.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.neoslax.composition.R
import com.neoslax.composition.databinding.FragmentGameBinding
import com.neoslax.composition.domain.entity.GameResult
import com.neoslax.composition.domain.entity.GameSettings
import com.neoslax.composition.domain.entity.Level

class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModelFactory by lazy {
        GameViewModelFactory(args.level, requireActivity().application)
    }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }
    private val tvOptionsList by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setupObservers()


    }

    private fun setupObservers() {
        with(viewModel) {
            timer.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }

            question.observe(viewLifecycleOwner) {
                for (i in 0 until tvOptionsList.size) {
                    tvOptionsList[i].text = it.option[i].toString()
                }
                binding.tvSum.text = it.sum.toString()
                binding.tvLeftNumber.text = it.visibleNumber.toString()


            }
            progressAnswers.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.text = it
            }
            percentOfRightAnswers.observe(viewLifecycleOwner) {
                binding.progressBar.setProgress(it, true)
            }
            minPercent.observe(viewLifecycleOwner) {
                binding.progressBar.secondaryProgress = it
            }

            enoughCount.observe(viewLifecycleOwner) {

                val color = getStateProgressColor(it)
                binding.tvAnswersProgress.setTextColor(color)
            }
            enoughPercent.observe(viewLifecycleOwner) {
                val color = getStateProgressColor(it)
                binding.progressBar.progressTintList = ColorStateList.valueOf(color)
            }

            gameResult.observe(viewLifecycleOwner) {
                launchGameFinishFragment(it)
            }

        }
    }

    private fun getStateProgressColor(isEnough: Boolean): Int {
        val colorId = if (isEnough) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireActivity(), colorId)
    }

    private fun setupListeners() {
        for (tvOption in tvOptionsList) {
            tvOption.setOnClickListener {
                viewModel.takeAnswer(tvOption.text.toString().toInt())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishFragment(gameResult: GameResult) {

        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameResult
            )
        )
    }


}