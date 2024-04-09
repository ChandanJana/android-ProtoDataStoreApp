package com.tutorial.protodatastoreapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.tutorial.protodatastoreapplication.ui.theme.ProtoDataStoreApplicationTheme
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<AppSettings> by dataStore(
    fileName = "app-settings.json",
    serializer = AppSettingsSerializer
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProtoDataStoreApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")
                    
                    //Reading AppSetting object from a proto data store
                    val appSettings = dataStore.data.collectAsState(
                        initial = AppSettings()
                    ).value
                    val scope = rememberCoroutineScope()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        for(i in 0..3) {
                            val language = Language.values()[i]
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = language == appSettings.language,
                                    onClick = {
                                        scope.launch {
                                            setLanguage(language)
                                        }
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = language.toString())
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun setLanguage(language: Language) {
        //Writing to a proto data store
        dataStore.updateData {
            it.copy(language = language)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProtoDataStoreApplicationTheme {
        Greeting("Android")
    }
}