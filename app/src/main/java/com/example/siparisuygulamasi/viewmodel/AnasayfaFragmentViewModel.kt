package com.example.siparisuygulamasi.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.fragment.AnasayfaFragmentDirections
import com.example.siparisuygulamasi.repo.SepetRepository
import com.example.siparisuygulamasi.repo.YemekRepository

class AnasayfaFragmentViewModel : ViewModel()  {
    var yemekListesi = MutableLiveData<List<Yemek>>()
    var sepetListesi = MutableLiveData<List<Sepet>>()
    val yemekRepo = YemekRepository()
    val sepetRepo = SepetRepository()

    init {
        Log.e("DebugFragmentVM", "AnasayfaFVM init")
        yemekleriGuncelle()
        sepetiGuncelle()
        yemekListesi = yemekRepo.tumYemekler()
        sepetListesi = sepetRepo.tumSepetler()
    }

    fun yemekleriGuncelle() { yemekRepo.yemekleriVeritabanindanGuncelle() }

    fun sepetiGuncelle() { sepetRepo.sepetiVeriTabanindanGuncelle() }

    fun yemekSiparisAdediniHesapla(yemek_adi: String): Int{ return listedekiToplamSiparisAdedi(sepetteYemegiFiltrele(yemek_adi,sepetListesi.value)) }

    fun sepetteYemegiFiltrele(yemek_adi: String, sepetListesi: List<Sepet>?): List<Sepet>?{ return sepetRepo.sepetteYemegiFiltrele(yemek_adi,sepetListesi) }

    fun listedekiToplamSiparisAdedi(sepetListesi: List<Sepet>?) : Int{ return sepetRepo.listedekiToplamSiparisAdedi(sepetListesi) }

    fun toplamSepetUcretiniHesapla(sepetListesi: List<Sepet>?) : Int{ return sepetRepo.toplamSepetUcretiniHesapla(sepetListesi) }

    fun gonderimUcretiniHesapla(sepetUcreti: Int):Int{
        return if (sepetUcreti == 0 || sepetUcreti > 75) 0
        else 3
    }

    fun yemekleriBas(){yemekRepo.yemekleriBas()}

    fun sepetiBas(){sepetRepo.sepetiBas()}
}