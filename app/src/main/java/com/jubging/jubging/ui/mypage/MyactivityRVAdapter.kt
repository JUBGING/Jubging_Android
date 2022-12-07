package com.jubging.jubging.ui.mypage

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jubging.jubging.data.remote.myactivity.MyactivityResponse
import com.jubging.jubging.databinding.ItemMyactivityBinding
import com.mummoom.md.data.remote.auth.JubjubiResponse

class MyactivityRVAdapter(val context: Context) : RecyclerView.Adapter<MyactivityRVAdapter.ViewHolder> () {

    private val myactivityList = ArrayList<MyactivityResponse>()


    inner class ViewHolder(val binding: ItemMyactivityBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(myactivityResponse: MyactivityResponse){
            Log.d("활동응답",myactivityResponse.toString())
            Glide.with(context)
                .load(myactivityResponse.imgUrl)
                .into(binding.itemMyactivityIv)

//            binding.itemMyactivityDateTv.text = myactivityResponse.date
            binding.itemMyactivityTimeData.text = myactivityResponse.time
            binding.itemMyactivityWalkData.text = myactivityResponse.walk
            binding.itemMyactivityDistanceData.text = myactivityResponse.distance
            binding.itemMyactivityWeightData.text = myactivityResponse.weight
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyactivityRVAdapter.ViewHolder {
        val binding: ItemMyactivityBinding = ItemMyactivityBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyactivityRVAdapter.ViewHolder, position: Int) {
        holder.bind(myactivityList[position])
    }

    override fun getItemCount(): Int = myactivityList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addActivity(jubgingList: ArrayList<MyactivityResponse>){

        Log.d("활동응답",jubgingList.toString())
        this.myactivityList.clear()
        this.myactivityList.addAll(jubgingList)

        notifyDataSetChanged()
    }

}