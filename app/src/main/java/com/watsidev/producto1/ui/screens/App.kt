package com.watsidev.producto1.ui.screens

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.watsidev.producto1.data.Datasource
import com.watsidev.producto1.ui.screens.apps.calculator.CalculatorScreen
import com.watsidev.producto1.ui.screens.apps.clock.ClockScreen
import com.watsidev.producto1.ui.screens.apps.compass.CompassScreen
import com.watsidev.producto1.ui.screens.apps.phone.PhoneScreen
import com.watsidev.producto1.ui.screens.apps.scanner.ScannerScreen
import com.watsidev.producto1.ui.screens.apps.todo.TodoTaskScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = (Login),
        ) {
            composable<Login> {
                LoginScreen(
                    navigateToHome = { navController.navigate(Home) }
                )
            }
            composable<Home> {
                val context = LocalContext.current

                HomeScreen(
                    listApps = Datasource.listApps,
                    onClickedIcon = { app ->
                        when (app.route) {
                            "youtube_intent" -> {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    setPackage("com.google.android.youtube")
                                    data = Uri.parse("https://www.youtube.com")
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                                }

                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {
                                    Toast.makeText(context, "YouTube no está instalado", Toast.LENGTH_SHORT).show()
                                }
                            }
                            else -> navController.navigate(app.route)
                        }
                    },
                    modifier = Modifier.statusBarsPadding()
                )
            }

            composable<Clock> {
                ClockScreen()
            }
            composable<Scanner> {
                ScannerScreen()
            }
            composable<Calculator> {
                CalculatorScreen()
            }
            composable<Phone> {
                PhoneScreen()
            }
            composable<Compass> {
                CompassScreen()
            }
            composable<ToDo> {
                TodoTaskScreen()
            }
        }
    }
}


