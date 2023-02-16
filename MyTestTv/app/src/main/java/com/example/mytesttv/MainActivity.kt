package com.example.mytesttv

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.mytesttv.MvpView.MainFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment, MainFragment())
                .commitNow()
        }
    }
}