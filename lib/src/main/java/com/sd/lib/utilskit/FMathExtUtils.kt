package com.sd.lib.utilskit

/**
 * input:[0-100]
 * output:[0-10]
 * inputValue:50
 * 计算结果:5
 *
 * @param inputMin   输入最小值
 * @param inputMax   输入最大值
 * @param outputMin  输出最小值
 * @param outputMax  输出最大值
 * @param inputValue 输入值
 * @param scale      最多保留几位小数
 * @param mode       舍入模式
 */
fun fScaleBoundsValue(
    inputMin: Double, inputMax: Double,
    outputMin: Double, outputMax: Double,
    inputValue: Double,
): Double {
    require(inputMin < inputMax) { "require inputMin < inputMax" }
    require(outputMin < outputMax) { "require outputMin < outputMax" }
    val input = inputValue.coerceIn(inputMin, inputMax)
    val result = scaleBoundsValue(
        inputMin = inputMin, inputMax = inputMax,
        outputMin = outputMin, outputMax = outputMax,
        input = input,
    )
    return result.toDouble()
}

private fun scaleBoundsValue(
    inputMin: Number, inputMax: Number,
    outputMin: Number, outputMax: Number,
    input: Number,
): Number {
    val inputTotal = inputMax.fSubtract(inputMin)
    val inputDelta = input.fSubtract(inputMin)
    val inputPercent = inputDelta.fDivide(inputTotal)

    val outTotal = outputMax.fSubtract(outputMin)
    val outDelta = outTotal.fMultiply(inputPercent)
    return outputMin.fAdd(outDelta)
}