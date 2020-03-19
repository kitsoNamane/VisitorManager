package com.abstractclass.visitormanager

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val toolbar : androidx.appcompat.widget.Toolbar?  = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar!!)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val navView : BottomNavigationView? = findViewById(R.id.bottom_nav)
        navView!!.setOnNavigationItemReselectedListener { _: MenuItem? -> }

        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.signInFragment -> {
                    navView.visibility = View.GONE
                    toolbar?.navigationIcon = null
                }
                R.id.signOutFragment -> {
                    navView.visibility = View.GONE
                    toolbar?.navigationIcon = null
                }
                else -> {
                    navView.visibility = View.VISIBLE
                }
            }

        }
        NavigationUI.setupActionBarWithNavController(this, navController!!)
        NavigationUI.setupWithNavController(navView, navController!!);
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController!!, null as DrawerLayout?)
    }

    companion object {
        var navController: NavController? = null
    }
}