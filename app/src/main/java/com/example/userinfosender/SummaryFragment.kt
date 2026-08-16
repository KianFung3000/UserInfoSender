package com.example.userinfosender

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class SummaryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvSummary: TextView = view.findViewById(R.id.tvSummary)

        // Read data sent to SecondActivity
        val i = requireActivity().intent

        // (1) Simple extras
        val name = i.getStringExtra("name_key") ?: "-"
        val age = i.getIntExtra("age_key", -1)

        // (2) Bundle
        val address = i.extras?.getString("address_key") ?: "-"
        val gender = i.extras?.getString("gender_key") ?: "-"
        val country = i.extras?.getString("country_key") ?: "-"

        // (3) Parcelable
        val userFromParcelable: User? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                i.getParcelableExtra("user_key", User::class.java)
            } else {
                @Suppress("DEPRECATION")
                i.getParcelableExtra("user_key")
            }

        // (4) Optional singleton
        val userFromSingleton = DataHolder.user

        val text = """
            Summary ✅

            (Extras)
            Name: $name
            Age: $age

            (Bundle)
            Address: $address
            Gender: $gender
            Country: $country

            (Parcelable)
            User: ${userFromParcelable}

            (Singleton)
            DataHolder.user: ${userFromSingleton}
        """.trimIndent()

        tvSummary.text = text
    }
}
