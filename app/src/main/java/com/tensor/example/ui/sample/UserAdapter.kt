package com.tensor.example.ui.sample

import android.view.View
import com.tensor.example.R
import com.tensor.example.ui.base.BaseRecyclerAdapter
import com.tensor.example.data.remote.response.ApiUser

class UserAdapter : BaseRecyclerAdapter<ApiUser>() {
    override fun getLayoutIdForType(viewType: Int): Int = if (viewType == ITEM_TYPE_NORMAL)
        R.layout.item_user
    else
        R.layout.layout_loader

    override fun onItemClick(view: View?, position: Int) { /* no-op */ }

    override fun areItemsSame(firstItem: ApiUser, secondItem: ApiUser): Boolean = firstItem == secondItem

    override fun isLastItemLoading(): Boolean = arrayList.lastOrNull()?.login?.uuid.isNullOrBlank()

    override fun isItemLoading(index: Int): Boolean = arrayList[index].login.uuid.isBlank()

    override fun getLoaderItem(): ApiUser = ApiUser()
}
