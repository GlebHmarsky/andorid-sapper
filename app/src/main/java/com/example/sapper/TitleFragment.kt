package com.example.sapper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.sapper.databinding.FragmentTitleBinding
import com.example.sapper.enums.LevelDifficulty


class TitleFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
      inflater, R.layout.fragment_title, container, false
    )

    binding.buttonEasy.setOnClickListener {
      redirectToGame(LevelDifficulty.EASY.size, LevelDifficulty.EASY.bombs)
    }
    binding.buttonMedium.setOnClickListener {
      redirectToGame(LevelDifficulty.MEDIUM.size, LevelDifficulty.MEDIUM.bombs)
    }
    binding.buttonHard.setOnClickListener {
      redirectToGame(LevelDifficulty.HARD.size, LevelDifficulty.HARD.bombs)
    }
    return binding.root
  }

  private fun redirectToGame(size: Int, bombs: Int) {
    findNavController().navigate(TitleFragmentDirections.actionTitleFragmentToMainGame(size, bombs))
  }
}