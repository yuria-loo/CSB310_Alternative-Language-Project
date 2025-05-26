class Cell(data: Array<String>) {
    private var oem: String? = null
    private var model: String? = null;
    private var launchAnnounced: Int? = null
    private var launchStatus: String? = null
    private var bodyDimensions: String? = null
    private var bodyWeight: Float? = null
    private var bodySim: String = ""
    private var displayType: String? = null
    private var displaySize: Float? = null
    private var displayResolution: String? = null
    private var featSensors: String? = null
    private var platformOs: String? = null

    init {
        oem = data[0]
        model = data[1]
        launchAnnounced = extractYear(data[2])
        launchStatus = data[3]
        bodyDimensions = data[4]
        bodyWeight = extractWeight(data[5])
        bodySim = data[6]
        displayType = data[7]
        displaySize = extractSize(data[8])
        displayResolution = data[9]
        featSensors = data[10]
        platformOs = data[11]
    }

    private fun clean(input: String?): String? {
        return if (input.isNullOrBlank() || input == "-") null else input.trim()
    }

    private fun extractYear(input: String?): Int? {
        val yearRegex = Regex("""\b(\d{4})\b""")
        return yearRegex.find(input.orEmpty())?.groupValues?.get(1)?.toIntOrNull()
    }

    private fun extractWeight(input: String?): Float? {
        val gramsRegex = Regex("""(\d+(\.\d+)?)\s*g""")
        return gramsRegex.find(input.orEmpty())?.groupValues?.get(1)?.toFloatOrNull()
    }

    private fun extractSize(input: String?): Float? {
        val sizeRegex = Regex("""(\d+(\.\d+)?)""")
        return sizeRegex.find(input.orEmpty())?.groupValues?.get(1)?.toFloatOrNull()
    }

    override fun toString(): String {
        return "$oem $model - $launchAnnounced - $bodyWeight g - $displaySize\""
    }
}