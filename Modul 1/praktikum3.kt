//fungsi standar
fun ucapkan_salam() {
    println("Assalamu'alaikum...selamat siang warga fkom semuanya")
}

// fungsi dengan argument
fun salam(nama: String) {
    println("Hallo Nama saya, $nama")
}

// fungsi dengan nilai return
fun kali(a:Int, b:Int) : Int {
    return a * b
}

// lanjutan kode sebelumnya
fun tambah(a:Int, b:Int) : Int = a + b

fun bagi(a:Float, b:Float) : Unit {
    println("Pembagian antara $a dan $b adalah ${a/b}")
}

fun kurang(a:Int, b:Int) { // Int return type assumed, can be Unit if no explicit return
    println("Pengurangan antara $a dan $b adalah ${a-b}")
}

fun main() {
    println("======fungsi standar kotlin======")
    //cara memanggil fungsi
    ucapkan_salam()
    salam("Dede")
    
    print("Hasil perkalian antara 3 dan 10 adalah = ")
    println(kali(3, 10))
    
    println("Hasil pertambahan dari 20 ditambah 10 adalah - ${tambah(20, 10)}")
    bagi(4f, 2f) // Menggunakan 'f' untuk Float
    kurang(13, 9)
}