package com.example.siparisuygulamasi.repo

import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.entity.Yemek

object CommonStaticFunctions {

    fun gonderimUcretiniHesapla(sepetUcreti: Int):Int{
        return if (sepetUcreti == 0 || sepetUcreti > 75) 0
        else 3
    }

}