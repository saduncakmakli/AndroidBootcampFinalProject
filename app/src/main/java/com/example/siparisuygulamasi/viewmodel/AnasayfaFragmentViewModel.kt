package com.example.siparisuygulamasi.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.repo.CommonStaticFunctions
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

    //Guncelleme Methodları
    fun yemekleriGuncelle() { yemekRepo.yemekleriVeritabanindanGuncelle() }

    fun sepetiGuncelle() { sepetRepo.sepetiVeriTabanindanGuncelle() }

    //LiveData Yazdırma Methodları
    fun yemekleriBas(){yemekRepo.yemekleriBas()}

    fun sepetiBas(){sepetRepo.sepetiBas()}

    //APILER
    fun yemekSiparisAdediniHesapla(yemek_adi: String): Int{ return listedekiToplamSiparisAdedi(sepetListesi.value, yemek_adi) }

    fun sepetteYemegiFiltrele(yemek_adi: String, sepetListesi: List<Sepet>?): List<Sepet>?{ return sepetRepo.sepetteYemegiFiltrele(yemek_adi) }

    fun listedekiToplamSiparisAdedi(sepetListesi: List<Sepet>?) : Int{ return sepetRepo.listedekiToplamSiparisAdedi(sepetListesi) }

    fun listedekiToplamSiparisAdedi(sepetListesi: List<Sepet>?, yemek_adi: String) : Int{ return listedekiToplamSiparisAdedi(sepetteYemegiFiltrele(yemek_adi,sepetListesi)) }

    //Ucret Methodları
    fun toplamSepetUcretiniHesapla() : Int{ return sepetRepo.toplamSepetUcretiniHesapla() }

    fun toplamSepetUcretiniYazdır() : String{ return "Sepet Ücreti: ${toplamSepetUcretiniHesapla()}₺" }

    fun sepetinGonderimUcretiniHesapla():Int{ return CommonStaticFunctions.gonderimUcretiniHesapla(toplamSepetUcretiniHesapla()) }

    fun sepetinGonderimUcretiniYazdır():String{
        val sepetUcreti = toplamSepetUcretiniHesapla()
        val gonderimUcreti = sepetinGonderimUcretiniHesapla()
        return if (sepetUcreti < 75) "Gönderim Ücreti: ${gonderimUcreti}₺" else "75₺ Üzeri Alışverişe, GÖNDERİM ÜCRETİ YOK!"
    }

    fun toplamUcretiHesapla(): Int{ return toplamSepetUcretiniHesapla()+sepetinGonderimUcretiniHesapla()}

    fun toplamUcretiYazdır(): String{return "Toplam Ücret: ${toplamUcretiHesapla()}₺"}

    //Directions
    fun sepeteGec(){

    }


}