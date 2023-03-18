package com.example.tiptime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
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
    var roundUp by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current // используеться для перемещения и снятия фокуса
    var tipInput by remember { mutableStateOf("") }
    val tipPresent = tipInput.toDoubleOrNull() ?: 0.0
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull()
        ?: 0.0 // пользовательский ввод приходит стракой поэтому преобразуем его в деситичное значений и проверяем на null
    val tip = calculateTip(amount, tipPresent, roundUp)
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
        EditNumberField(label = R.string.bill_amount,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ), // добавили параметр настройки клавиатуры
            value = amountInput, // передаем текушее состояние вода пользователя
            onValueChange = { amountInput = it }, // пердаем веденое значение
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) } // сместить фокус в низ
            ))
        EditNumberField(
            label = R.string.how_was_the_service,
            value = tipInput,
            onValueChange = { tipInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }  // очищает фокус после действия
            )
        )
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

        RoundTheTipRow(
            roundUp = roundUp,
            onRoudUpChanged = { roundUp = it }) // выводем переключатель на юй


    }

}

// функция для вода пользователя
@Composable
fun EditNumberField(
//    Подъем состояния - это шаблон перемещения состояния до другой функции, чтобы сделать компонент без состояния.
// Применительно к составным элементам это часто означает введение двух параметров в составное:
    @StringRes label: Int, // добовляем параметр лэйбел для переиспользования функций, чтобы покозать что ожидаеться строковый ресурс добовляем анотацию
    keyboardOptions: KeyboardOptions, // параметр для устоновки типа клавиатуры
    keyboardActions: KeyboardActions,
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
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions

    )
}

// функция для переключателя чайвых
@Composable
fun RoundTheTipRow(
    modifier: Modifier = Modifier,
    roundUp: Boolean,
    onRoudUpChanged: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(id = R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoudUpChanged,
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = Color.DarkGray
            )
        )
    }
}

// функция для подсчета чаевых 15% от суммы
private fun calculateTip(
    amount: Double,
    tipPresent: Double = 15.0,
    roundUp: Boolean
): String {

    var tip = tipPresent / 100 * amount
    if (roundUp) {
        tip = kotlin.math.ceil(tip)
    }
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