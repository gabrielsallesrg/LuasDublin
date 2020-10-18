package com.gsrg.luasdublin.core.utils

fun Any.TAG(): String {
    val tagName = this.javaClass.simpleName
    return if (tagName.length > 23) tagName.substring(0, 23) else tagName
}