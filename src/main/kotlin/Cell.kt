/**
 * Represents a cell phone object created from a row in the CSV file.
 *
 * @constructor Initializes and cleans data from a CSV row represented as an Array of Strings.
 * @param data A row from the CSV file, containing 12 columns of phone attributes.
 *
 * @property oem The original equipment manufacturer of the phone.
 * @property model The model of the phone.
 * @property launchAnnounced The year the phone was announced.
 * @property launchStatus The availability status and release info.
 * @property bodyDimensions The physical dimensions of the phone.
 * @property bodyWeight The phone's weight in grams.
 * @property bodySim The type of SIM supported.
 * @property displayType The screen technology.
 * @property displaySize The screen size in inches.
 * @property displayResolution The screen resolution as a string.
 * @property featSensors The sensor features listed.
 * @property platformOs The phone's operating system.
 *
 * @author Yuria Loo
 */
class Cell(data: Array<String>) {
    // Class properties for each column in the CSV
    var oem: String? = null
    var model: String? = null
    var launchAnnounced: Int? = null
    var launchStatus: String? = null
    var bodyDimensions: String? = null
    var bodyWeight: Float? = null
    var bodySim: String? = null
    var displayType: String? = null
    var displaySize: Float? = null
    var displayResolution: String? = null
    var featSensors: String? = null
    var platformOs: String? = null

    // Initialization block that cleans and assigns values from the CSV row
    init {
        oem = clean(data.getOrNull(0))
        model = clean(data.getOrNull(1))
        launchAnnounced = extractYear(data.getOrNull(2))
        launchStatus = clean(data.getOrNull(3))
        bodyDimensions = clean(data.getOrNull(4))
        bodyWeight = extractWeight(data.getOrNull(5))
        bodySim = cleanSim(data.getOrNull(6))
        displayType = clean(data.getOrNull(7))
        displaySize = extractSize(data.getOrNull(8))
        displayResolution = clean(data.getOrNull(9))
        featSensors = cleanSensor(data.getOrNull(10))
        platformOs = extractPlatform(data.getOrNull(11))
    }

    /**
     * Cleans the input string by trimming and converting '-' or blank strings to null.
     *
     * @param input The raw string value from CSV.
     * @return The cleaned string or null.
     */
    private fun clean(input: String?): String? {
        return if (input.isNullOrBlank() || input == "-") null else input.trim()
    }

    /**
     * Extracts a 4-digit year from a string using regex.
     *
     * @param input A string potentially containing a year.
     * @return The year as an integer, or null if not found.
     */
    fun extractYear(input: String?): Int? {
        val yearRegex = Regex("""\b(\d{4})\b""")
        return yearRegex.find(input.orEmpty())?.groupValues?.get(1)?.toIntOrNull()
    }

    /**
     * Extracts numeric grams value from a weight string.
     *
     * @param input The weight string (e.g., "200 g").
     * @return The weight as a Float, or null if not found.
     */
    private fun extractWeight(input: String?): Float? {
        val gramsRegex = Regex("""(\d+(\.\d+)?)\s*g""")
        return gramsRegex.find(input.orEmpty())?.groupValues?.get(1)?.toFloatOrNull()
    }

    /**
     * Extracts a float value from a string.
     *
     * @param input The input string containing size.
     * @return The float value or null.
     */
    private fun extractSize(input: String?): Float? {
        val sizeRegex = Regex("""(\d+(\.\d+)?)""")
        return sizeRegex.find(input.orEmpty())?.groupValues?.get(1)?.toFloatOrNull()
    }

    /**
     * Treats 'yes' and 'no' as null in SIM field; otherwise cleans the input.
     *
     * @param input The SIM field value.
     * @return The cleaned SIM string or null.
     */
    private fun cleanSim(input: String?): String? {
        return if (input.equals("no", true) || input.equals("yes", true)) null else clean(input)
    }

    /**
     * Cleans the sensor field unless it is a numeric value.
     *
     * @param input The raw sensor string.
     * @return Cleaned string or null.
     */
    private fun cleanSensor(input: String?): String? {
        return if (input?.trim()?.matches(Regex("""\d+(\.\d+)?""")) == true) null else clean(input)
    }

    /**
     * Extracts the OS platform name, ignoring version numbers and parentheses.
     *
     * @param input The platform string.
     * @return Cleaned OS name or null.
     */
    private fun extractPlatform(input: String?): String? {
        return input?.split(',', '(')?.getOrNull(0)?.trim()?.takeIf { it.matches(Regex(""".*[a-zA-Z].*""")) }
    }

    /**
     * Extracts the release year from the launch status string.
     *
     * @return The release year as an Int, or null if not found.
     */
    fun extractReleaseYear(): Int? {
        val regex = Regex("""Released\s+(\d{4})""", RegexOption.IGNORE_CASE)
        return regex.find(this.launchStatus ?: "")?.groupValues?.get(1)?.toIntOrNull()
    }

    /**
     * Generates a human-readable string representing the phone's attributes.
     *
     * @return A formatted string of phone data.
     */
    override fun toString(): String {
        return "Oem: $oem , Model: $model," +
                "Launch announced: $launchAnnounced, " +
                "Launch status: $launchStatus, " +
                "Body Dimensions: $bodyDimensions, " +
                "Body weight: $bodyWeight g, " +
                "Body sim: $bodySim, " +
                "Display Type: $displayType, " +
                "Display size: $displaySize\" " +
                "Display resolution: $displayResolution, " +
                "Feature sensor: $featSensors, " +
                "Platform OS: $platformOs"
    }

    // Static-like methods in companion object

    companion object {
        /**
         * Calculates the average weight of phones in the list.
         *
         * @param cells List of Cell instances.
         * @return The mean body weight, or null if list is empty.
         */
        fun meanWeight(cells: List<Cell>): Float? {
            val weights = cells.mapNotNull { it.bodyWeight }
            return if (weights.isNotEmpty()) weights.average().toFloat() else null
        }

        /**
         * Calculates the median weight from a list of Cell instances.
         *
         * @param cells List of Cell instances.
         * @return The median weight, or null if list is empty.
         */
        fun medianWeight(cells: List<Cell>): Float? {
            val weights = cells.mapNotNull { it.bodyWeight }.sorted()
            val size = weights.size
            return when {
                size == 0 -> null
                size % 2 == 1 -> weights[size / 2]
                else -> (weights[size / 2 - 1] + weights[size / 2]) / 2
            }
        }

        /**
         * Computes the standard deviation of phone weights.
         *
         * @param cells List of Cell instances.
         * @return The standard deviation as a Double, or null if list is empty.
         */
        fun stdDevWeight(cells: List<Cell>): Double? {
            val weight = cells.mapNotNull { it.bodyWeight }
            val mean = weight.average()
            val variance = weight.map { (it - mean) * (it - mean) }.average()
            return if (weight.isNotEmpty()) kotlin.math.sqrt(variance) else null
        }

        /**
         * Determines the most frequent launch status among phones.
         *
         * @param cells List of Cell instances.
         * @return The most common launch status, or null if list is empty.
         */
        fun modeStatus(cells: List<Cell>): String? {
            return cells.mapNotNull { it.launchStatus }
                    .groupingBy { it }
                    .eachCount()
                    .maxByOrNull { it.value }
                    ?.key
        }

        /**
         * Retrieves all unique values for a specified column.
         *
         * @param column The name of the column (e.g., "oem", "model").
         * @param cells List of Cell instances.
         * @return A set of unique values, or an empty set if column not recognized.
         */
        fun uniqueValuesFor(column: String, cells: List<Cell>): Set<String?> {
            return when (column.lowercase()) {
                "oem" -> cells.map { it.oem }.toSet()
                "model" -> cells.map { it.model }.toSet()
                "status" -> cells.map { it.launchStatus }.toSet()
                "platform" -> cells.map { it.platformOs }.toSet()
                else -> emptySet()
            }
        }

        /**
         * Adds a Cell instance to a mutable list.
         *
         * @param cells The mutable list to add the Cell to.
         * @param cell The Cell instance to add.
         */
        fun addToList(cells: MutableList<Cell>, cell: Cell) {
            cells.add(cell)
        }

        /**
         * Deletes a Cell instance from the list by matching model name.
         *
         * @param cells The mutable list to delete from.
         * @param model The model name to match (case-insensitive).
         * @return True if a match was found and removed, false otherwise.
         */
        fun deleteByModel(cells: MutableList<Cell>, model: String): Boolean {
            return cells.removeIf { it.model.equals(model, ignoreCase = true) }
        }
    }
}