package com.convity.presentation.home.view.activity

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.convity.R
import com.convity.databinding.ActivityHomeBinding
import com.convity.presentation.base.BaseActivity
import com.convity.presentation.home.view.adapter.HomePagerAdapter
import com.convity.presentation.home.viewmodel.HomeViewModel
import com.convity.presentation.newpost.filterpost.view.FilterPostActivity
import com.convity.utility.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding

    private val currentViewModel: HomeViewModel by viewModel()
    override fun getBaseViewModel() = currentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initAdapter()
        initListener()
    }

    private fun initListener() {
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.tab_home -> {
                    binding.homeViewPager.currentItem = 0
                    binding.bottomNavigationView.menu.findItem(R.id.tab_home).isChecked = true
                    window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                R.id.tab_search -> {
                    binding.homeViewPager.currentItem = 1
                    binding.bottomNavigationView.menu.findItem(R.id.tab_search).isChecked = true
                    window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                R.id.tab_notification -> {
                    binding.homeViewPager.currentItem = 2
                    binding.bottomNavigationView.menu.findItem(R.id.tab_notification).isChecked =
                        true
                    window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
                R.id.tab_profile -> {
                    binding.homeViewPager.currentItem = 3
                    binding.bottomNavigationView.menu.findItem(R.id.tab_profile).isChecked = true
                    window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
                    window.decorView.systemUiVisibility =
                        (window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
                }
            }
            false
        }

        binding.addPost.setOnSafeClickListener {
            if (checkPermission()) {
                startActivityForResult(
                    getPickImageChooserIntent(
                        "Select source",
                        false,
                    ), 100
                )
            } else {
                requestPermission()
            }

        }
    }

    private fun getPickImageChooserIntent(
        title: CharSequence?,
        includeDocuments: Boolean
    ): Intent? {
        val allIntents: MutableList<Intent> = ArrayList()
        var galleryIntents = getGalleryIntents(packageManager, Intent.ACTION_PICK, includeDocuments)
        if (galleryIntents.isEmpty()) {
            galleryIntents =
                getGalleryIntents(packageManager, Intent.ACTION_GET_CONTENT, includeDocuments)
        }
        allIntents.addAll(galleryIntents)
        val target: Intent
        if (allIntents.isEmpty()) {
            target = Intent()
        } else {
            target = allIntents[allIntents.size - 1]
            allIntents.removeAt(allIntents.size - 1)
        }
        val chooserIntent = Intent.createChooser(target, title)
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toTypedArray<Parcelable>())
        return chooserIntent
    }

    private fun getGalleryIntents(
        packageManager: PackageManager, action: String, includeDocuments: Boolean
    ): List<Intent> {
        val intents: MutableList<Intent> = ArrayList()
        val galleryIntent = if (action === Intent.ACTION_GET_CONTENT) Intent(action) else Intent(
            action,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        galleryIntent.type = "image/*"
        val listGallery = packageManager.queryIntentActivities(galleryIntent, 0)
        for (res in listGallery) {
            val intent = Intent(galleryIntent)
            intent.component = ComponentName(res.activityInfo.packageName, res.activityInfo.name)
            intent.setPackage(res.activityInfo.packageName)
            intents.add(intent)
        }
        if (!includeDocuments) {
            for (intent in intents) {
                if (intent.component!!.className == "com.android.documentsui.DocumentsActivity") {
                    intents.remove(intent)
                    break
                }
            }
        }
        return intents
    }


    private fun initAdapter() {
        val homePagerAdapter = HomePagerAdapter(supportFragmentManager)
        binding.homeViewPager.adapter = homePagerAdapter
        binding.homeViewPager.setSwipePagingEnabled(false)
        binding.homeViewPager.offscreenPageLimit = 5
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 2296) {
            if (checkPermission()) {
                startActivityForResult(
                    getPickImageChooserIntent(
                        "Select source",
                        false,
                    ), 100
                )
            } else {
                toast("Please allow storage permission!")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                val imageUri = data!!.data
                val imagePath: String = PathUtil.getPath(this, imageUri!!)!!
                startActivity(
                    Intent(this, FilterPostActivity::class.java).putExtra(
                        "path",
                        imagePath
                    )
                )
            } else if (requestCode == 2296) {
                if (checkPermission()) {
                    startActivityForResult(
                        getPickImageChooserIntent(
                            "Select source",
                            false,
                        ), 100
                    )
                } else {
                    toast("Please allow storage permission!")
                }
            }
        }
    }
}