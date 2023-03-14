package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptime.ui.theme.TipTimeTheme
import androidx.compose.ui.Alignment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTimeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipTimeScreen()
                }
            }
        }
    }
}

@Composable
fun TipTimeScreen() {
    Column(modifier = Modifier.padding(32.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp))
    {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EditNumberField()

    }

}
// функция для вода пользователя
@Composable
fun EditNumberField() {
//    изменяемая перменая для отслеживания вода через состояния
    var amountInput by remember { mutableStateOf("") }
    TextField(value = amountInput, // значение
        onValueChange = {amountInput = it}, // возращаемое значение
    label = { Text(text = stringResource(id = R.string.cost_of_service))}, // лэйбел для ввода который помогает понять пользователю о контексте
    modifier = Modifier.fillMaxWidth(), // модификатор занять всю ширину
    singleLine = true, // парамет сводяший поля вода в одну строку
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // опция для вода с клавиатуры только цифр
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipTimeTheme {
        TipTimeScreen()
    }
}