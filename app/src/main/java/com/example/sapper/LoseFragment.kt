package com.example.sapper

import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.sapper.databinding.FragmentLoseBinding


class LoseFragment : Fragment() {
  private lateinit var binding: FragmentLoseBinding
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_lose, container, false)
    val gameContentFragmentArgs by navArgs<LoseFragmentArgs>()

    binding.timeText.text =
      DateUtils.formatElapsedTime(gameContentFragmentArgs.time.toLong()).toString()
    binding.flagText.text = gameContentFragmentArgs.flagsSetted.toString()

    binding.buttonMain.setOnClickListener {
      Navigation.findNavController(it).navigate(R.id.action_loseFragment_to_titleFragment)
    }

    return binding.root
  }


}