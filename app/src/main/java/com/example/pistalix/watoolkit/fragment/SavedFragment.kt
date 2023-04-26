package com.example.pistalix.watoolkit.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.StatusAdapter
import com.example.pistalix.watoolkit.databinding.FragmentSavedBinding
import java.io.File

class SavedFragment : Fragment() {
    private lateinit var savedFragmentBinding: FragmentSavedBinding
    fun getSavedData(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            getAndroidFiles(context)
        } else {
            val recyclerViewMediaAdapter = StatusAdapter(
                context,
                getListFiles(
                    File(
                        Environment.getExternalStorageDirectory().toString() +"/Download/" +getString(R.string.app_name)
                    )
                ),true)
            savedFragmentBinding.savedStatusList.adapter = recyclerViewMediaAdapter
        }
    }

    private fun getAndroidFiles(c: Context) {
        val folder = File(
            Environment.getExternalStorageDirectory().toString() +"/Download/" +getString(R.string.app_name))
        Log.d("pathsave", folder.toString())
        val files = folder.listFiles()
        if (files != null) {
            Log.d("filesSize", files.size.toString())
            val inFiles = ArrayList<String>()
            for (file: File in files) {
                Log.e("savedCheck", file.name)
                if ((file.name.endsWith(".jpg") ||
                            file.name.endsWith(".gif") ||
                            file.name.endsWith(".mp4"))
                ) {
                    if (!inFiles.contains(file.absolutePath)) inFiles.add(file.absolutePath)
                }
            }
            val recyclerViewMediaAdapter = StatusAdapter(c, inFiles,true)
            savedFragmentBinding.savedStatusList.adapter = recyclerViewMediaAdapter
        }
    }

    private fun getListFiles(parentDir: File): ArrayList<String> {
        val inFiles = ArrayList<String>()
        val files = parentDir.listFiles()
        Log.d("parent", parentDir.toString())
        if (files != null) {
            Log.d("de", files.size.toString())
            for (file: File in files) {
                Log.e("check", file.name)
                if ((file.name.endsWith(".jpg") ||
                            file.name.endsWith(".gif") ||
                            file.name.endsWith(".mp4"))
                ) {
                    if (!inFiles.contains(file.absolutePath)) inFiles.add(file.absolutePath)
                }
            }
        }
        return inFiles
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?):View {
        // Inflate the layout for this fragment
        savedFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_saved, container, false
        )
        getSavedData(savedFragmentBinding.root.context)
       return savedFragmentBinding.root
    }


}