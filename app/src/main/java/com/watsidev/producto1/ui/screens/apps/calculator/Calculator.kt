package com.watsidev.producto1.ui.screens.apps.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import net.objecthunter.exp4j.ExpressionBuilder

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalculatorScreen() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    fun evaluateExpression(expr: String): String {
        return try {
            val cleanExpr = expr.replace("×", "*").replace("÷", "/")
            val result = ExpressionBuilder(cleanExpr).build().evaluate()
            if (result % 1 == 0.0) result.toInt().toString() else result.toString()
        } catch (e: Exception) {
            "Syntax Error"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            TextField(
                value = expression,
                onValueChange = { expression = it },
                readOnly = true,
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 24.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
            )
            TextField(
                value = result,
                onValueChange = { result = it},
                readOnly = true,
                textStyle = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 50.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                ),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )
        }

        val buttons = listOf(
            listOf("(", ")", "C", ""),
            listOf("7", "8", "9", "÷"),
            listOf("4", "5", "6", "×"),
            listOf("1", "2", "3", "-"),
            listOf(".", "0", "=", "+")
        )

        buttons.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { symbol ->
                    Button(
                        onClick = {
                            when (symbol) {
                                "=" -> result = if (expression.isNotEmpty()) evaluateExpression(expression) else ""
                                "C" -> {
                                    expression = ""
                                    result = ""
                                }
                                else -> expression += symbol
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp,
                            hoveredElevation = 6.dp
                        ),
                        colors = when (symbol) {
                            "=" -> ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary,
                                contentColor = MaterialTheme.colorScheme.onTertiary
                            )
                            "÷", "×", "-", "+", "C", "(", ")", "." -> ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                            else -> ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    ) {
                        if (symbol == "") {
                            IconButton(
                                onClick = {
                                    if (expression.isNotEmpty()){
                                        expression = expression.dropLast(1)
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Backspace,
                                    contentDescription = "Delete Expression",
                                )
                            }
                        } else {
                            Text(text = symbol, fontSize = 24.sp)
                        }
                    }
                }
            }
        }
    }
}
