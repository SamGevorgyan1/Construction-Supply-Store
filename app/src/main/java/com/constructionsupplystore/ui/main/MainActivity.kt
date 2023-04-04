package com.constructionsupplystore.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import com.common.basecommon.BaseCommonActivity
import com.constructionsupplystore.R
import com.constructionsupplystore.databinding.ActivityMainBinding
import com.constructionsupplystore.ui.fragment.AddProductFragment
import com.constructionsupplystore.ui.fragment.ProductsFragment


class MainActivity : BaseCommonActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.bottomNavigationView.setOnItemSelectedListener {
            val fragment: Fragment = when (it.itemId) {
                R.id.home -> ProductsFragment()
                R.id.add -> AddProductFragment()
                else -> ProductsFragment()
            }
            supportFragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit()
            true
        }

        binding.bottomNavigationView.selectedItemId = R.id.home
    }
}