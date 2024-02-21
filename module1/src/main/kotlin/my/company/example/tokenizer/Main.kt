package my.company.example.tokenizer

import my.company.example.core.RobertaModelBuilder

fun safeInput(): String {
    var input: String
    do{
        input = readlnOrNull() ?: error("error")
    }while(!input.all {it.isLetter()} || input.isEmpty())
    return input
}
fun main() {
    val tokenizer = buildRobertaTokenizer()
    val prediction = RobertaModelBuilder.makePrediction(tokenizer, safeInput())
    println(prediction)
}
