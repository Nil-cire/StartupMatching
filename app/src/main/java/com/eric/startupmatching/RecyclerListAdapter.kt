package com.eric.startupmatching

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.databinding.ItemProjectEditTaskBinding
import java.util.*
import kotlin.collections.ArrayList

class RecyclerListAdapter(context: Context, dragStartListener: OnStartDragListener) :
    RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>(), ItemTouchHelperAdapter {

    private val mItems: MutableList<String> = ArrayList()
    private val mDragStartListener: OnStartDragListener

    class ItemViewHolder(val binding: ItemProjectEditTaskBinding) : RecyclerView.ViewHolder(binding.root),
        ItemTouchHelperViewHolder {
        val textView: TextView
        val handleView: ImageView
        override fun onItemSelected() {
            binding.root.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            binding.root.setBackgroundColor(0)
        }

        init {
            textView = itemView.findViewById(R.id.text)
            handleView = itemView.findViewById(R.id.handle) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectEditTaskBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.textView.text = mItems[position]

        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder)
                }
                return false
            }
        })
    }

    override fun onItemDismiss(position: Int) {
        mItems.removeAt(position)
        Log.d("listOnItemReMoved", mItems.toString())
        notifyItemRemoved(position)
    }

//    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
//        Collections.swap(mItems, fromPosition, toPosition)
//        notifyItemMoved(fromPosition, toPosition)
//        return true
//    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mItems, i, i + 1)
                Log.d("listOnItemMoved", mItems.toString())
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mItems, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    init {
        mDragStartListener = dragStartListener
        mItems.addAll((context.resources.getStringArray(R.array.dummy_items)))
    }
}