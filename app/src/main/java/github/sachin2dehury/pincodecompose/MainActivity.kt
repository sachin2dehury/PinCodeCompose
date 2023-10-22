package github.sachin2dehury.pincodecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import github.sachin2dehury.pincodecompose.ui.theme.PinCodeComposeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinCodeComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(Color.Black),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        TextInput(callback = viewModel::getPinData)
                        PinDetailsBlock(pinResponse = viewModel.pinData.collectAsState().value)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(modifier: Modifier = Modifier, callback: (String) -> Unit) {
    var state by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(Color.White),
            value = state,
            onValueChange = {
                state = it
            },
        )

        Button(onClick = { callback.invoke(state) }) {
            Text(text = "Fetch Pincode Data", textAlign = TextAlign.Center, style = TextStyle())
        }
    }
}

@Composable
fun PinDetailsBlock(modifier: Modifier = Modifier, pinResponse: ResultState) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Cyan),
        verticalArrangement = Arrangement.Top,
    ) {
        when (pinResponse) {
            is ResultState.Success -> {
                Text(text = "Country : ${pinResponse.data?.country}")
                Box(modifier = Modifier.height(12.dp))
                Text(text = "Place : ${pinResponse.data?.places?.firstOrNull()?.placeName}")
                Box(modifier = Modifier.height(12.dp))
                Text(text = "State : ${pinResponse.data?.places?.firstOrNull()?.state}")
            }

            is ResultState.Error -> {
                Text(text = pinResponse.data ?: "Something went wrong!!")
            }

            is ResultState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }

            is ResultState.Empty -> {
                // do nothing
            }
        }
    }
}
