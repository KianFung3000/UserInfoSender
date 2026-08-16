package com.example.userinfosender

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvAge: TextView = view.findViewById(R.id.tvAge)
        val tvGender: TextView = view.findViewById(R.id.tvGender)
        val tvCountry: TextView = view.findViewById(R.id.tvCountry)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)

        // Prefer Parcelable from Intent; if null, fallback to DataHolder
        val i = requireActivity().intent
        val user: User? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                i.getParcelableExtra("user_key", User::class.java)
            } else {
                @Suppress("DEPRECATION")
                i.getParcelableExtra("user_key")
            } ?: DataHolder.user

        // Show values (safe defaults if still null)
        val u = user
        tvName.text = "Name: ${u?.name ?: "-"}"
        tvAge.text = "Age: ${u?.age?.toString() ?: "-"}"
        tvGender.text = "Gender: ${u?.gender ?: "-"}"
        tvCountry.text = "Country: ${u?.country ?: "-"}"
        tvAddress.text = "Address: ${u?.address ?: "-"}"
    }
}
