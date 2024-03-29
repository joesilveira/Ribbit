package com.ribbit.main.Screens

import android.content.ClipData
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.ribbit.main.R
import com.ribbit.main.Screens.ui.gallery.RecordingsFragment
import com.ribbit.main.Screens.ui.home.HomeFragment
import com.ribbit.main.Screens.ui.settings.SettingsFragment
import com.ribbit.main.dbConnections.GetUserData


class HomeScreen : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_recordings,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener {
            displaySelectedScreen(it.itemId)
            var toolBarTitle = findViewById<Toolbar>(R.id.toolbar)
            toolBarTitle.title = it.title
            true
        }
    }

    fun displaySelectedScreen(itemId : Int){
        var fragment : Fragment? = null

        when (itemId) {
            R.id.nav_home -> fragment = HomeFragment()
            R.id.nav_recordings -> fragment = RecordingsFragment()
            R.id.action_settings -> println("clicked")
        }

        if(fragment != null){
            var ft : FragmentTransaction = supportFragmentManager.beginTransaction()
            var btn : Button = findViewById<Button>(R.id.recordButton)

            btn.isVisible = false
            ft.replace(R.id.nav_host_fragment,fragment)
            ft.commit()
        }

        var drawLayout : DrawerLayout = findViewById(R.id.drawer_layout)
        drawLayout.closeDrawer(GravityCompat.START)

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.right_home_menu, menu)


        var emailPassed = intent!!.extras!!.getString("userEmail")

        var appBarUserName = findViewById<TextView>(R.id.userName)
        var appBarEmail = findViewById<TextView>(R.id.userEmail)

        if(emailPassed != null){
            appBarEmail.text = GetUserData().getUserEmail(emailPassed)
            appBarUserName.text = GetUserData().getUserFullName(emailPassed)
        }



        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)

        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
