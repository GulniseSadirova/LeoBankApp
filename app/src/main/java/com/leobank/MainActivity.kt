package com.leobank

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.leobank.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.numberAddFragment -> showBottomNavigation(false)
                R.id.smsAddFragment -> showBottomNavigation(false)
                R.id.pinCodeFragment -> showBottomNavigation(false)
                R.id.thansForSignUpFragment -> showBottomNavigation(false)
                R.id.emailAddFragment -> showBottomNavigation(false)
                R.id.detailedCardFragment -> showBottomNavigation(false)
                R.id.balanceIncreaseFragment -> showBottomNavigation(false)
                R.id.transferFragment -> showBottomNavigation(false)
                R.id.sendToCardFragment -> showBottomNavigation(false)
                R.id.paymentFragment -> showBottomNavigation(false)
                R.id.anotherCardIncreaseFragment -> showBottomNavigation(false)
                else -> showBottomNavigation(true)
            }
        }
    }

    private fun showBottomNavigation(show: Boolean) {
        if (show) {
            binding.bottomNav.visibility = View.VISIBLE
        } else {
            binding.bottomNav.visibility = View.GONE
        }
    }
}
