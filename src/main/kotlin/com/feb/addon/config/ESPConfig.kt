package com.feb.addon.config

import java.awt.Color

object ESPConfig {
    val blue = Color(51, 79, 143)
    val Februari10Color = Color(33, 15, 235)
    val green = Color(0, 255, 0)
    val red = Color(250, 0, 0)
    val th3w4rd3nscolor = Color(158, 101, 194)
    val diegocolor = Color(93, 0, 2)

    var activeColor: Color = blue

    fun setColor(color: Color) {
        activeColor = color
    }
}