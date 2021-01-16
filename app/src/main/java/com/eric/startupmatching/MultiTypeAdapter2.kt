package com.eric.startupmatching

import com.drakeet.multitype.MultiTypeAdapter
import java.util.*

class MultiTypeAdapter2(dragStartListener: OnStartDragListener):
    MultiTypeAdapter(), ItemTouchHelperAdapter {

    private val mDragStartListener: OnStartDragListener

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        items.drop(position)
        notifyItemRemoved(position)
    }

    init {
        mDragStartListener = dragStartListener
    }
}

