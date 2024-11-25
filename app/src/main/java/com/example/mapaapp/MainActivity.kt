package com.example.mapaapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        //Julio Noboa 2022-1015
        topBar = {
            TopAppBar(
                title = { Text("Map App") }
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                TextField(
                    value = name,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { name = it },
                    label = { Text("First Name") }
                )
                TextField(
                    value = surname,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { surname = it },
                    label = { Text("Last Name") }
                )
                TextField(
                    value = latitude,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { latitude = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Latitude") }
                )
                TextField(
                    value = longitude,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { longitude = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Longitude") }
                )
                Button(
                    onClick = {
                        val intent = Intent(context, MapActivity::class.java).apply {
                            putExtra("name", name)
                            putExtra("surname", surname)
                            putExtra("latitude", latitude.toDoubleOrNull() ?: 0.0)
                            putExtra("longitude", longitude.toDoubleOrNull() ?: 0.0)
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Map")
                }
            }
        }
    )
}