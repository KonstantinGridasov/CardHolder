package com.gkreduction.cardholder.ui.widjet

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import com.gkreduction.cardholder.R

@SuppressLint("CustomViewStyleable")
class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var image: ImageView? = null
    private var onItemClickListener = {}


    init {
        LayoutInflater.from(context).inflate(R.layout.toolbar_view, this)
        image = findViewById(R.id.image_toolbar)
        image?.setOnClickListener { onItemClickListener.invoke() }

    }


    fun setImageByDestination(destinationId: Int) {
        image?.visibility = when (destinationId) {
            R.id.infoFragment -> GONE
            else -> VISIBLE
        }
        when (destinationId) {
            R.id.homeFragment -> image?.setImageResource(R.drawable.ic_info)
            R.id.cardFragment -> image?.setImageResource(R.drawable.ic_pencil)

        }
    }


    fun setOnImageClickListener(function: () -> Unit) {
        this.onItemClickListener = function
    }


}