package com.example.siparisuygulamasi.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siparisuygulamasi.databinding.SepetCardBinding
import com.example.siparisuygulamasi.entity.ActiveData.sepetRecyclerviewDisplayCorrectly
import com.example.siparisuygulamasi.entity.Sepet
import com.example.siparisuygulamasi.fragment.SepetFragment
import com.example.siparisuygulamasi.picasso.PicassoUtils
import com.example.siparisuygulamasi.viewmodel.SepetFragmentViewModel

class SepetAdapter(var mContext: Context,
                   var viewModel:SepetFragmentViewModel,
                   var fragment:SepetFragment,
                   val sepetListesi:List<Sepet>
                   ) : RecyclerView.Adapter<SepetAdapter.CardDesingHolder>() {

    init {
        Log.e("DebugAdapter", "SepetAdapterInit")
    }

    inner class CardDesingHolder(desing: SepetCardBinding) : RecyclerView.ViewHolder(desing.root){
        var desing : SepetCardBinding
        init {
            this.desing = desing
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesingHolder {
        val layoutInflater = LayoutInflater.from(mContext)
        val desing = SepetCardBinding.inflate(layoutInflater, parent, false)

        Log.e("DebugAdapter", "SepetAdapter")
        sepetRecyclerviewDisplayCorrectly = true
        return CardDesingHolder(desing)
    }

    override fun onBindViewHolder(holder: CardDesingHolder, position: Int) {
        val sepet = sepetListesi.get(position)
        val cardDesing = holder.desing
        cardDesing.sepetObject = sepet
        cardDesing.sepetFragment = fragment
        PicassoUtils.yemekResimGoster(sepet.yemek_resim_adi, cardDesing.imageViewItem)

    }

    override fun getItemCount(): Int {
        sepetListesi?.let {
            Log.e("DebugAdapter", "SepetAdapter-getItemCount ${it.size}")
            return it.size }
        Log.e("DebugAdapter", "SepetAdapter-getItemCount Zero0")
        return 0
    }
}