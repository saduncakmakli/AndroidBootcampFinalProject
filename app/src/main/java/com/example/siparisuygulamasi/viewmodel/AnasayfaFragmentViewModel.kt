package com.example.siparisuygulamasi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siparisuygulamasi.databinding.AnasayfaMenuCardBinding
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.repo.YemekRepository
import com.squareup.picasso.Picasso

class AnasayfaFragmentViewModel : ViewModel()  {
    var yemekListesi = MutableLiveData<List<Yemek>>()
    val yemekRepo = YemekRepository()

    init {
        Log.e("DebugFragmentVM", "AnasayfaFVM init")
        //yemekleriGuncelle()
        yemekListesi = yemekRepo.tumYemekler()
    }

    fun yemekleriGuncelle() {
        yemekRepo.yemekleriVeritabanindanGuncelle()
    }

    fun cardMenuResimGoster(resimAdi:String, desing:AnasayfaMenuCardBinding){
        Log.e("DebugFragmentVM", "Anasayfa $resimAdi card picasso image load")
        val url = "http://kasimadalan.pe.hu/yemekler/resimler/$resimAdi"
        Picasso.get().load(url).into(desing.imageViewCardMenu)
    }
}