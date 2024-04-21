package com.example.samplemvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.samplemvi.navigation.MainNavHost
import com.example.samplemvi.ui.theme.SampleMVITheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            SampleMVITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MainNavHost(
                        navController = navController
                    )
                }
            }
        }
    }
}