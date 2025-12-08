fun main() {
print("Masukkan nilai ujian: ")
    val inputString = readLine()

    val nilai = inputString?.toDoubleOrNull()
    println("\n=====Hasil Nilai=====")

    if (nilai != null) {
        println("Nilai $nilai mendapatkan predikat Nilai " + nilai_predikat(nilai))
    } else {
        println("Input tidak valid.")
    }
}

fun nilai_predikat(nilai: Double): String {
    val predikat = when (nilai) {
        in 80.0..100.0 -> "A"
        in 60.0..79.0 -> "B"
        in 50.0..59.0 -> "C"
        in 0.0..49.0 -> "D"
        else -> "Nilai tidak valid"
    }
    return predikat
}