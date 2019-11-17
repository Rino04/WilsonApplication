package com.example.wilsonapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.File

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener,
    OnMapReadyCallback {
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        if (id == R.id.share) {
            val api = applicationContext.applicationInfo
            val apk: String = api.sourceDir
            val intent = Intent.ACTION_SEND.apply {
                intent.setType("/text")
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(apk)))
            }
            startActivity(Intent.createChooser(Intent(), "share"))
        }
        return true
    }
    private fun zoomingLocation(): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(LatLng(-1.281229, 36.821402), 16f)
    }

    private lateinit var mMap: GoogleMap
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        // Add a marker in Sydney and move the camera
        val nairobi = LatLng(-1.281229, 36.821402)
        mMap.addMarker(MarkerOptions().position(nairobi).title("Marker in Nairobi Kenya"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nairobi))
        mMap.setMaxZoomPreference(16.0F)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_corporate, R.id.nav_shuttle,
                R.id.nav_payment, R.id.nav_ride_history, R.id.nav_wallent
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

//        var myMap = new google.maps.Map(document.getElementById('map-canvas'),{ zoom : 16 });
//        myMap.setZoom(12);
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id: Int = item.getItemId()
        if (id == R.id.share) {
            val api = applicationContext.applicationInfo
            val apk: String = api.sourceDir
            val intent = Intent.ACTION_SEND.apply {
                intent.setType("application/vnd.android.package-archive")
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(File(apk)))
            }
            startActivity(Intent.createChooser(Intent(), "Share:"))
        }
        return true
    }

}
