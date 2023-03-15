package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import java.text.NumberFormat

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
    var tipInput by remember { mutableStateOf("") }
    val tipPresent = tipInput.toDoubleOrNull() ?: 0.0
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0 // пользовательский ввод приходит стракой поэтому преобразуем его в деситичное значений и проверяем на null
    val tip = calculateTip(amount, tipPresent)
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        Text(
            text = stringResource(R.string.calculate_tip),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EditNumberField(label = R.string.tip_amount,
            value = amountInput, // передаем текушее состояние вода пользователя
            onValueChange = { amountInput = it }) // пердаем веденое значение
        EditNumberField(label = R.string.how_was_the_service,
            value = tipInput,
            onValueChange = {tipInput = it})
        Spacer(modifier = Modifier.height(24.dp)) // добовляем промежуток высотой 24 пикселя уневерсальных
        Text(
            text = stringResource(
                id = R.string.tip_amount,
                tip
            ), // добовляе строковый ресурс который использует для отображения чайвых с валютой
            modifier = Modifier.align(Alignment.CenterHorizontally), // модефикатор выравнивания по горизонтали в центре
            fontSize = 20.sp, // размер шрифта 20
            fontWeight = FontWeight.Bold // толстый шрифт
        )


    }

}

// функция для вода пользователя
@Composable
fun EditNumberField(
//    Подъем состояния - это шаблон перемещения состояния до другой функции, чтобы сделать компонент без состояния.
// Применительно к составным элементам это часто означает введение двух параметров в составное:
    @StringRes label: Int, // добовляем параметр лэйбел для переиспользования функций, чтобы покозать что ожидаеться строковый ресурс добовляем анотацию
    value: String, // текуший параметр для отображения
    onValueChange: (String) -> Unit, // код для обратного вызова, для измения состояния
    modifier: Modifier = Modifier // рекомендуеться добовлять параметр модификатора после обезательных параметров для дольнейщего переиспользования функций
) {


    TextField(
        value = value, // значение текущее
        onValueChange = onValueChange, // возращаемое значение для изменения состояния
        label = { Text(text = stringResource(label)) }, // лэйбел для ввода который помогает понять пользователю о контексте
        modifier = Modifier.fillMaxWidth(), // модификатор занять всю ширину
        singleLine = true, // парамет сводяший поля вода в одну строку
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number,
        imeAction = ImeAction.Next) // опция для вода с клавиатуры только цифр
    )
}

// функция для подсчета чаевых 15% от суммы
private fun calculateTip(
    amount: Double,
    tipPresent: Double = 15.0
): String {
    val tip = tipPresent / 100 * amount
    return NumberFormat.getCurrencyInstance()
        .format(tip) // класс позволяет формотировать значения в качестве валюты

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipTimeTheme {
        TipTimeScreen()
    }
}