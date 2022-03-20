package com.example.sapper

import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.sapper.databinding.FragmentMainGameBinding
import com.example.sapper.sapper.field.SapperField
import java.util.*


class MainGame : Fragment() {
  private lateinit var binding: FragmentMainGameBinding
  private lateinit var sapperField: SapperField
  private var width = 0
  private var height = 0
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_game, container, false)


    return binding.root
  }

  override fun onStart() {
    super.onStart()

    getSizes()
    val cnt = context
    if(cnt !=null) {
      this.sapperField = SapperField(cnt, width,5,10)
    }
    addAllButtons()
  }

  private fun getSizes() {
    val display: Display = requireActivity().windowManager.defaultDisplay
    width = display.width
    height = display.height
  }

  private fun addAllButtons() {
    val rlMain = binding.mainRl
    sapperField.addAllButtonsToLayout(rlMain)
  }
}