import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CellTest {

    @Test
    fun testCellInitAndGetterSetter() {
        // This will test the initialization, getter and setter, and cleaning data functions.
        // Also test the following:
        // Ensure each column's final transformation matches what is stated above as its final form (ex: test if display_size is now a float)

        val rawData = arrayOf(
                "Samsung", "Galaxy S21", "Announced 2021", "Available. Released 2021",
                "151.7 x 71.2 x 7.9 mm", "169 g", "Nano-SIM", "AMOLED", "6.2", "1080x2400",
                "Fingerprint (under display)", "Android 11"
        )
        val cell = Cell(rawData)

        assertEquals("Samsung", cell.oem)
        assertEquals("Galaxy S21", cell.model)
        assertEquals(2021, cell.launchAnnounced)
        assertEquals("Available. Released 2021", cell.launchStatus)
        assertEquals(169.0f, cell.bodyWeight)
        assertEquals(6.2f, cell.displaySize)
        assertEquals("Android 11", cell.platformOs)
    }

    @Test
    fun testMeanWeight() {
        // This will test the calculation of mean weight as well as all missing or "-" data is replaced with a null value.

        val cell1 = Cell(arrayOf("OEM", "M1", "2020", "Available. Released 2020", "-", "180 g", "-", "-", "-", "-", "-", "-"))
        val cell2 = Cell(arrayOf("OEM", "M2", "2020", "Available. Released 2020", "-", "220 g", "-", "-", "-", "-", "-", "-"))
        val cells = listOf(cell1, cell2)

        val mean = Cell.meanWeight(cells)
        assertEquals(200.0f, mean)
        assertEquals(null, cell1.bodySim)
        assertEquals(null, cell1.bodyDimensions)
        assertEquals(null, cell1.displayType)
        assertEquals(null, cell1.displaySize)
        assertEquals(null, cell1.displayResolution)
        assertEquals(null, cell1.featSensors)
        assertEquals(null, cell1.platformOs)
    }

    @Test
    fun testMedianWeight() {
        val cells = listOf(
                Cell(arrayOf("OEM", "A", "2021", "", "", "100 g", "", "", "", "", "", "")),
                Cell(arrayOf("OEM", "B", "2021", "", "", "200 g", "", "", "", "", "", "")),
                Cell(arrayOf("OEM", "C", "2021", "", "", "300 g", "", "", "", "", "", ""))
        )
        assertEquals(200.0f, Cell.medianWeight(cells))
    }

    @Test
    fun testStdDevWeight() {
        val cells = listOf(
                Cell(arrayOf("OEM", "A", "2021", "", "", "100 g", "", "", "", "", "", "")),
                Cell(arrayOf("OEM", "B", "2021", "", "", "200 g", "", "", "", "", "", "")),
                Cell(arrayOf("OEM", "C", "2021", "", "", "300 g", "", "", "", "", "", ""))
        )
        val result = Cell.stdDevWeight(cells)
        assertNotNull(result)
        assertEquals(81.65, result!!, 0.01)  // ±0.01 tolerance
    }

    @Test
    fun testModeStatus() {
        val cells = listOf(
                Cell(arrayOf("OEM", "A", "2020", "Available", "-", "100 g", "-", "-", "-", "-", "-", "-")),
                Cell(arrayOf("OEM", "B", "2021", "Available", "-", "110 g", "-", "-", "-", "-", "-", "-")),
                Cell(arrayOf("OEM", "C", "2022", "Discontinued", "-", "120 g", "-", "-", "-", "-", "-", "-"))
        )

        assertEquals("Available", Cell.modeStatus(cells))
    }

    @Test
    fun testUniqueValuesFor() {
        val cells = listOf(
                Cell(arrayOf("Samsung", "A", "2020", "Available", "-", "100 g", "-", "-", "-", "-", "-", "-")),
                Cell(arrayOf("Apple", "B", "2021", "Discontinued", "-", "110 g", "-", "-", "-", "-", "-", "-")),
                Cell(arrayOf("Samsung", "C", "2022", "Available", "-", "120 g", "-", "-", "-", "-", "-", "-"))
        )

        val uniqueOEMs = Cell.uniqueValuesFor("oem", cells)
        assertEquals(setOf("Samsung", "Apple"), uniqueOEMs)
    }

    @Test
    fun testAddToList() {
        val cellList = mutableListOf<Cell>()
        val newCell = Cell(arrayOf("OEM", "ModelX", "2023", "Available", "-", "180 g", "-", "-", "-", "-", "-", "-"))

        Cell.addToList(cellList, newCell)

        assertEquals(1, cellList.size)
        assertEquals("ModelX", cellList[0].model)
    }

    @Test
    fun testDeleteByModel() {
        val cellList = mutableListOf(
                Cell(arrayOf("OEM", "Alpha", "2022", "Available", "-", "190 g", "-", "-", "-", "-", "-", "-")),
                Cell(arrayOf("OEM", "Beta", "2023", "Available", "-", "200 g", "-", "-", "-", "-", "-", "-"))
        )

        val wasDeleted = Cell.deleteByModel(cellList, "beta")

        assertTrue(wasDeleted)
        assertEquals(1, cellList.size)
        assertEquals("Alpha", cellList[0].model)
    }

    @Test
    fun testExtractYearFromMalformedString() {
        val cell = Cell(Array(12) { "" })
        assertEquals(2018, cell.extractYear("Expected in Q1 2018"))
        assertNull(cell.extractYear("No year provided"))
    }

    @Test
    fun testExtractReleaseYear() {
        val data = arrayOf(
                "OEM", "Model", "2021", "Available. Released 2021",
                "-", "150 g", "-", "-", "-", "-", "-", "-"
        )
        val cell = Cell(data)
        assertEquals(2021, cell.extractReleaseYear())
    }

    @Test
    fun `readCSV should throw if given invalid file`() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            readCSV("/no_such_file.csv")
        }
        assertTrue(exception.message!!.contains("File not found"))
    }

}