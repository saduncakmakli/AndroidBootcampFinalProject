package com.example.siparisuygulamasi.runable

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.repo.SepetRepository
import com.example.siparisuygulamasi.repo.YemekRepository

val yemekRepo = YemekRepository()
val sepetRepo = SepetRepository("sadun")
fun main()
{
    yemekRepo.yemekleriVeritabanindanGuncelle()
}
