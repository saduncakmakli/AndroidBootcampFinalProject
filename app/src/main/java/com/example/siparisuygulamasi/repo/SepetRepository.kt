package com.example.siparisuygulamasi.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.siparisuygulamasi.entity.*
import com.example.siparisuygulamasi.retrofit.ApiUtils
import com.example.siparisuygulamasi.retrofit.SepetDaoInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SepetRepository () {
    var sepetListesi:MutableLiveData<List<Sepet>>
    var sepetDataAccessObject:SepetDaoInterface

    init {
        sepetDataAccessObject = ApiUtils.getSepetDaoInterface()
        sepetListesi = MutableLiveData()
    }

    fun tumSepetler() : MutableLiveData<List<Sepet>>{
        return sepetListesi
    }

    //API PRIMARY-FUNCTION
    fun sepeteEkle(yemek_adi:String, yemek_resim_adi:String, yemek_fiyat:Int, yemek_siparis_adet:Int){
        sepetDataAccessObject.sepeteYemekEkle(yemek_adi,yemek_resim_adi,yemek_fiyat,yemek_siparis_adet,ActiveData.kullanici_adi).enqueue(object : Callback<CRUDCevap>{
            override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                val success = response.body().success
                val mesaj = response.body().message
                Log.e("DebugRepo", "Sepete Yemek Ekle Api success -> $success Api message: $mesaj ")
            }
            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                Log.e("DebugRepo", "Sepete Yemek Ekle Api Failure")
            }
        })
    }

    fun sepeteEkle(yemek:Yemek, siparisAdet:Int){
        sepeteEkle(yemek.yemek_adi,yemek.yemek_resim_adi,yemek.yemek_fiyat,siparisAdet)
    }

    fun sepetiVeriTabanindanGuncelle(){
        sepetDataAccessObject.sepettekiYemekleriGetir(ActiveData.kullanici_adi).enqueue(object : Callback<SepetCevap>{
            override fun onResponse(call: Call<SepetCevap>?, response: Response<SepetCevap>) {
                val success = response.body().success
                val liste = response.body().sepet_yemekler
                sepetListesi.value = liste
                Log.e("DebugRepo", "Sepetteki Yemekleri Getir Api success -> $success")
            }
            override fun onFailure(call: Call<SepetCevap>?, t: Throwable?) {
                Log.e("DebugRepo", "Sepetteki Yemekleri Getir Api Failure")
                sepetListesi.value = null
            }
        })
    }

    fun sepettenYemekSil(sepet_yemek_id:Int){
        sepetDataAccessObject.sepettenYemekSil(sepet_yemek_id,ActiveData.kullanici_adi).enqueue(object : Callback<CRUDCevap>{
            override fun onResponse(call: Call<CRUDCevap>?, response: Response<CRUDCevap>) {
                val success = response.body().success
                val mesaj = response.body().message
                Log.e("DebugRepo", "Sepetten Yemek Sil Api success -> $success Api message: $mesaj ")
            }

            override fun onFailure(call: Call<CRUDCevap>?, t: Throwable?) {
                Log.e("DebugRepo", "Sepetten Yemek Sil Api Failure")
            }
        })
    }

    fun sepettenYemekSil(yemek_adi:String){
        if (sepetListesi.value != null)
            for (s in sepetListesi.value!!){
                if (s.yemek_adi == yemek_adi) sepettenYemekSil(s.sepet_yemek_id)
            }
    }

    //TURETILMIS/DERIVED FONKSIYONLAR

    fun sepetiBas(){ sepetListesi.value?.let {  for (sepet in sepetListesi.value!!) Log.e("DebugFragmentVM", "Sepet ID:${sepet.sepet_yemek_id} Ad:${sepet.yemek_adi}") } }
    //---
    fun sepetteYemegiFiltrele(yemek_adi: String, sepetListesi: List<Sepet>?): List<Sepet>?{
        if (sepetListesi != null ){
            val localSepetListesi = sepetListesi.toList() //Parametre olarak gelen listeyi kopyalamak için
            val returnList = ArrayList<Sepet>()
            for (s in localSepetListesi){
                if (s.yemek_adi == yemek_adi){
                    returnList.add(s)
                }
            }
            return returnList
        }
        else return null
    }

    fun sepetteYemegiFiltrele(yemek_adi: String): List<Sepet>?{ return sepetteYemegiFiltrele(yemek_adi,sepetListesi.value) }
    //---
    fun listedekiToplamSiparisAdedi(sepetListesi: List<Sepet>?) : Int{
        var count = 0
        if (sepetListesi != null)
            for (s in sepetListesi){
                count += s.yemek_siparis_adet
            }
        return count
    }

    fun listedekiToplamSiparisAdedi(sepetListesi: List<Sepet>?, yemek_adi: String) : Int{ return listedekiToplamSiparisAdedi(sepetteYemegiFiltrele(yemek_adi, sepetListesi)) }

    fun listedekiToplamSiparisAdedi() : Int{ return listedekiToplamSiparisAdedi(sepetListesi.value)}

    fun listedekiToplamSiparisAdedi(yemek_adi: String) : Int{ return listedekiToplamSiparisAdedi(sepetListesi.value, yemek_adi) }
    //---
    fun toplamSepetUcretiniHesapla() : Int{
        var count = 0
        sepetListesi.value?.let{
            for (s in it){
                count += s.yemek_siparis_adet*s.yemek_fiyat
            }
        }
        return count
    }

    fun toplamSepetUcretiniHesapla(sepetListesi: List<Sepet>?) : Int {
        var count = 0
        if (sepetListesi != null)
            for (s in sepetListesi) {
                count += s.yemek_siparis_adet * s.yemek_fiyat
            }
        return count
    }
    //---

    /*
    fun duzenlenmisSepetListesiDondur(duzensizSepetListesi:List<Sepet>?, yemekListesi:List<Yemek>): List<Sepet>?{
        var duzenliSepet = ArrayList<Sepet>()
        for (yemek in yemekListesi){

        }
    }
    */



}