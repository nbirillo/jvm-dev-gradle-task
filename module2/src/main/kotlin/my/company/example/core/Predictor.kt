package my.company.example.core

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import com.genesys.roberta.tokenizer.RobertaTokenizer
import io.kinference.ort.ORTEngine.loadModel
import io.kinference.ort.data.tensor.ORTTensor
import kotlinx.coroutines.runBlocking
import java.io.File
import java.nio.LongBuffer

object RobertaModelBuilder {
    val modelPath =
        RobertaModelBuilder::class.java.getResource("roberta-sequence-classification-9.onnx")
            ?.toURI()
            ?: error("We didn't find the file")

    fun makePrediction(tokenizer: RobertaTokenizer, text: String): String {
        val tokenizedText = tokenizer.tokenize(text)

        return runBlocking {
            val mlModel = loadModel(File(modelPath).readBytes())
            val shape = longArrayOf(1, tokenizedText.size.toLong())
            val tensor = OnnxTensor.createTensor(OrtEnvironment.getEnvironment(), LongBuffer.wrap(tokenizedText), shape)
            val prediction = mlModel.predict(listOf(ORTTensor("input", tensor)), false)
            val data = (prediction["output"] as ORTTensor).toFloatArray().toList()

            if (data[0] > data[1]) {
                "Prediction: negative"
            } else {
                "Prediction: positive"
            }
        }
    }
}