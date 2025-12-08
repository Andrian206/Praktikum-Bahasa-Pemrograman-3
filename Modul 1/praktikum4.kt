fun main() {
    cek_login()
}

fun cek_login(){
    val username = "admin"
    val password = "FkomJuara1"

    if (username == "admin" && password == "FkomJuara1"){
        println("Anda berhasil masuk")
    } else {
        println("Kombinasi username dan password Anda salah")
    }
}