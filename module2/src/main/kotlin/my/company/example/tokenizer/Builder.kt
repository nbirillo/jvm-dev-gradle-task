package my.company.example.tokenizer

import com.genesys.roberta.tokenizer.RobertaTokenizer
import com.genesys.roberta.tokenizer.RobertaTokenizerResources
import java.io.File


object RobertaTokenizerBuilder {
    private val resources = listOf("merges.txt", "base_vocabulary.json", "vocabulary.json")

    // 1. Find all three files in resources (all of them exist)
    // 2. All of the files are stored in the same folder
    // 3. Return the path
    fun getPath() = resources.mapNotNull {
        RobertaTokenizerBuilder::class.java.getResource(it)
    }.also {
        require(it.size == resources.size) {
            "Some of the files were not found."
        }
    }.map { File(it.path).parent }.toSet().also {
        require(it.size == 1) {
            "The root folder is not the same for the files."
        }
    }.first()
}

fun buildRobertaTokenizer() =
    RobertaTokenizer(
        RobertaTokenizerResources(
            RobertaTokenizerBuilder.getPath()
        )
    )
