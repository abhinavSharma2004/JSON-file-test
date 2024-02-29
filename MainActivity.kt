package com.example.jsontester

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jsontester.ui.theme.JsonTesterTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JsonTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Apper(this)
                }
            }
        }
    }
}

data class Camp(val name: String, val dead: String)

@Composable
fun Apper(context: Context, modifier: Modifier = Modifier) {
    var dis by remember { mutableStateOf(false) }
    val listy = remember { mutableStateListOf<Camp>() }

    val file = context.assets.open("companies.json")
    val reader = BufferedReader(InputStreamReader(file))
    val jsonString = reader.readText()
    reader.close() // Close the reader properly
    file.close() // Close the file

    val gson = Gson()
    val compListFromJson: List<Camp> = gson.fromJson(jsonString, object : TypeToken<List<Camp>>() {}.type)
    listy.addAll(compListFromJson)

    Button(
        onClick = { dis = true },
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Text(text = "Show Companies")
    }

    if (dis) {
        LazyColumn {
            items(listy) { camp ->
                Display(camp)
            }
        }
    }
}

@Composable
fun Display(listy: Camp, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(text = listy.name, modifier = Modifier, textAlign = TextAlign.Center)
        Text(text = listy.dead, modifier = Modifier, textAlign = TextAlign.Center)
    }
}

//@Preview(showBackground = true)
@Composable
fun GreetingPreview(activity: Activity) {
    JsonTesterTheme {
        Apper(LocalContext.current)
    }
}
