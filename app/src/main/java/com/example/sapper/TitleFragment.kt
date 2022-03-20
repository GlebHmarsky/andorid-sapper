package com.example.sapper

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.sapper.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding = DataBindingUtil.inflate<FragmentTitleBinding>(
      inflater, R.layout.fragment_title, container, false
    )

    binding.buttonStart.setOnClickListener(){
      Navigation.findNavController(it).navigate(R.id.action_titleFragment_to_mainGame)
    }
    return binding.root
  }
}