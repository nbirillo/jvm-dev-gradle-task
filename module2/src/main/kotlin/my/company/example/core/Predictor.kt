package my.company.example.core

object RobertaModelBuilder{
    val modelPath =
        RobertaModelBuilder::class.java.getResource("roberta-sequence-classification-9.onnx")?: error("We didn't find the file")
}