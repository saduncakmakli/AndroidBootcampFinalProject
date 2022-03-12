package com.example.siparisuygulamasi.fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.siparisuygulamasi.R
import com.example.siparisuygulamasi.adapter.AnasayfaMenuAdapter
import com.example.siparisuygulamasi.databinding.FragmentAnasayfaBinding
import com.example.siparisuygulamasi.viewmodel.AnasayfaFragmentViewModel

class AnasayfaFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var desing:FragmentAnasayfaBinding
    private lateinit var viewModel:AnasayfaFragmentViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        desing= DataBindingUtil.inflate(inflater, R.layout.fragment_anasayfa, container, false)
        desing.anasayfaFragment = this

        //TOOLBAR
        desing.toolbarTitle = "Menü"
        (activity as AppCompatActivity).setSupportActionBar(desing.toolbarAnasayfa)

        //
        val bundle:AnasayfaFragmentArgs by navArgs()
        desing.welcomeMessage = "Hoşgeldin ${bundle.kullaniciAdi}."

        //ADAPTER
        desing.rvMenu.layoutManager = StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL)
        viewModel.yemekListesi.observe(viewLifecycleOwner) {
            val adapter = AnasayfaMenuAdapter(requireContext(),it,viewModel)
            Log.e("DebugFragment", "Anasayfa Menu Adapter Updated")
            desing.menuAdapter = adapter
        }

        //Slider Oto-Slide
        var firstStart = true
        val timer = object: CountDownTimer(4000, 2000) {
            override fun onTick(millisUntilFinished: Long) {
                if ((desing.textViewWelcome.visibility == View.VISIBLE) && !firstStart){
                    desing.textViewWelcome.visibility = View.GONE
                    Log.e("DebugFragment", "Welcome message is gone.")
                }
                Log.e("DebugFragment", "Timer tick")
                firstStart = false
            }
            override fun onFinish() {}
        }
        timer.start()

        return desing.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        val tempViewModel : AnasayfaFragmentViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_anasayfa, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.yemekleriGuncelle()
    }


}