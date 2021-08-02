package com.example.editor.UI

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.editor.R
import com.example.editor.databinding.FragmentMapsBinding
import com.example.editor.databinding.UsersListFragmentBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    lateinit var viewBind : FragmentMapsBinding
    var myMarker : Marker?= null
    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
        googleMap.uiSettings.isMapToolbarEnabled = true
        googleMap.uiSettings.isCompassEnabled = true
        googleMap.uiSettings.isZoomControlsEnabled = true

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val coim = LatLng(11.00945, 76.95861)
        viewBind.lat.text = "11.00945"
        viewBind.lng.text = "76.95861"
        googleMap.addMarker(MarkerOptions().position(coim).title("Marker in Coimbatore"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coim))
        googleMap.setOnMapClickListener {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            myMarker = googleMap.addMarker(MarkerOptions()
                .position(it)
                .title("My Location")
                .snippet("Lat : ${it.latitude} Long : ${it.longitude}"))

            viewBind.lat.text = it.latitude.toString()
            viewBind.lng.text = it.longitude.toString()
        }
    }
    val MEDIA_REQUEST_CODE = 101
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBind = FragmentMapsBinding.inflate(layoutInflater)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        viewBind.floatingActionButton.setOnClickListener {
            dispatchTakeVideoIntent()
        }

        viewBind.videoView.setOnCompletionListener { mp ->
            mp?.start()
            mp?.isLooping
        }

        viewBind.videoView.setOnClickListener {
            if (viewBind.videoView.isPlaying) {
                viewBind.videoView.pause()
            } else {
                viewBind.videoView.start()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MEDIA_REQUEST_CODE && resultCode == RESULT_OK) {
            val videoUri: Uri? = data?.data
            val mediaController = MediaController(requireContext())
            mediaController.setAnchorView(viewBind.videoView)
            viewBind.videoView.setVideoURI(videoUri)
            viewBind.videoView.requestFocus()
            viewBind.videoView.start()
        }
    }

    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(requireActivity().packageManager)?.also {
                startActivityForResult(takeVideoIntent, MEDIA_REQUEST_CODE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewBind.videoView.stopPlayback()

    }
}