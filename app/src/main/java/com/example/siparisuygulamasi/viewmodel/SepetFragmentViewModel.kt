package com.example.siparisuygulamasi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.siparisuygulamasi.entity.ActiveData
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.entity.Yemek
import com.example.siparisuygulamasi.repo.SepetRepository
import com.example.siparisuygulamasi.repo.YemekRepository
import kotlinx.coroutines.*

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

    //
    fun yemekleriGuncelle() { yemekRepo.yemekleriVeritabanindanGuncelle() }

    fun sepetiGuncelle() { sepetRepo.sepetiVeriTabanindanGuncelle() }

    fun sepettenYemekCikar(yemek_adi:String){ sepetRepo.sepettenYemekSil(yemek_adi) }

    fun siparisOlustur(yemek: Yemek, siparisAdedi:Int){ sepetRepo.sepeteEkle(yemek,siparisAdedi) }

    fun siparisOlustur(yemek_adi: String, siparisAdedi:Int){
        getYemekObject(yemek_adi)?.let {
            sepetRepo.sepeteEkle(it,siparisAdedi)
        }
    }

    fun sepetteYemegiFiltrele(yemek_adi: String, sepetListesi: List<Sepet>?): List<Sepet>?{ return sepetRepo.sepetteYemegiFiltrele(yemek_adi) }

    fun listedekiToplamSiparisAdedi(sepetListesi: List<Sepet>?) : Int{ return sepetRepo.listedekiToplamSiparisAdedi(sepetListesi) }

    fun yemekSiparisAdediniHesapla(yemek_adi: String): Int{ return listedekiToplamSiparisAdedi(sepetteYemegiFiltrele(yemek_adi,sepetListesi.value)) }

    fun duzenlenmisSepetListesi(duzensizSepetListesi:List<Sepet>?, yemekListesi:List<Yemek>?): List<Sepet>?{return sepetRepo.duzenlenmisSepetListesi(duzensizSepetListesi,yemekListesi)}

    fun duzenlenmisSepetListesi(yemekListesi:List<Yemek>?): List<Sepet>?{return sepetRepo.duzenlenmisSepetListesi(yemekListesi)}

    fun duzenlenmisSepetListesi(): List<Sepet>?{return sepetRepo.duzenlenmisSepetListesi(yemekListesi.value)}

    fun getYemekObject(yemek_adi: String):Yemek? {return yemekRepo.getYemekObject(yemek_adi)}

    fun chainingRequestSiparisiGuncelle(sepet:Sepet, adet:Int):List<Sepet>? {
        var sepet_list:List<Sepet>? = null
        runBlocking{
            launch(Dispatchers.IO){
                sepetRepo.sepetListesi.value?.let {
                    for (s in it){
                        if(s.yemek_adi == sepet.yemek_adi)
                        {
                            val yemekSilCRUDCevap = sepetRepo.syncSepettenYemekSil(s.sepet_yemek_id).body()
                            Log.e("DebugSuspend", "Sepetten Yemek Sil Api success -> ${yemekSilCRUDCevap?.success} Api message: ${yemekSilCRUDCevap?.message} ")
                        }
                    }
                }

                val yemekEkleCRUDCevap = sepetRepo.syncSepeteEkle(sepet.yemek_adi, sepet.yemek_resim_adi,sepet.yemek_fiyat,adet).body()
                Log.e("DebugSuspend", "Sepetten Yemek Ekle Api success -> ${yemekEkleCRUDCevap?.success} Api message: ${yemekEkleCRUDCevap?.message} ")

                val sepetCevap = sepetRepo.syncGuncelSepetiGetir().body()
                Log.e("DebugSuspend", "Guncel Sepet Getirldi Api success -> ${sepetCevap?.success}")
                launch(Dispatchers.Default) {
                    sepet_list = sepetCevap!!.sepet_yemekler
                }
            }
        }
        ActiveData.sepetAdapterActive = false
        return sepet_list
    }

}