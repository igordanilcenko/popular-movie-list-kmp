package com.ihardanilchanka.sampleappkmp.android.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ihardanilchanka.sampleappkmp.android.feature.movies.navigation.AppNavigationScreen
import com.ihardanilchanka.sampleappkmp.android.core.navigation.ComposeNavigation
import com.ihardanilchanka.sampleappkmp.android.core.ui.resource.AppTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    private val navigation: ComposeNavigation by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            AppTheme { AppNavigationScreen(navController) }
            navigation.init(this, navController)
        }
    }
}
