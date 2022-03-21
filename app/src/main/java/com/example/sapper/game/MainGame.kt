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


class MainGame : Fragment() {
  private lateinit var binding: FragmentMainGameBinding


  private lateinit var viewModel: SapperViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_game, container, false)

    val application = requireNotNull(this.activity).application

    getScreenSizes()

    val viewModelFactory = SapperViewModelFactory(requireContext(), binding.mainRl, application)
    viewModel = ViewModelProvider(this, viewModelFactory).get(SapperViewModel::class.java)

    return binding.root
  }

  private fun getScreenSizes() {
    val display: Display = requireActivity().windowManager.defaultDisplay
    viewModel.width = display.width
    viewModel.height = display.height
  }

//  fun addAllButtons(layout:RelativeLayout) {
//    sapperField.addAllButtonsToLayout(layout)
//  }

  // TODO: Change it (Separate logic?)
  private fun addButtonToView(
    countHorizontal: Int,
    countVertical: Int,
    id: Int,
    blockSize: Int
  ) {
    val view = binding.mainRl

    val pseudoMargin = 15
    val buttonSize = blockSize - pseudoMargin

    val buttonDynamic = Button(context)
    buttonDynamic.id = id

    buttonDynamic.setBackgroundColor(Color.LTGRAY)
    buttonDynamic.layoutParams = RelativeLayout.LayoutParams(
      RelativeLayout.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )

    val param = buttonDynamic.layoutParams as ViewGroup.MarginLayoutParams
    param.setMargins(
      ((buttonSize + pseudoMargin) * countVertical + pseudoMargin / 2),
      ((buttonSize + pseudoMargin) * countHorizontal + pseudoMargin / 2),
      0,
      0
    )
    buttonDynamic.layoutParams.width = buttonSize
    buttonDynamic.layoutParams.height = buttonSize
    buttonDynamic.height = buttonSize


    view.addView(buttonDynamic)

  }
}