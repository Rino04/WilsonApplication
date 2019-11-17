package com.example.wilsonapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.pusher.client.ChannelData
import com.pusher.client.Pusher
import com.pusher.client.PusherOptions
import com.pusher.client.channel.Channel
import com.pusher.client.channel.SubscriptionEventListener
import kotlinx.android.synthetic.main.activity_drivers.*
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


class DriversActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var markerOptions: MarkerOptions
    private lateinit var marker: Marker
    private lateinit var cameraPosition: CameraPosition
    var defaultLongitude = 36.821402
    var defaultLatitude = -1.281229
    lateinit var pusher: Pusher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drivers)
        markerOptions = MarkerOptions()
        val latLng = LatLng(defaultLatitude, defaultLongitude)
        markerOptions.position(latLng)
        cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17f).build()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        setupPusher()

        stimulateButton.setOnClickListener {
            callServerToSimulate()
        }
    }

    private fun setupPusher() {
        val options = PusherOptions()
        options.setCluster("mt1")
        pusher = Pusher("d4350df67a46f0641a39", options)

        val channel = pusher.subscribe("my-channel")

//        channel.bind("new-values") { channelName, eventName, data ->
//            val jsonObject = JSONObject(data)
//            val lat:Double = jsonObject.getString("latitude").toDouble()
//            val lon:Double = jsonObject.getString("longitude").toDouble()
//
//            runOnUiThread {
//                val newLatLng = LatLng(lat, lon)
//                marker.position = newLatLng
//                cameraPosition = CameraPosition.Builder()
//                    .target(newLatLng)
//                    .zoom(17f).build()
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        pusher.connect()
    }

    override fun onPause() {
        super.onPause()
        pusher.disconnect()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap?) {
        this.mMap = googleMap!!
        marker = mMap.addMarker(markerOptions)
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    fun getRetrofitObject(): ApiInterface {
        val httpClient = OkHttpClient.Builder()
        val builder = Retrofit.Builder()
            .baseUrl("http://10.0.3.2:4000/")
            .addConverterFactory(ScalarsConverterFactory.create())

        val retrofit = builder
            .client(httpClient.build())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }
    private fun callServerToSimulate() {
        val jsonObject = JSONObject()
        jsonObject.put("latitude",defaultLatitude)
        jsonObject.put("longitude",defaultLongitude)

        val body = RequestBody.create(
            MediaType.parse("application/json"),
            jsonObject.toString()
        )

        getRetrofitObject().sendCoordinates(body).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.d("TAG",response!!.body().toString())
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.d("TAG",t!!.message)
            }
        })
    }

}




