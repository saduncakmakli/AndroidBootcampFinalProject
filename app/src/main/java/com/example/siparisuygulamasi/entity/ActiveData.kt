package com.example.siparisuygulamasi.entity

import com.example.siparisuygulamasi.retrofit.ApiState

object ActiveData {
    lateinit var kullanici_adi:String
    var menuAdapterActive = false
    var anasayfaFragmentHasNeverBeenShownYet = true


    /*
    sepetAdapterActive -> Identified due to unresolved bug :/
    The content of the recyclerview was not being displayed if the content of the RecyclerView moved beyond the screen's visible area.
    Recyclerview On scroll property seems unimportant for this bug.
     */
    var sepetAdapterActive = false
}
