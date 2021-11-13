package com.example.venuesnearby.ui.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

// Use LinearLayout.VERTICAL or HORIZONTAL integers, or DividerItemDecoration.VERTICAL or HORIZONTAL
@BindingAdapter("divider")
fun addDividerItemDecoration(recyclerView: RecyclerView, orientation: Int) {
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, orientation))
}
