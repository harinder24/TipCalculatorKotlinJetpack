package com.example.tipcalculatorkotlinjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculatorkotlinjetpack.ui.theme.TipCalculatorKotlinJetpackTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorKotlinJetpackTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TipCalculatorScreen() {
    var userInput by remember { mutableStateOf("") }
    val amount = userInput.toDoubleOrNull() ?: 0.0

    var userTipAmount by remember {
        mutableStateOf("")
    }
    val userTipInDouble = userTipAmount.toDoubleOrNull() ?: 0.0

    val tip = tipCalculator(amount, userTipInDouble)

    val focusManager = LocalFocusManager.current

    Column(modifier = Modifier.padding(36.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.calculate_tip), fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        EditTextField(
            R.string.cost_of_service,
            userInput,
            onValueChange = { userInput = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ), keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(FocusDirection.Down)})
        )

        EditTextField(
            R.string.how_was_the_service,
            userTipAmount,
            onValueChange = { userTipAmount = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onNext = {focusManager.clearFocus()})
        )
        Text(
            text = stringResource(id = R.string.tip_amount, tip),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun EditTextField(
    @StringRes label: Int,
    userInput: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions
) {

    TextField(
        value = userInput, onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true, keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )


}

fun tipCalculator(amount: Double, tipPercentage: Double): String {

    val tipAmount = tipPercentage / 100.00 * amount
    return NumberFormat.getCurrencyInstance().format(tipAmount)
}