package com.example.siparisuygulamasi.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class YemekCevap (@SerializedName("yemekler") @Expose var yemekler:List<Yemek>,
                       @SerializedName("success") @Expose var success:Int){}