package com.eric.startupmatching

import android.animation.ObjectAnimator
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.project.treeview.model.TreeViewModel

class TreeViewUtil {
    companion object {

        fun rotationExpandIcon(imgView: View, startAngle: Float, endAngle: Float) {
            val animator = ObjectAnimator.ofFloat(imgView, "rotation", startAngle, endAngle)
            animator.duration = 300
            animator.start()
        }


        fun generateExpandAndHideListener(): OnExpandAndHideListener {
            return object : OnExpandAndHideListener {
                override fun onExpand(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, adapterItems: ArrayList<TreeViewModel>, clickedItem: TreeViewModel, clickedItemPosition: Int) {
                    val isBadData = clickedItemPosition == RecyclerView.NO_POSITION || clickedItem.children?.isNotEmpty() != true
                    if (isBadData) {
                        return
                    }
                    val childStartPosition = clickedItemPosition + 1
                    adapterItems.addAll(childStartPosition, clickedItem.children!!)
                    adapter.notifyItemRangeInserted(childStartPosition, clickedItem.children!!.size)
                }

                override fun onHide(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, adapterItems: ArrayList<TreeViewModel>, clickedItem: TreeViewModel, clickedItemPosition: Int) {
                    val isBadData = clickedItemPosition == RecyclerView.NO_POSITION || clickedItem.children?.isNotEmpty() != true
                    if (isBadData) {
                        return
                    }
                    val childStartPosition = clickedItemPosition + 1
                    val childCount = TreeViewModel.getChildCountForHide(clickedItem)
                    for (i in 0 until childCount) {
                        adapterItems.removeAt(childStartPosition)
                    }
                    adapter.notifyItemRangeRemoved(childStartPosition, childCount)
                }
            }
        }
    }
}