package com.example.siparisuygulamasi.picasso

import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso

class PicassoUtils {
    companion object{
        fun yemekResimGoster(imageFileName:String, imageView: ImageView){
            //Log.e("DebugPicassoUtils", "$imageFileName (Yemek) picasso image loaded")
            val url = "http://kasimadalan.pe.hu/yemekler/resimler/$imageFileName"
            Picasso.get().load(url).into(imageView)
        }
    }
}