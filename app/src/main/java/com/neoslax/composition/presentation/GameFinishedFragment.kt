package com.neoslax.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.neoslax.composition.R
import com.neoslax.composition.databinding.FragmentGameBinding
import com.neoslax.composition.databinding.FragmentGameFinishedBinding
import com.neoslax.composition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {

    private val args: GameFinishedFragmentArgs by navArgs()

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        setResultOfGame()
    }

    private fun setResultOfGame() {
        with(binding) {
            val gameSettings = gameResult.gameSettings
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_score),
                gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                gameResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                gameResult.percentOfRightAnswers.toString()
            )
            emojiResult.setImageResource(chooseSmile(gameResult.winner))
        }

    }

    private fun setupListeners() {

        binding.buttonRetry.setOnClickListener {
            retryGame()
        }
    }

    private fun chooseSmile(isGoodResult: Boolean): Int {
        return if (isGoodResult) {
            R.drawable.ic_smile
        } else {
            R.drawable.ic_sad
        }
    }

    private fun parseArgs() {
        gameResult = args.gameResult
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}