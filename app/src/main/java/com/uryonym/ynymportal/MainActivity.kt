package com.uryonym.ynymportal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.uryonym.ynymportal.ui.YnymPortalApp
import com.uryonym.ynymportal.ui.theme.YnymPortalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            YnymPortalTheme {
                YnymPortalApp()
            }
        }
    }
}
