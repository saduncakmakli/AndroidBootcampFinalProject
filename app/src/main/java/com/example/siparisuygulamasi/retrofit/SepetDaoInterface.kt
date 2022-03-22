package com.example.siparisuygulamasi.retrofit

import com.example.siparisuygulamasi.entity.CRUDCevap
import com.example.siparisuygulamasi.entity.SepetCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SepetDaoInterface {
    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded
    fun sepeteYemekEkle(
        @Field("yemek_adi") yemek_adi:String,
        @Field("yemek_resim_adi") yemek_resim_adi:String,
        @Field("yemek_fiyat") yemek_fiyat:Int,
        @Field("yemek_siparis_adet") yemek_siparis_adet:Int,
        @Field("kullanici_adi") kullanici_adi:String
    ) : Call<CRUDCevap>

    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    fun sepettekiYemekleriGetir(
        @Field("kullanici_adi") kullanici_adi:String
    ) : Call<SepetCevap>

    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    fun sepettenYemekSil(
        @Field("sepet_yemek_id") sepet_yemek_id:Int,
        @Field("kullanici_adi") kullanici_adi:String
    ) : Call<CRUDCevap>

}