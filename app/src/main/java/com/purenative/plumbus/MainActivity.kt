package com.purenative.plumbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.purenative.plumbus.core.ui.theme.PlumbusTheme
import com.purenative.plumbus.features.characters.Characters
import com.purenative.plumbus.features.characters.CharactersAction
import com.purenative.plumbus.features.characters.CharactersViewModel
import com.purenative.plumbus.features.home.Home
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val charactersViewModel: CharactersViewModel by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        charactersViewModel.submitAction(CharactersAction.RefreshAction)
        setContent {
            PlumbusTheme {
                Home()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PlumbusTheme {
        Greeting("Android")
    }
}