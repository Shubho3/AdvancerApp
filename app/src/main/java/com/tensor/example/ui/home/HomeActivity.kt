package com.tensor.example.ui.home

import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tensor.example.R
import com.tensor.example.databinding.ActivityHomeBinding
import com.tensor.example.databinding.ActivityProfileBinding
import com.tensor.example.ui.base.BaseAppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseAppCompatActivity<ActivityHomeBinding, HomeViewModel>(),
    View.OnClickListener {
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var navController: NavController

    override fun getLayoutResId() = R.layout.activity_home
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.home -> {

            }
        }
    }

    override fun initialize() {
        super.initialize()
        binding.clickHandler = this
        val host: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment? ?: return
        navController = host.navController

    }

}