package com.gkreduction.cardholder.ui.widjet.carousel

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/***
 * https://github.com/sparrow007/CarouselRecyclerview
 *
 * Copyright (C) 2021 Sparrow007 (Ankit kumar)
 * */


class CarouselRecyclerview(context: Context, attributeSet: AttributeSet) :
    RecyclerView(context, attributeSet) {

    /** Create layout manager builder so that we can easily add more methods to it */
    private var carouselLayoutManager = CarouselLayoutManager()

    private var layoutManagerState: Parcelable? = null

    companion object {
        private const val SAVE_SUPER_STATE = "super-state"
        private const val SAVE_LAYOUT_MANAGER = "layout-manager-state"
    }

    /**
     * Initialize the layout manager and also enable the childDrawingOrder
     */
    init {
        layoutManager = carouselLayoutManager
        isChildrenDrawingOrderEnabled = true
    }


    /**
     * Get the layout manager instance
     * @return carouselLayoutManager
     */
    private fun getCarouselLayoutManager(): CarouselLayoutManager {
        return layoutManager as CarouselLayoutManager
    }

    /**
     * provides the drawing of the child view in the layout manager, calculate the priority of the views
     * depends on the position in the layout manager
     * @param childCount currently number of visible view
     * @param i current position of view
     * @return order of the view
     */
    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        val center: Int = getCarouselLayoutManager().centerPosition()

        // Get the actual position of the i-th child view in RecyclerView
        val actualPos: Int = getCarouselLayoutManager().getChildActualPos(i)
        var order: Int = i

        if (actualPos != Int.MIN_VALUE) {

            // The number of intervals from the middle item
            val dist = actualPos - center
            // [< 0] indicates that the item is located to the left of the middle item and can be drawn in order
            // [< 0] indicates that the item is located to the left of the middle item and can be drawn in order
            order = if (dist < 0) {
                i
            } else {
                //[>= 0] It means that the item is located to the right
                // of the middle item, and the order needs to be reversed.
                childCount - 1 - dist
            }
        }

        if (order < 0) order = 0 else if (order > childCount - 1) order = childCount - 1

        return order
    }


//    fun setItemSelectListener(listener: CarouselLayoutManager.OnSelected) {
//        getCarouselLayoutManager().setOnSelectedListener(listener)
//    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(SAVE_SUPER_STATE, super.onSaveInstanceState())

        bundle.putParcelable(SAVE_LAYOUT_MANAGER, getCarouselLayoutManager().onSaveInstanceState())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            layoutManagerState = state.getParcelable(SAVE_LAYOUT_MANAGER)
            super.onRestoreInstanceState(state.getParcelable(SAVE_SUPER_STATE))

        } else super.onRestoreInstanceState(state)

    }

    /**
     * Get selected position from the layout manager
     * @return center view of the layout manager
     */
    fun getSelectedPosition() = getCarouselLayoutManager().getSelectedPosition()

    private fun restorePosition() {
        if (layoutManagerState != null) {
            getCarouselLayoutManager().onRestoreInstanceState(layoutManagerState)
            layoutManagerState = null
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        restorePosition()
    }
}