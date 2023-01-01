package com.rst.jetpack_compose.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.google.android.material.datepicker.MaterialDatePicker
import com.rst.jetpack_compose.LocalFragmentManager
import com.rst.jetpack_compose.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun AppContent() {
    val mContext = LocalContext.current

    val mCalendar = Calendar.getInstance().apply { time = Date() }
    val year = mCalendar.get(Calendar.YEAR)
    val month = mCalendar.get(Calendar.MONTH)
    val day = mCalendar.get(Calendar.DAY_OF_MONTH)

    val date = remember { mutableStateOf("") }

    val picker = MaterialDatePicker.Builder.dateRangePicker()
        .setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar).build()
    val fragmentManager = LocalFragmentManager.current


    DatePicker({}, {})
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DatePicker(onDateSelected: (LocalDate) -> Unit, onDismissRequest: () -> Unit) {
    val selDate = remember { mutableStateOf(LocalDate.now()) }

    //todo - add strings to resource after POC
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colors.surface,
//                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Column(
                Modifier
                    .defaultMinSize(minHeight = 72.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
//                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Заезд такой-то".uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary
                    )

                    Text(
                        text = "Сбросить всё".uppercase(Locale.getDefault()),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                Spacer(modifier = Modifier.size(24.dp))

                Text(
                    text = selDate.value.format(DateTimeFormatter.ofPattern("MMM d, YYYY")),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )

                Spacer(modifier = Modifier.size(16.dp))
            }

            CustomCalendarView(onDateSelected = {
                selDate.value = it
            })

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    //TODO - hardcode string
                    Text(
                        text = "Cancel",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                TextButton(
                    onClick = {
                        onDateSelected(selDate.value)
                        onDismissRequest()
                    }
                ) {
                    //TODO - hardcode string
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

            }
        }
    }
}

data class CalendarCell(
    val day: Int = -1,
    val enable: Boolean = true,
    val empty: Boolean = false,
)

sealed class CalendarItem {
    private companion object {
        private var id: Long = 1L
    }

    init {
        CalendarItem.id++
    }

    val id: Long = CalendarItem.id

    class Month(val name: String) : CalendarItem()
    class Row(val cells: List<CalendarCell>) : CalendarItem()
}

@Composable
fun CustomCalendarView(onDateSelected: (LocalDate) -> Unit) {
    val cellSize = 40.dp
    val horizontalPadding = 10.dp
    // Adds view to Compose
    val calendarData = remember {
        val res = mutableListOf<CalendarItem>()
        var id = 0L
        fun add() {
            var list = mutableListOf<CalendarCell>()
            for (i in 1..30) {
                list += CalendarCell(
                    day = i,
                    enable = i !in 1..2,
                )
                if (list.size == 7) {
                    res += CalendarItem.Row(list)
                    list = mutableListOf()
                }
            }
            list += List((7 - list.size) % 7) { CalendarCell(empty = true) }
            if (list.isNotEmpty()) {
                res += CalendarItem.Row(list)
            }
        }

        res += CalendarItem.Month("Октябрь 2021")
        add()

        res += CalendarItem.Month("Ноябрь 2021")
        add()

        res += CalendarItem.Month("Декабрь 2021")
        add()

        res += CalendarItem.Month("Январь 2022")
        add()

        res += CalendarItem.Month("Февраль 2022")
        add()

        res += CalendarItem.Month("Март 2022")
        add()

        res
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Calendar(horizontalPadding, cellSize, calendarData)
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 10.dp, end = 20.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue),
            onClick = { }) {
            Text("Окей", color = Color.White)
        }
    }
}

@Composable
private fun Calendar(
    horizontalPadding: Dp,
    cellSize: Dp,
    calendarData: MutableList<CalendarItem>
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(horizontal = horizontalPadding)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val days = remember { listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс") }
            repeat(days.size) {
                Box(
                    modifier = Modifier
                        .size(cellSize),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        text = days[it]
                    )
                }
            }
        }
        Log.d("asd", "calendarData: $calendarData")
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = calendarData, key = { it.id }) { item ->
                when (item) {
                    is CalendarItem.Month -> {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = horizontalPadding),
                            text = item.name
                        )
                    }
                    is CalendarItem.Row -> {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = horizontalPadding)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            repeat(7) {
                                val cell = item.cells[it]
                                when {
                                    cell.empty -> {
                                        Box(
                                            modifier = Modifier.size(cellSize)
                                        )
                                    }
                                    !cell.enable ->
                                        Box(
                                            modifier = Modifier
                                                .size(cellSize),
                                        ) {
                                            Text(
                                                modifier = Modifier.align(Alignment.Center),
                                                text = cell.day.toString(),
                                                textAlign = TextAlign.Center,
                                                color = Color.Gray,
                                            )
                                        }
                                    else -> {
                                        var background by remember {
                                            mutableStateOf(Color.White)
                                        }
                                        Box(
                                            modifier = Modifier
                                                .size(cellSize)
                                                .clip(CircleShape)
                                                .background(background)
                                                .clickable {
                                                    if (background == Color.White) {
                                                        background = Color.Red
                                                    } else {
                                                        background = Color.White
                                                    }
                                                },
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .align(Alignment.Center),
                                                text = cell.day.toString(),
                                                textAlign = TextAlign.Center,
                                                color = Color.Black,
                                            )
                                        }

                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}