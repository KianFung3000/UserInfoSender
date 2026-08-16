package com.example.userinfosender

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    companion object {
        private const val KEY_LAST_TAG = "last_fragment_tag"
        private const val TAG_SUMMARY = "SUMMARY"
        private const val TAG_DETAIL = "DETAIL"
    }
    // Tracking which fragment is currently shown
    private var lastTag: String = TAG_SUMMARY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        // bind button
        val btnShowSummary: Button = findViewById(R.id.btnShowSummary)
        val btnShowDetail: Button = findViewById(R.id.btnShowDetail)

        if (savedInstanceState == null) {
            // First time opening this screen -> load Summary once
            lastTag = TAG_SUMMARY
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SummaryFragment(), "SUMMARY")
                .setReorderingAllowed(true)
                .commit()
        } else {
            // rotation case -> restore lastTag
            lastTag = savedInstanceState.getString(KEY_LAST_TAG, TAG_SUMMARY)
            // FragmentManager auto-restores fragment state
        }

        // Show Summary
        btnShowSummary.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SummaryFragment(), "SUMMARY")
                .addToBackStack(null) // back button returns to previous fragment if any
                .setReorderingAllowed(true)
                .commit()
        }

        // Show Detail
        btnShowDetail.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, DetailFragment(), "DETAIL")
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .commit()
        }
    }

    // Save state for rotation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_LAST_TAG, lastTag)
    }
}
