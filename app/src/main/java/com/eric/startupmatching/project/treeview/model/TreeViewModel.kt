package com.eric.startupmatching.project.treeview.model

open class TreeViewModel {
    var itemType: Int = 0
    var treeDepth: Int = 0
    var expand: Boolean = false
    var children: ArrayList<TreeViewModel>? = null

    companion object {
        const val ITEM_TYPE_PARENT = 0

        const val ITEM_TYPE_CHILD = 1

        fun getChildCountForHide(item: TreeViewModel): Int {
            val list = ArrayList<TreeViewModel>()
            printChild(item, list)
            return list.size - 1
        }

        private fun printChild(item: TreeViewModel, list: ArrayList<TreeViewModel>) {
            list.add(item)
            if (item.expand) {
                item.children?.forEach {
                    printChild(it, list)
                }
            }
        }
    }
}