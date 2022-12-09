package com.sd.lib.utilskit

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * 加法
 */
fun Number.fAdd(value: Number): BigDecimal {
    return BigDecimal(this.toString())
        .add(BigDecimal(value.toString()))
}

/**
 * 减法
 */
fun Number.fSubtract(value: Number): BigDecimal {
    return BigDecimal(this.toString())
        .subtract(BigDecimal(value.toString()))
}

/**
 * 乘法
 */
fun Number.fMultiply(value: Number): BigDecimal {
    return BigDecimal(this.toString())
        .multiply(BigDecimal(value.toString()))
}

/**
 * 除法
 */
fun Number.fDivide(value: Number): BigDecimal {
    return BigDecimal(this.toString())
        .divide(BigDecimal(value.toString()))
}

/**
 * 除法
 */
fun Number.fDivide(value: Number, scale: Int, mode: RoundingMode = RoundingMode.HALF_UP): BigDecimal {
    return BigDecimal(this.toString())
        .divide(BigDecimal(value.toString()), scale, mode)
}

/**
 * 保留小数位
 */
fun Number.fScale(scale: Int, mode: RoundingMode = RoundingMode.HALF_UP): BigDecimal {
    return BigDecimal(this.toString()).setScale(scale, mode)
}