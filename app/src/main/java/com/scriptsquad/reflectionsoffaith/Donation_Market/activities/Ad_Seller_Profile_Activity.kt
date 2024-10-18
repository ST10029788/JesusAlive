package com.scriptsquad.reflectionsoffaith.Donation_Market.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.scriptsquad.reflectionsoffaith.R
import com.scriptsquad.reflectionsoffaith.Utilities.Utils
import com.scriptsquad.reflectionsoffaith.Donation_Market.adapter.AdapterAd
import com.scriptsquad.reflectionsoffaith.databinding.ActivityAdSellerProfileBinding
import com.scriptsquad.reflectionsoffaith.Donation_Market.model.ModelAd

class Ad_Seller_Profile_Activity : AppCompatActivity() {

    private var sellerUid = ""
    private lateinit var binding:ActivityAdSellerProfileBinding

    private companion object{
        private const val TAG = "SELLER_PROFILE_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityAdSellerProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sellerUid = intent.getStringExtra("sellerUid").toString()

        loadSellerDetails()
        loadAds()

        binding.toolbarBackbtn.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun loadSellerDetails(){
        Log.d(TAG, "loadSellerDetails: ")

        val ref =FirebaseDatabase.getInstance().getReference("Users")
        ref.child(sellerUid)
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val name ="${snapshot.child("name").value}"
                    val profileImageUrl = "${snapshot.child("profileImageURl").value}"
                    val timestamp = snapshot.child("timestamp").value as Long

                    val formattedDate = Utils.formatTimestampDate(timestamp)

                    binding.sellerNameTv.text=name
                    binding.sellerMemberSinceTv.text=formattedDate
                    try {
                        Glide.with(this@Ad_Seller_Profile_Activity)
                            .load(profileImageUrl)
                            .placeholder(R.drawable.ic_person_white)
                            .into(binding.sellerProfileIv)

                    }catch (e:Exception){
                        Log.e(TAG, "onDataChange: ",e )
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }


            })
    }

    private fun loadAds(){
        Log.d(TAG, "loadAds: ")
        val adArrayList:ArrayList<ModelAd> = ArrayList()

        val ref =FirebaseDatabase.getInstance().getReference("Ads")
        ref.orderByChild("uid").equalTo(sellerUid)
            .addValueEventListener(object:ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    adArrayList.clear()

                    for (ds in snapshot.children){
                    try {
                        val modelAd =ds.getValue(ModelAd::class.java)


                        adArrayList.add(modelAd!!)

                    }   catch (e:java.lang.Exception){
                        Log.e(TAG, "onDataChange: ",e )
                    }

                        val adapterAd = AdapterAd(this@Ad_Seller_Profile_Activity,adArrayList)
                        binding.adsRv.adapter=adapterAd


                        val adsCount = "${adArrayList.size}"
                        binding.publishedAdsCountTv.text=adsCount
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }
}
//method used from YouTube
//https://youtu.be/mNKQ9dc1knI?si=Zl5SD0u9dZ3kjbey
//Philipp Lackner
