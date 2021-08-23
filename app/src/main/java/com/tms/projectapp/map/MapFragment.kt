package com.tms.projectapp.map

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.Task
import com.google.maps.android.PolyUtil
import com.tms.projectapp.R
import com.tms.projectapp.databinding.FragmentMapBinding
import com.tms.projectapp.entities.DirectionResponses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapFragment : Fragment(), OnMapReadyCallback {

    private var binding: FragmentMapBinding? = null
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var start: LatLng
    private lateinit var end: LatLng

    private var myLocation: LatLng? = null
        set(value) {
            field = value
            Toast.makeText(requireContext(),"My location was updated", Toast.LENGTH_SHORT).show()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        end = LatLng(-6.1890511, 106.8251573)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                myLocation = LatLng(p0.lastLocation.latitude, p0.lastLocation.longitude)
            }
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    ACCESS_COARSE_LOCATION,
                    ACCESS_FINE_LOCATION,
                    ACCESS_NETWORK_STATE
                ), 2
            )
        } else {
            locationWizardry()
        }
        return binding?.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         val mapFragment =
             childFragmentManager.findFragmentById(R.id.fragment_map) as SupportMapFragment?
         mapFragment?.getMapAsync(this)
     }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        locationWizardry()
    }

    @SuppressLint("MissingPermission")
    private fun locationWizardry() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
         fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                myLocation = LatLng(location.latitude, location.longitude)
            }
        }
        start = myLocation!!
        createLocRequest()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(requireActivity(), 500)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        val markerFkip = MarkerOptions()
            .position(start)
        val markerMonas = MarkerOptions()
            .position(end)


        mMap.addMarker(markerFkip)
        mMap.addMarker(markerMonas)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(end, 11.6f))

        val from = start.latitude.toString() + "," + start.longitude.toString()
        val to = end.latitude.toString() + "," + end.longitude.toString()

        val apiServices = DirectionService.apiService()
        apiServices.getDirection(from, to, getString(R.string.google))
            .enqueue(object : Callback<DirectionResponses> {
                override fun onResponse(call: Call<DirectionResponses>, response: Response<DirectionResponses>) {
                    drawPolyline(response)
                    Log.d("JJJ", response.message())
                }

                override fun onFailure(call: Call<DirectionResponses>, t: Throwable) {
                    Log.e("Failure", t.localizedMessage)
                }
            })
    }

    private fun goToMe() {
        if (::mMap.isInitialized && myLocation != null) {
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(myLocation!!, 17f),
                object : GoogleMap.CancelableCallback {
                    override fun onFinish() {
                        val minsk = LatLng(53.902334, 27.561879)
                        mMap.addMarker(MarkerOptions().position(minsk).title("Hello from Minsk"))
                    }

                    override fun onCancel() = Unit

                })

        } else {
            Toast.makeText(requireContext(), "Not initialized", Toast.LENGTH_SHORT).show()
        }
    }

    private fun drawPolyline(response: Response<DirectionResponses>) {
        val shape = response.body()?.routes?.get(0)?.overviewPolyline?.points
        val polyline = PolylineOptions()
            .addAll(PolyUtil.decode(shape))
            .width(8f)
            .color(Color.RED)
        mMap.addPolyline(polyline)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}