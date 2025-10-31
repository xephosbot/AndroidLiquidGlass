package com.kyant.backdrop.catalog.utils

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

internal actual fun epochTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()