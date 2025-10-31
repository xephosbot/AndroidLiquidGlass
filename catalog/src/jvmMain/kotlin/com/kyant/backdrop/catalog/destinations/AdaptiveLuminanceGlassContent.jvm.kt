package com.kyant.backdrop.catalog.destinations

internal actual fun String.format(vararg args: Any?): String {
    return java.lang.String.format(this, *args)
}
