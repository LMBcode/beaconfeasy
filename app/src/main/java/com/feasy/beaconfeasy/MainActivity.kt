package com.feasy.beaconfeasy

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.feasy.beaconfeasy.adapter.BeaconAdapter
import com.feasy.beaconfeasy.databinding.ActivityMainBinding
import com.feasycom.feasybeacon.logic.model.Beacon

class MainActivity : AppCompatActivity() {

    private val mBeaconList = mutableListOf<Beacon>()
    private lateinit var binding : ActivityMainBinding
    private val mDeviceAdapter: BeaconAdapter by lazy {
        BeaconAdapter(this, mBeaconList).apply {
            mOnItemClickListener = {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}