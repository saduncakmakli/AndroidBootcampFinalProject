package com.example.siparisuygulamasi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.repo.SepetRepository
import com.example.siparisuygulamasi.repo.YemekRepository

class YemekDetayFragmentViewModel : ViewModel()  {
    var yemekListesi = MutableLiveData<List<Yemek>>()
    var sepetListesi = MutableLiveData<List<Sepet>>()
    val yemekRepo = YemekRepository()
    val sepetRepo = SepetRepository()

    init {
        Log.e("DebugFragmentVM", "YemekDetayFVM init")
        yemekleriGuncelle()
        sepetiGuncelle()
        yemekListesi = yemekRepo.tumYemekler()
        sepetListesi = sepetRepo.tumSepetler()
    }

    fun yemekleriGuncelle() { yemekRepo.yemekleriVeritabanindanGuncelle() }

    fun sepetiGuncelle() { sepetRepo.sepetiVeriTabanindanGuncelle() }

    fun yemekleriBas(){yemekRepo.yemekleriBas()}

    fun sepetiBas(){sepetRepo.sepetiBas()}

    fun sepettenYemekCikar(yemek_adi:String){ sepetRepo.sepettenYemekCikar(yemek_adi) }
}