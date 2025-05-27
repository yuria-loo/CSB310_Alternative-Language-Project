import java.io.BufferedReader
import java.io.InputStreamReader

fun main() {
    val cells = readCSV()
    println("Total phones loaded: ${cells.size}\n")
   
    // Print the first 5 Cell objects
    cells.take(5).forEachIndexed { index, cell ->
        println("Phone #${index + 1}: $cell")
    }

    // Q1: OEM with the highest average weight
    val oemWithHighestAvgWeight = cells
            .filter { it.oem != null && it.bodyWeight != null }
            .groupBy { it.oem }
            .mapValues { (_, list) -> list.mapNotNull { it.bodyWeight }.average() }
            .maxByOrNull { it.value }?.key
    println("Q1: OEM with the highest average weight: $oemWithHighestAvgWeight")

    // Q2: Phones announced in one year, released in another
    val mismatchCells = cells.filter {
        val announced = it.launchAnnounced
        val release = it.extractReleaseYear()
        announced != null && release != null && announced != release
    }
    println("\nQ2: Phones announced and released in different years: ${mismatchCells.size}")
    mismatchCells.forEach { println("- ${it.oem} ${it.model} (Announced: ${it.launchAnnounced}, Released: ${it.extractReleaseYear()})") }

    // Q3: Phone with only one censor
    val singleSensorCells = cells.count{
        val sensor = it.featSensors?.split(',')?.map { s -> s.trim() }?.filter { it.isNotEmpty() }
        sensor?.size == 1
    }
    println("\nQ3: Number of phones with only one feature sensor: $singleSensorCells")

    // Q4: Year with the most phones launched after 1999
    val commonYear = cells.mapNotNull { it.launchAnnounced }
            .filter { it > 1999 }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }
    println("\nQ4: Year with the most phone launches after 1999: ${commonYear?.key} with ${commonYear?.value} launches")
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