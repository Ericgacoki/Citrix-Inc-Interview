package com.ericdev.citrixincinterview.util.extension

fun String.hideMiddleCharacters(): String {
    val length = this.length
    return if (length <= 8) {
        // If the user ID has 4 or fewer characters, no need to hide anything
        this
    } else {
        val prefix = this.substring(0, 4)
        val suffix = this.substring(length - 4)
        val middleDots = "*".repeat(6)
        "$prefix$middleDots$suffix"
    }
}
