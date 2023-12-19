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
package com.tensor.example.ui.profile

import INTENT_USER
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tensor.example.R
import com.tensor.example.databinding.ActivityProfileBinding
import com.tensor.example.ui.base.BaseAppCompatActivity
import com.tensor.example.ui.home.HomeActivity
import com.tensor.example.ui.login.LoginActivity
import com.tensor.example.ui.sample.SampleActivity
import com.tensor.example.utils.extension.launchActivity
import com.tensor.example.utils.extension.launchActivityAndFinishAll
import com.tensor.example.utils.extension.observe
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileActivity : BaseAppCompatActivity<ActivityProfileBinding, ProfileViewModel>(),
    View.OnClickListener {

    private var user: FirebaseUser? = null

    override val viewModel: ProfileViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_profile

    override fun initialize() {
        super.initialize()
        binding.clickHandler = this
        if (intent.extras?.containsKey(INTENT_USER) == true) {
            user = intent.extras?.getParcelable(INTENT_USER)
            binding.user = user
            user?.let { viewModel.getSingleUser(it.uid) }

        }
    }
    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.getuserRes.observe(this@ProfileActivity , Observer {
            Timber.tag("TAG").e("ProfileActivityProfileActivity: %s", it.toString())
        })
        }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLogout -> {
                Firebase.auth.signOut()
                launchActivityAndFinishAll<LoginActivity>()
            }

            R.id.btn_home -> {
                launchActivity<HomeActivity>()
            }

            R.id.btnUser -> {
                launchActivity<SampleActivity>()
            }
        }
    }
}
