package com.example.tipcalculatorkotlinjetpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun TipCalculatorScreen(){
    var userInput by remember { mutableStateOf("")}
    var amount = userInput.toDoubleOrNull() ?: 0.0
    var tip = tipCalculator(amount)
    Column(modifier = Modifier.padding(36.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){
        Text(text = stringResource(R.string.calculate_tip),fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(Modifier.height(16.dp))
        EditTextField(userInput, onValueChange = {userInput = it})
        Text(text = stringResource(id = R.string.tip_amount, tip),fontSize = 20.sp, fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun EditTextField(userInput: String, onValueChange: (String) -> Unit){

    TextField(value = userInput, onValueChange = onValueChange,
        label = { Text(stringResource(R.string.cost_of_service)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))


}

fun tipCalculator(amount: Double, tipPercentage: Double = 15.00): String {

        val tipAmount =  tipPercentage/100.00 * amount
    return NumberFormat.getCurrencyInstance().format(tipAmount)
}