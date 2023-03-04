package com.feasy.beaconfeasy.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.arrayMapOf
import androidx.recyclerview.widget.RecyclerView
import com.feasy.beaconfeasy.R
import com.feasy.beaconfeasy.databinding.ItemBeaconBinding
import com.feasycom.bean.FeasyBeacon
import com.feasycom.feasybeacon.logic.model.Beacon

class BeaconAdapter(private val context: Context, private val mBeaconList: MutableList<Beacon>) : RecyclerView.Adapter<BeaconAdapter.ViewHolder>(){

    var mOnItemClickListener :((position: Int)-> Unit)? = null

 //   var isFold = ShowBroadcastStatus.NONE

    var defaultState = View.GONE

    private val mBroadcastMap =  arrayMapOf<String, MutableList<FeasyBeacon>>()

  //  private val mBroadcastAdapterMap = arrayMapOf<String, BroadcastAdapter>()

    class ViewHolder(val binding: ItemBeaconBinding): RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = mBeaconList.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beacon = mBeaconList[position]
        with(holder.binding){
            addressTv.text = beacon.mDevice.address
            nameTv.text = beacon.mDevice.completeLocalName ?: beacon.mDevice.name ?: "unknown name"
            rssiTv.text = "${beacon.mDevice.rssi}dBm"

            /*if(mBroadcastMap[beacon.mDevice.address] == null){
                mBroadcastMap[beacon.mDevice.address] = mutableListOf()
            }*/

            mBroadcastMap[beacon.mDevice.address] = mutableListOf()

            /*var broadcastAdapter = mBroadcastAdapterMap[beacon.mDevice.address]

            if(broadcastAdapter == null){
                broadcastAdapter = BroadcastAdapter(mBroadcastMap[beacon.mDevice.address]!!)
                mBroadcastAdapterMap[beacon.mDevice.address] = broadcastAdapter

            }*/


            if (beacon.mDevice.getiBeacon() != null){
                if (!mBroadcastMap[beacon.mDevice.address]!!.contains(beacon.mDevice.getiBeacon())){
                    mBroadcastMap[beacon.mDevice.address]!!.add(beacon.mDevice.getiBeacon())
                    broadcastList.adapter!!.notifyItemChanged(mBroadcastMap[beacon.mDevice.address]!!.size)
                }
            }

            if (beacon.mDevice.getgBeacon() != null ){
                if (!mBroadcastMap[beacon.mDevice.address]!!.contains(beacon.mDevice.getgBeacon())){
                    mBroadcastMap[beacon.mDevice.address]!!.add(beacon.mDevice.getgBeacon())
                    broadcastList.adapter!!.notifyItemChanged(mBroadcastMap[beacon.mDevice.address]!!.size)
                }
            }
            if (beacon.mDevice.altBeacon != null){
                if (!mBroadcastMap[beacon.mDevice.address]!!.contains(beacon.mDevice.altBeacon)){
                    mBroadcastMap[beacon.mDevice.address]!!.add(beacon.mDevice.altBeacon)
                    broadcastList.adapter!!.notifyItemChanged(mBroadcastMap[beacon.mDevice.address]!!.size)
                }
            }

            val intervalNanos = beacon.intervalNanos / 1000000
            if (intervalNanos > 0){
                intervalTv.text = "$intervalNanos ms <-> "
                intervalTv.setTextColor(context.resources.getColor(R.color.black))
            }else {
                intervalTv.text = "N/A <-> "
                intervalTv.setTextColor(context.resources.getColor(R.color.purple_200))
            }

          /*  if (isFold == ShowBroadcastStatus.FOLD || isFold != beacon.isFold){
                showBroadcast(beacon)
            }else {
                when(isFold){
                    ShowBroadcastStatus.FOLD -> {
                        broadcastList.visibility = View.GONE
                    }
                    ShowBroadcastStatus.EXPANSION -> {
                        broadcastList.visibility = View.VISIBLE
                    }
                    else -> {
                        // 默认显示
                        broadcastList.visibility = defaultState
                    }
                }
            }

           */

           /* holder.itemView.setOnClickListener {
                if (broadcastList.visibility == View.GONE){
                    broadcastList.visibility = View.VISIBLE
                    isFold = ShowBroadcastStatus.NONE
                    beacon.isFold = ShowBroadcastStatus.EXPANSION
                }else {
                    broadcastList.visibility = View.GONE
                    isFold = ShowBroadcastStatus.NONE
                    beacon.isFold = ShowBroadcastStatus.FOLD
                }
                notifyItemChanged(position, " ")
            }

            */
        }
    }


    // 局部刷新
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any?>) {
        if (payloads.isEmpty()){
            onBindViewHolder(holder, position)
        }
        val beacon = mBeaconList[position]
        with(holder.binding){
            rssiTv.text = "${beacon.mDevice.rssi}dB"
            val intervalNanos = beacon.intervalNanos / 1000000
            if (intervalNanos > 0){
                intervalTv.text = "$intervalNanos ms <-> "
                intervalTv.setTextColor(context.resources.getColor(R.color.black))
            }else {
                intervalTv.text = "N/A <-> "
                intervalTv.setTextColor(context.resources.getColor(R.color.purple_200))
            }

            val mutableList = mBroadcastMap[beacon.mDevice.address]
            if (mutableList != null){
                if (beacon.mDevice.getiBeacon() != null){
                    if (!mutableList.contains(beacon.mDevice.getiBeacon())){
                        mutableList.add(beacon.mDevice.getiBeacon())
                        broadcastList.adapter!!.notifyItemChanged(mutableList.size)
                    }
                }
                if (beacon.mDevice.getgBeacon() != null ){
                    if (!mutableList.contains(beacon.mDevice.getgBeacon())){
                        mutableList.add(beacon.mDevice.getgBeacon())
                        broadcastList.adapter!!.notifyItemChanged(mutableList.size)
                    }
                }
                if (beacon.mDevice.altBeacon != null){
                    if (!mutableList.contains(beacon.mDevice.altBeacon)){
                        mutableList.add(beacon.mDevice.altBeacon)
                        broadcastList.adapter!!.notifyItemChanged(mutableList.size)
                    }
                }
                mBroadcastMap[beacon.mDevice.address] = mutableList
            }else {
                val beaconList = mutableListOf<FeasyBeacon>()
                if (beacon.mDevice.getiBeacon() != null){
                    beaconList.add(beacon.mDevice.getiBeacon())
                    broadcastList.adapter!!.notifyItemChanged(beaconList.size)
                }
                if (beacon.mDevice.getgBeacon() != null ){
                    beaconList.add(beacon.mDevice.getgBeacon())
                    broadcastList.adapter!!.notifyItemChanged(beaconList.size)
                }
                if (beacon.mDevice.altBeacon != null){
                    beaconList.add(beacon.mDevice.altBeacon)
                    broadcastList.adapter!!.notifyItemChanged(beaconList.size)
                }
                mBroadcastMap[beacon.mDevice.address] = beaconList
            }

        /*    if (isFold != beacon.isFold){
                showBroadcast(beacon)
            }else {
                when(isFold){
                    ShowBroadcastStatus.FOLD -> {
                        broadcastList.visibility = View.GONE
                    }
                    ShowBroadcastStatus.EXPANSION -> {
                        broadcastList.visibility = View.VISIBLE
                    }
                    ShowBroadcastStatus.NONE -> {
                        // 默认显示
                        broadcastList.visibility = defaultState
                    }
                }
            }

         */
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBeaconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        viewHolder.binding.root.setOnClickListener {
            val position = viewHolder.adapterPosition
            if (position >= 0 ){
            }
        }
        return viewHolder
    }

    override fun getItemId(position: Int): Long {
        return mBeaconList[position].mDevice.timestampNanos;
    }
}