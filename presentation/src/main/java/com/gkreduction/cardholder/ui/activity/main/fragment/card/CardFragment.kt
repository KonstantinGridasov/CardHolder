package com.gkreduction.cardholder.ui.activity.main.fragment.card

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.navigation.findNavController
import com.gkreduction.cardholder.R
import com.gkreduction.cardholder.databinding.FragmentCardBinding
import com.gkreduction.cardholder.ui.activity.main.MainActivity
import com.gkreduction.cardholder.ui.base.BaseFragment

class CardFragment : BaseFragment<CardViewModel>(
    R.layout.fragment_card,
    CardViewModel::class.java
) {

    private var isRevert = false
    var id = 0L

    override fun onStart() {
        super.onStart()
        val args = CardFragmentArgs.fromBundle(requireArguments())
        if (args.cardId != 0L) {
            id = args.cardId
            viewModel?.getCards(id)
        } else
            view?.findNavController()?.navigateUp()

        (binding as FragmentCardBinding).viewmodel = viewModel
        (binding as FragmentCardBinding).isRevert = isRevert
        initListener()

    }

    private fun initListener() {
        if (activity is MainActivity) {
            (activity as MainActivity).getToolbar().setOnImageClickListener { navigateToEdit() }
        }


        if (activity is MainActivity) {
            (activity as MainActivity).getButton()
                .setOnClickListener {
                    animation((binding as FragmentCardBinding).itemCard)
                    (binding as FragmentCardBinding).isRevert = !isRevert
                    isRevert = !isRevert
                }
        }

        activity?.let {
            viewModel?.isSecondCode?.observe(it) { exist ->
                if (activity is MainActivity)
                    (activity as MainActivity).setVisibilityButton(exist)
            }
        }
    }


    private fun navigateToEdit() {
        view?.findNavController()?.navigate(CardFragmentDirections.toEdit(id))
    }

    private fun animation(view: View) {
        val rotate = (binding as FragmentCardBinding).cardRotate
        val animatorRotate = ObjectAnimator.ofFloat(rotate, View.ROTATION_Y, 0f, 180f)
            .setDuration(500)

        animatorRotate.apply {
            doOnStart {
                view.visibility = View.INVISIBLE
                rotate.visibility = View.VISIBLE
            }
            doOnEnd {
                rotate.visibility = View.GONE
                view.visibility = View.VISIBLE
            }
        }
        animatorRotate.start()
    }
}