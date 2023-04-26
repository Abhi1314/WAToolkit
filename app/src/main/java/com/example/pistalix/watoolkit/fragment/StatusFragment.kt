package com.example.pistalix.watoolkit.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.adapter.Android11StatusAdapter
import com.example.pistalix.watoolkit.adapter.StatusAdapter
import com.example.pistalix.watoolkit.databinding.FragmentStatusBinding
import java.io.File

class StatusFragment : Fragment() {
    var WHATSAPP_STATUS_LOCATION = "/WhatsApp/Media/.Statuses"
    private lateinit var statusFragmentBinding: FragmentStatusBinding
    fun getAndroidFile(getSaveTreeUri: String, context: Context) {
        val allFiles: Array<DocumentFile>? = getFromSdcard(getSaveTreeUri, context)
        val inFiles = ArrayList<String>()

        if (allFiles == null) {
            Log.d("LogFetch", "all data array empty")
        } else {
            var i = 0
            while (true) {
                val documentFileArr: Array<DocumentFile> = allFiles
                if (i > documentFileArr.size - 1) {
                    break
                }
                if (!documentFileArr[i].uri.toString().contains(".nomedia")) {
                    inFiles.add(allFiles[i].uri.toString())
                }
                i++
            }
            val recyclerViewMediaAdapter = Android11StatusAdapter(context, inFiles)
            statusFragmentBinding.rvStatusList.adapter = recyclerViewMediaAdapter
        }
    }

    fun getFromSdcard(getSaveTreeUri: String, context: Context): Array<DocumentFile>? {
        val fromTreeUri = DocumentFile.fromTreeUri(context, Uri.parse(getSaveTreeUri))
        return if (fromTreeUri == null || !fromTreeUri.exists() || !fromTreeUri.isDirectory || !fromTreeUri.canRead() || !fromTreeUri.canWrite()) {
            null
        } else fromTreeUri.listFiles()
    }

    fun setsaveuri(treeUri: String, context: Context) {
        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        val prefsEditor = sharedPreferences.edit()
        prefsEditor.putString("treeUri", treeUri).apply()
    }

    fun getSaveTreeUri(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("treeUri", "")
    }

    private fun getListFiles(parentDir: File): ArrayList<String> {
        val inFiles = ArrayList<String>()
        val files = parentDir.listFiles()
        Log.d("parent", parentDir.toString())
        if (files != null) {
            Log.d("de", files.size.toString())
            for (file in files) {
                Log.e("check", file.name)
                if (file.name.endsWith(".jpg") ||
                    file.name.endsWith(".gif") ||
                    file.name.endsWith(".mp4")
                ) {
                    if (!inFiles.contains(file.absolutePath)) inFiles.add(file.absolutePath)
                }
            }
        }
        return inFiles
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        statusFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_status, container, false
        )
        val someActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val treeUri = result.data
                Log.d("mees","result")
                statusFragmentBinding.root.context.contentResolver.takePersistableUriPermission(
                    treeUri!!.data!!,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
                setsaveuri(treeUri.data.toString(), statusFragmentBinding.root.context)
                getAndroidFile(
                    treeUri.data.toString(),
                    statusFragmentBinding.root.context
                )
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val saveTreeUri = getSaveTreeUri(statusFragmentBinding.root.context)
            if (saveTreeUri == "") {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
                val whatsappuri =
                    Uri.parse("content://com.android.externalstorage.documents/document/primary%3A" + "Android%2Fmedia%2Fcom.whatsapp%2FWhatsApp%2FMedia%2F.Statuses");
                intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, whatsappuri)
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION)
                someActivityResultLauncher.launch(intent)
            } else {
                getAndroidFile(saveTreeUri!!, statusFragmentBinding.root.context)
            }
        } else {
            val recyclerViewMediaAdapter = StatusAdapter(
                statusFragmentBinding.root.context, getListFiles(
                    File(
                        Environment.getExternalStorageDirectory().toString()
                                + WHATSAPP_STATUS_LOCATION
                    )
                )
           ,false )
            statusFragmentBinding.rvStatusList.adapter = recyclerViewMediaAdapter
        }
        return statusFragmentBinding.root
    }


}