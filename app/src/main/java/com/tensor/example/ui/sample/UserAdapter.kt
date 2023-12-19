/*
* Copyright 2023 TensorIotExample
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
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
