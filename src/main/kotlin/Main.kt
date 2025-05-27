import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val cells = readCSV()
    println("Total phones loaded: ${cells.size}\n")
   
    // Print the first 5 Cell objects
    cells.take(5).forEachIndexed { index, cell ->
        println("Phone #${index + 1}: $cell")
    }
}

// Reads and processes the CSV file into a list of Cell Object
fun readCSV(): List<Cell> {
    val cells = mutableListOf<Cell>()

    // Opens CSV file
    val input = object {}.javaClass.getResourceAsStream("/cells.csv")
            ?: throw IllegalArgumentException("File not found.")

    val reader = BufferedReader(InputStreamReader(input))

    reader.readLine() // Skips the header line

    var line = reader.readLine()
    while (line != null) {
        // Splits each line into columns using regex to handle commas
        val values = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*\$)".toRegex()).map { it.trim() }.toTypedArray()
        // Creates Cell object and adds to list
        cells.add(Cell(values))
        line = reader.readLine()
    }
    // Removes duplicates using toString representation
    return cells.distinctBy { it.toString() }
}

//