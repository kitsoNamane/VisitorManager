package com.abstractclass.visitormanager

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.facebook.stetho.Stetho
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val toolbar : androidx.appcompat.widget.Toolbar?  = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar!!)
        val navView : BottomNavigationView? = findViewById(R.id.bottom_nav)
        navView!!.setOnNavigationItemReselectedListener { item: MenuItem? -> }
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