package com.example.siparisuygulamasi.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.fragment.AnasayfaFragmentDirections
import com.example.siparisuygulamasi.repo.YemekRepository

class AnasayfaFragmentViewModel : ViewModel()  {
    var yemekListesi = MutableLiveData<List<Yemek>>()
    val yemekRepo = YemekRepository()
    var menuAdapterActive = false

    init {
        Log.e("DebugFragmentVM", "AnasayfaFVM init")
        //yemekleriGuncelle()
        yemekListesi = yemekRepo.tumYemekler()
    }

    fun yemekleriGuncelle() {
        yemekRepo.yemekleriVeritabanindanGuncelle()
    }

    fun detaySayfasınaGec(kullaniciAdi:String, yemekNesnesi:Yemek, v:View){
        menuAdapterActive = false
        //--------------------------------!!! Yemek Adedi Hesap Edilip Gönderilecek.
        val direction = AnasayfaFragmentDirections.actionAnasayfaFragmentToYemekDetayFragment(kullaniciAdi,yemekNesnesi,0)
        Navigation.findNavController(v).navigate(direction)
    }
}