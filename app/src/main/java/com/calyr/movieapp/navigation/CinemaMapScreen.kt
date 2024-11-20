package com.calyr.movieapp.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CinemaMap() {
    Scaffold {
            paddingValues -> CinemaMapScreen(modifier= Modifier.padding(paddingValues) )
    }
}


@Composable
fun CinemaMapScreen(modifier: Modifier) {
    val ucb = LatLng(-17.371636431521956, -66.14369566112977)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(ucb, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = ucb),
            title = "UCB",
            snippet = "Marker in UCB"
        )
    }

}