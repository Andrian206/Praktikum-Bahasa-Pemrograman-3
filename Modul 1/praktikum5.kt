fun main() {
    cek_nama_hari()
    cek_khodam()
}

//fungsi cek nama hari
fun cek_nama_hari() {
    val hari = 4 // input nomor hari
    
    val namaHari = when (hari) {
        1 -> "Senin"
        2 -> "Selasa"
        3 -> "Rabu"
        4 -> "Kamis"
        5 -> "Jum'at"
        6 -> "Sabtu"
        7 -> "Minggu"
        else -> "Input hari salah"
    }

    println("Hari ini adalah hari $namaHari")
}

//fungsi cek khodam
fun cek_khodam () {
    val khodam = "Januari"
    
    when (khodam) {
        "Januari", "Februari", "Maret" -> println("Khodam Anda adalah Harimau")
        "April", "Mei", "Juni", "Juli" -> println("Khodam Anda adalah Kucing")
        "Agustus", "September", "Oktober" -> println("Khodam Anda adalah Macan Ciremai")
        "November", "Desember" -> println("Khodam Anda adalah Hiu Pantai selatan")
        else -> println("Anda tidak punya khodam")
    }
}