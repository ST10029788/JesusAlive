package com.scriptsquad.reflectionsoffaith.Church_Events_Feed.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.scriptsquad.reflectionsoffaith.databinding.ActivityGalleryBinding
import com.scriptsquad.reflectionsoffaith.Church_Events_Feed.fragments.PictureFragment
import com.scriptsquad.reflectionsoffaith.Church_Events_Feed.fragments.VideoFragment

class Socials_Gallery_Activity : AppCompatActivity() {

    // Late-initialized variable for the activity's binding
    private lateinit var binding: ActivityGalleryBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityGalleryBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.toolbarBackBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        showPicturesFragment()

        binding.pictureBtn.setOnClickListener{
            showPicturesFragment()
        }
        binding.videoBtn.setOnClickListener {
            showVideoFragment()
        }

    }

    // Show the pictures fragment
    private fun showPicturesFragment() {
        val fragment = PictureFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsFl.id, fragment)
        fragmentTransaction.commit()
    }

    // Show the video fragment
    private fun showVideoFragment() {
        val fragment = VideoFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentsFl.id, fragment)
        fragmentTransaction.commit()
    }

}