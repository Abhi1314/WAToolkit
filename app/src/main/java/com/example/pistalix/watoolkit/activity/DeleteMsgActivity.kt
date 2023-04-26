package com.example.pistalix.watoolkit.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pistalix.watoolkit.R
import com.example.pistalix.watoolkit.databinding.ActivityDeleteMsgBinding

class DeleteMsgActivity : AppCompatActivity() {
    private val deleteMsgBinding:ActivityDeleteMsgBinding by lazy {
        DataBindingUtil.setContentView(this,R.layout.activity_delete_msg)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       deleteMsgBinding.toolBarLayout.name="Deleted Chat"
        deleteMsgBinding.toolBarLayout.backImage.setOnClickListener { finish() }
    }


}