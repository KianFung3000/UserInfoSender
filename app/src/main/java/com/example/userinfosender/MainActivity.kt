package com.example.userinfosender

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etAddress: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var spCountry: Spinner
    private lateinit var btnSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1) Bind views
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        etAddress = findViewById(R.id.etAddress)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        spCountry = findViewById(R.id.spCountry)
        btnSubmit = findViewById(R.id.btnSubmit)

        // 2) Setup Spinner with countries from arrays.xml
        val countries = resources.getStringArray(R.array.countries)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        spCountry.adapter = adapter

        // 3) Submit button logic
        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val ageText = etAge.text.toString().trim()
            val address = etAddress.text.toString().trim()

            // get gender
            val gender = when (rgGender.checkedRadioButtonId) {
                R.id.rbMale -> "Male"
                R.id.rbFemale -> "Female"
                else -> ""
            }

            // simple validations
            if (name.isEmpty()) {
                etName.error = "Required"
                return@setOnClickListener
            }
            if (ageText.isEmpty()) {
                etAge.error = "Required"
                return@setOnClickListener
            }
            val age = ageText.toIntOrNull()
            if (age == null || age < 0) {
                etAge.error = "Invalid age"
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                etAddress.error = "Required"
                return@setOnClickListener
            }
            if (gender.isEmpty()) {
                Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val country = spCountry.selectedItem?.toString() ?: ""

            // Create User object
            val user = User(
                name = name,
                age = age,
                address = address,
                gender = gender,
                country = country
            )

            // intent to SecondActivity
            val intent = Intent(this, SecondActivity::class.java)

            // 1) putExtras
            intent.putExtra("name_key", name)
            intent.putExtra("age_key", age)

            // 2) Bundle
            val bundle = Bundle().apply {
                putString("address_key", address)
                putString("gender_key", gender)
                putString("country_key", country)
            }
            intent.putExtras(bundle)

            // 3) Parcelable
            intent.putExtra("user_key", user)

            // 4) Singleton
            DataHolder.user = user

            startActivity(intent)
        }
    }
}
