package com.example.siparisuygulamasi.repo

object CommonStaticFunctions {

    fun gonderimUcretiniHesapla(sepetUcreti: Int):Int{
        return if (sepetUcreti == 0 || sepetUcreti > 75) 0
        else 3
    }

}