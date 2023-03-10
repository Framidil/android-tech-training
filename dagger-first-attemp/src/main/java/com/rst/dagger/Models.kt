package com.rst.dagger

import android.content.Context

class Processor {
    override fun toString() = "AB2021"
}

class Motherboard {
    override fun toString() = "X7 3000"
}

class RAM {
    override fun toString() = "16 GB"
}

data class Computer(
    val processor: Processor,
    val motherboard: Motherboard,
    val ram: RAM
)

interface F
class FA(val context: Context) : F
class FB: F