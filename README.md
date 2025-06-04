# CSB310_Alternative-Language-Project

###  Programming Language Chosen
**Kotlin 1.9.x**  
Kotlin is a modern, statically typed programming language developed by JetBrains. It runs on the JVM and is fully interoperable with Java, but features cleaner syntax, null safety, and strong type inference.


---

### Why Kotlin?
I chose Kotlin because:
- It is widely used in **Android development** and increasingly in backend/server-side applications.
- It combines the **conciseness** of scripting languages like Python with the **performance and structure** of Java.
- Its **expressive syntax** and functional programming support make data manipulation more intuitive.

---

## 🧱 How Kotlin Handles Core Programming Concepts

| Concept                               | Kotlin Support                                                                                                                |
|---------------------------------------|-------------------------------------------------------------------------------------------------------------------------------|
| **Object-Oriented Programming**       | Fully supported: classes, inheritance, interfaces, and encapsulation.                                                         |
| **File Ingestion**                    | Built-in libraries and extensions make reading CSVs straightforward using `BufferedReader`, Kotlin I/O, or external CSV libs. |
| **Conditional Statements & Loops**    | Supports `if`, `when`, `for`, `while`, and `do-while`.                                                                        |
| **Assignment & Variable Declaration** | Uses `val` (immutable) and `var` (mutable). Strong type inference.                                                            |
| **Subprograms (Functions/Methods)**   | Functions are first-class citizens, and support named/optional parameters and lambda expressions.                             |
| **Exception Handling**                | Supports `try-catch-finally`, and throws exceptions similar to Java.                                                          |
| **Unit Testing**                      | Works with `JUnit`, `Kotest`, or `Spek`. Test classes are placed under `src/test/kotlin/`.                                    |

---


## 📚 Libraries Used

1. **kotlin.io / java.io**  
   Used for reading the CSV file (`BufferedReader`) from the resources' folder.

2. **Regex (Kotlin stdlib)**  
   Used extensively for data cleaning, such as extracting years, weights, and numeric values from strings.

---

## 📊 Data Analysis Outputs

### 1. ️ What company (OEM) has the highest average weight of the phone body?
- **Answer:** HP with 453.61g

### 2. Were there any phones that were announced in one year and released in another?
- **Answer:** 3 results
  - Motorola One Hyper (Announced: 2019, Released: 2020)
  - Motorola Razr 2019 (Announced: 2019, Released: 2020)
  - Xiaomi Redmi K30 5G (Announced: 2019, Released: 2020)

### 3. How many phones have only one feature sensor?
- **Answer:** 419

### 4. What year had the most phones launched (in years after 1999)?
- **Answer:** 2019 with 251 launches

The following is the screenshot of the results:  
![The result image](/images/results.png)

---
## ✅ Unit Test Result
The following are the screenshots for the unit test.

![Test cases](/images/unit-test1.png)
![Test coverage](/images/unit-test2.png)

--- 
## 📌 Running the Program

To run the project:
1. Open in IntelliJ IDEA.
2. Place `cells.csv` in `src/main/resources/`.
3. Run the `Main.kt` file.
4. Results will be printed in the terminal; screenshots can be taken for documentation.
