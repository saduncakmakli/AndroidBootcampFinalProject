package com.example.siparisuygulamasi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.repo.SepetRepository
import com.example.siparisuygulamasi.repo.YemekRepository

class SepetFragmentViewModel : ViewModel()  {
    var yemekListesi = MutableLiveData<List<Yemek>>()
    var sepetListesi = MutableLiveData<List<Sepet>>()
    val yemekRepo = YemekRepository()
    val sepetRepo = SepetRepository()

    init {
        Log.e("DebugFragmentVM", "SepetFVM init")
        yemekleriGuncelle()
        sepetiGuncelle()
        yemekListesi = yemekRepo.tumYemekler()
        sepetListesi = sepetRepo.tumSepetler()
    }

    fun yemekleriGuncelle() { yemekRepo.yemekleriVeritabanindanGuncelle() }

    fun sepetiGuncelle() { sepetRepo.sepetiVeriTabanindanGuncelle() }

    fun yemekleriBas(){yemekRepo.yemekleriBas()}

    fun sepetiBas(){sepetRepo.sepetiBas()}

    fun sepettenYemekCikar(yemek_adi:String){ sepetRepo.sepettenYemekSil(yemek_adi) }

    fun siparisOlustur(yemek: Yemek, siparisAdedi:Int){ sepetRepo.sepeteEkle(yemek,siparisAdedi) }

    fun sepetteYemegiFiltrele(yemek_adi: String, sepetListesi: List<Sepet>?): List<Sepet>?{ return sepetRepo.sepetteYemegiFiltrele(yemek_adi) }

    fun listedekiToplamSiparisAdedi(sepetListesi: List<Sepet>?) : Int{ return sepetRepo.listedekiToplamSiparisAdedi(sepetListesi) }

    fun yemekSiparisAdediniHesapla(yemek_adi: String): Int{ return listedekiToplamSiparisAdedi(sepetteYemegiFiltrele(yemek_adi,sepetListesi.value)) }

}