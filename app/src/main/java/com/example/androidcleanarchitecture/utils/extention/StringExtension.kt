package com.example.androidcleanarchitecture.utils.extention

class StringExtension {
    fun String.capitalizeFirstChar(): String {
        return replaceFirstChar {
            it.uppercaseChar()
        }
    }
}