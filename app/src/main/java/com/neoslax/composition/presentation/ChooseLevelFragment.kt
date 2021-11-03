package com.neoslax.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neoslax.composition.R
import com.neoslax.composition.databinding.FragmentChooseLevelBinding
import com.neoslax.composition.databinding.FragmentGameFinishedBinding
import com.neoslax.composition.domain.entity.Level


class ChooseLevelFragment : Fragment() {

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameInMode(Level.TEST)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameInMode(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameInMode(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameInMode(Level.HARD)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameInMode(level: Level) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFragment.newInstance(level))
            .addToBackStack(GameFragment.NAME)
            .commit()
    }

    companion object {

        const val NAME = "ChooseLevelFragment"

        fun getNewInstance(): ChooseLevelFragment {
            return ChooseLevelFragment()
        }

    }
}