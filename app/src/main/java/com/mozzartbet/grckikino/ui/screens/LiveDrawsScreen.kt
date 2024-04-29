package com.mozzartbet.grckikino.ui.screens

import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mozzartbet.grckikino.BuildConfig
import com.mozzartbet.grckikino.MainViewModel
import com.mozzartbet.grckikino.ui.components.NavigationBar

@Composable
fun LiveDrawsScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val networkConnection by mainViewModel.networkConnection.collectAsStateWithLifecycle()
    var refresh by rememberSaveable { mutableStateOf(false) }

    val backgroundColor = MaterialTheme.colors.background
    val url = BuildConfig.LIVE_DRAWS_URL

    Column {
        AndroidView(
            factory = { context ->
                WebView.setWebContentsDebuggingEnabled(true)
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    setBackgroundColor(backgroundColor.hashCode())
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadsImagesAutomatically = true
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    settings.mediaPlaybackRequiresUserGesture = false
                    settings.safeBrowsingEnabled = false
                    loadUrl(url)
                }
            },
            update = {
                if (refresh) {
                    refresh = false
                    it.loadUrl(url)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )

        NavigationBar(navController)

        LaunchedEffect(networkConnection) {
            if (networkConnection) {
                refresh = true
            }
        }
    }
}

@Preview
@Composable
private fun LiveDrawsScreenPreview() {
    LiveDrawsScreen(viewModel(), rememberNavController())
}