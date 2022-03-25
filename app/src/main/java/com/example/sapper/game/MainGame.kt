package com.example.sapper.game

import android.graphics.Color
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sapper.R
import com.example.sapper.databinding.FragmentMainGameBinding
import com.example.sapper.sapper.field.SapperCell


class MainGame : Fragment() {
  private lateinit var binding: FragmentMainGameBinding
  private lateinit var viewModel: SapperViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_game, container, false)

    val application = requireNotNull(this.activity).application

    val width = getScreenSizes()
    val viewModelFactory = SapperViewModelFactory(width, application)
    viewModel = ViewModelProvider(this, viewModelFactory)[SapperViewModel::class.java]
    addAllButtons()

    binding.switch1.isChecked = viewModel.modeOpen.value!!
    binding.switch1.setOnClickListener() {
      viewModel.modeOpen.value = !viewModel.modeOpen.value!!
    }

    return binding.root
  }

  private fun getScreenSizes(): Int {
    val display: Display = requireActivity().windowManager.defaultDisplay
    return display.width
//    viewModel.height = display.height
  }

  private fun addAllButtons() {
    val view = binding.mainRl
    view.removeAllViews()
    val field = viewModel.sapperField.value?.field
    if (field != null) {
      for (i in field.indices) {
        for (g in field[i].indices) {
          val cell = field[i][g]
          addButtonToView(cell, i, g)
        }
      }
    }
  }

  fun basicHandlerButton(i: Int, g: Int) {
    if (viewModel.isGameShouldInit) {
      viewModel.sapperField.value!!.startGame(i, g)
      viewModel.isGameShouldInit = false
    }
  }

  private fun addButtonToView(
    sapperCell: SapperCell,
    i: Int,
    g: Int
  ) {
    val view = binding.mainRl

    val buttonDynamic = Button(context)
    buttonDynamic.id = sapperCell.id

    buttonDynamic.setBackgroundColor(Color.LTGRAY)
    buttonDynamic.layoutParams = RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )

    val param = buttonDynamic.layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(
      sapperCell.topMargin,
      sapperCell.leftMargin,
      0,
      0
    )
    buttonDynamic.textSize = buttonDynamic.layoutParams.width * 0.17F
    buttonDynamic.layoutParams.width = sapperCell.cellSize
    buttonDynamic.layoutParams.height = sapperCell.cellSize
//  buttonDynamic.height = sapperCell.cellSize

    udpView(buttonDynamic, sapperCell,requireContext())

    viewModel.modeOpen.observe(viewLifecycleOwner) { newModeOpen ->
      setListener(
        buttonDynamic, sapperCell, newModeOpen
      ) {
        basicHandlerButton(i, g)
        if (newModeOpen && !sapperCell.isBomb) {
          viewModel.sapperField.value?.openNeighborCells(i, g);
        }
        addAllButtons()
      }
//      buttonDynamic.isClickable = !((!newModeOpen && sapperCell.isFlagged) || sapperCell.isOpen)
      if (newModeOpen) {
        buttonDynamic.isClickable = !sapperCell.isOpen && !sapperCell.isFlagged
      } else {
        buttonDynamic.isClickable = true
      }


    }

    view.addView(buttonDynamic)
  }
}