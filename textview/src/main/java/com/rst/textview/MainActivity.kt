package com.rst.textview

import android.os.Bundle
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {
    private val flow = MutableStateFlow("a")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}

fun main() {
    val printer = when (val scenario = 2) {
        1 -> createFirstScenarioPrinter()
        2 -> createSecondScenarioPrinter()
        else -> error("Укажите существующий сценарий")
    }
    printer.show(
        showCoordinates = true
    )
}

private fun createFirstScenarioPrinter(): CubePrinter {
    val printer = CubePrinter(
        canvasWidth = 30,
        canvasHeight = 19,
    )

    printer.printCube(
        widthOffset = 0,
        heightOffset = 0,
        height = 10,
        width = 10,
        depth = 10
    )
    printer.printCube(
        widthOffset = 0,
        heightOffset = 1,
        height = 5,
        width = 5,
        depth = 5
    )
    printer.printCube(
        widthOffset = 0,
        heightOffset = 0,
        height = 4,
        width = 4,
        depth = 4
    )
    printer.printCube(
        widthOffset = 9,
        heightOffset = 10,
        height = 6,
        width = 8,
        depth = 4
    )
    return printer
}

private fun createSecondScenarioPrinter(): CubePrinter {
    val printer = CubePrinter(
        canvasWidth = 30,
        canvasHeight = 25,
    )

    printer.printCube(
        widthOffset = 0,
        heightOffset = 0,
        height = 13,
        width = 13,
        depth = 13,
    )

    printer.printCube(
        widthOffset = 2,
        heightOffset = 15,
        height = 5,
        width = 5,
        depth = 3,
    )
    printer.printCube(
        widthOffset = 15,
        heightOffset = 10,
        height = 5,
        width = 5,
        depth = 5,
    )
    printer.printCube(
        widthOffset = 6,
        heightOffset = 2,
        height = 5,
        width = 5,
        depth = 5,
    )
    return printer
}

private class CubePrinter(
    @IntRange(from = 1) val canvasWidth: Int,
    @IntRange(from = 1) val canvasHeight: Int,
) {

    private companion object {
        const val EMPTY = ' '
        const val EDGE = '#'
        const val DIAGONAL = '.'
        const val FRONT_SIDE = ' '
        const val TOP_SIDE = EMPTY
        const val RIGHT_SIDE = ' '
    }

    private val matrix =
        MutableList(canvasHeight) {
            MutableList(canvasWidth) { EMPTY }
        }

    private var cubeToPrintCount = 0

    fun printCube(
        widthOffset: Int,
        heightOffset: Int,
        @IntRange(from = 1) width: Int,
        @IntRange(from = 1) height: Int,
        @IntRange(from = 1) depth: Int
    ) {
        cubeToPrintCount++
        try {
            _printCube(
                widthOffset = widthOffset,
                heightOffset = heightOffset,
                width = width,
                height = height,
                depth = depth,
            )
        } catch (e: Exception) {
            println("Error on cube $cubeToPrintCount")
            println("\twidthOffset: $widthOffset")
            println("\theightOffset: $heightOffset")
            println("\twidth: $width")
            println("\theight: $height")
            println("\tdepth: $depth")
            throw e
        }
    }

    fun _printCube(
        widthOffset: Int,
        heightOffset: Int,
        @IntRange(from = 1) width: Int,
        @IntRange(from = 1) height: Int,
        @IntRange(from = 1) depth: Int
    ) {
        val startX = widthOffset
        val startY = heightOffset
        val depthOffset = depth - 1

        for (y in (startY + depthOffset + 1)..(startY + depthOffset + height - 2)) {
            for (x in (startX + 1)..(startX + width - 2)) {
                matrix[y][x] = FRONT_SIDE
            }
        }

        for ((shiftX, y) in ((startY + depthOffset - 1) downTo (startY + 1)).withIndex()) {
            for (x in (startX + shiftX + 1)..(startX + shiftX + width - 1)) {
                matrix[y][x] = TOP_SIDE
            }
        }

        for ((shiftX, y) in ((startY + depthOffset) downTo (startY + depthOffset - height - 3)).withIndex()) {
            for (x in (startX + width + shiftX)..(startX + width + depthOffset - 2)) {
                matrix[y][x] = RIGHT_SIDE
            }
        }
        for ((shiftX, y) in ((startY + depthOffset)..(startY + depthOffset + height - 3)).withIndex()) {
            for (x in (startX + width)..(startX - shiftX + width + depthOffset - 2)) {
                matrix[y][x] = RIGHT_SIDE
            }
        }

        repeat(width) { columnIndex ->
            matrix[startY + depthOffset][columnIndex + startX] = EDGE
            matrix[startY + height - 1 + depthOffset][columnIndex + startX] = EDGE

            matrix[startY][columnIndex + depthOffset + startX] = EDGE
        }
        repeat(height) { rowIndex ->
            matrix[startY + rowIndex + depthOffset][0 + startX] = EDGE
            matrix[startY + rowIndex + depthOffset][width - 1 + startX] = EDGE

            matrix[startY + rowIndex][depthOffset + width - 1 + startX] = EDGE
        }

        repeat(depthOffset) {
            if (it == 0) {
                return@repeat
            }
            matrix[startY + depthOffset - it][it + startX] = DIAGONAL
            matrix[startY + depthOffset - it][width + it - 1 + startX] = DIAGONAL
            matrix[startY + depthOffset + height - it - 1][width + it - 1 + startX] = DIAGONAL
        }
    }

    fun show(showCoordinates: Boolean) {
        matrix.forEachIndexed { index, row ->
            if (showCoordinates) {
                print("%2d| ".format(index))
            }
            row.forEachIndexed { index, it ->
                print("$it ")
            }
            println()
        }
    }
}
