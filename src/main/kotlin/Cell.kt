class Cell(data: Array<String>) {
    // Class properties for each column in the CSV
    private var oem: String? = null
    private var model: String? = null
    private var launchAnnounced: Int? = null
    private var launchStatus: String? = null
    private var bodyDimensions: String? = null
    private var bodyWeight: Float? = null
    private var bodySim: String? = null
    private var displayType: String? = null
    private var displaySize: Float? = null
    private var displayResolution: String? = null
    private var featSensors: String? = null
    private var platformOs: String? = null

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

    // Removes dash or clank entries and trims valid strings
    private fun clean(input: String?): String? {
        return if (input.isNullOrBlank() || input == "-") null else input.trim()
    }

    // Extracts a 4-digit year from a string
    private fun extractYear(input: String?): Int? {
        val yearRegex = Regex("""\b(\d{4})\b""")
        return yearRegex.find(input.orEmpty())?.groupValues?.get(1)?.toIntOrNull()
    }

    // Extracts numeric grams value from a weight string
    private fun extractWeight(input: String?): Float? {
        val gramsRegex = Regex("""(\d+(\.\d+)?)\s*g""")
        return gramsRegex.find(input.orEmpty())?.groupValues?.get(1)?.toFloatOrNull()
    }

    // Extracts a float value from a string
    private fun extractSize(input: String?): Float? {
        val sizeRegex = Regex("""(\d+(\.\d+)?)""")
        return sizeRegex.find(input.orEmpty())?.groupValues?.get(1)?.toFloatOrNull()
    }

    // Treat "yes" and "no" in SIM field as null, otherwise clean
    private fun cleanSim(input: String?): String? {
        return if (input.equals("no", true) || input.equals("yes", true)) null else clean(input)
    }

    // Cleans sensor string unless it's just a number
    private fun cleanSensor(input: String?): String? {
        return if (input?.trim()?.matches(Regex("""\d+(\.\d+)?""")) == true) null else clean(input)
    }

    // Extracts the OS name, removing versions/parentheses
    private fun extractPlatform(input: String?): String? {
        return input?.split(',', '(')?.getOrNull(0)?.trim()?.takeIf { it.matches(Regex(""".*[a-zA-Z].*""")) }
    }

    // Extracts release year from launch status if present
    private fun extractReleaseYear(): Int? {
        val regex = Regex("""Released\s+(\d{4})""", RegexOption.IGNORE_CASE)
        return regex.find(this.launchStatus ?: "")?.groupValues?.get(1)?.toIntOrNull()
    }

    // Generates a string representation of the cell phone object
    override fun toString(): String {
        return "Oem: $oem , Model: $model," +
                "Launch announced: $launchAnnounced, " +
                "Body weight: $bodyWeight g, " +
                "Display size: $displaySize\""
    }
}