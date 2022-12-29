package com.example.fypcanteensystem

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.fypcanteensystem.databinding.ActivityVendorRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class VendorRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVendorRegistrationBinding
    private lateinit var auth : FirebaseAuth
    private var databaseReference : DatabaseReference? = null
    private var database : FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVendorRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("vendorProfile")

        binding.vendorSignUpButton.setOnClickListener()
        {

            if(TextUtils.isEmpty(binding.fullNameEditText.text.toString())){
                binding.fullNameContainer.setError("*Required!")
                return@setOnClickListener
            }
            else{
                binding.fullNameContainer.error = null
            }

            if(TextUtils.isEmpty(binding.emailEditText.text.toString())){
                binding.emailContainer.setError("*Required!")
                return@setOnClickListener
            }
            else if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString()).matches()){
                binding.emailContainer.setError("*Invalid Email Address!")
                return@setOnClickListener
            }
            else{
                binding.emailContainer.error = null
            }

            if(TextUtils.isEmpty(binding.passwordEditText.text.toString())) {
                binding.passwordContainer.setError("*Required!")
                binding.passwordContainer.errorIconDrawable = null
                return@setOnClickListener
            }
            else if(binding.passwordEditText.text.toString().length < 6){
                binding.passwordContainer.setError("*Minimum 6 Character Password!")
                binding.passwordContainer.errorIconDrawable = null
                return@setOnClickListener
            }
            else{
                binding.passwordContainer.error = null
            }

            if(TextUtils.isEmpty(binding.phoneEditText.text.toString())){
                binding.phoneContainer.setError("*Required!")
                return@setOnClickListener
            }
            else if(!binding.phoneEditText.text.toString().matches("^(\\+?6?01)[02-46-9]-*[0-9]{7}\$|^(\\+?6?01)[1]-*[0-9]{8}\$".toRegex())){
                binding.phoneContainer.setError("*Invalid Phone Number format!")
                return@setOnClickListener
            }
            else{
                binding.phoneContainer.error = null
            }

            if(TextUtils.isEmpty(binding.rentalCodeEditText.text.toString())){
                binding.rentalCodeContainer.setError("*Required!")
                return@setOnClickListener
            }
            else if(!binding.rentalCodeEditText.text.toString().matches("^(East|West)-[0-9]{4}\$".toRegex())){
                binding.rentalCodeContainer.setError("*Invalid Rental Code format!")
                return@setOnClickListener
            }
            else{
                binding.rentalCodeContainer.error = null
            }

            if(TextUtils.isEmpty(binding.merchantNameEditText.text.toString())){
                binding.merchantNameContainer.setError("*Required!")
                return@setOnClickListener
            }
            else{
                binding.merchantNameContainer.error = null
            }


            registerVendor(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString(),
                    binding.fullNameEditText.text.toString(), binding.phoneEditText.text.toString(),
                    binding.merchantNameEditText.text.toString(), binding.rentalCodeEditText.text.toString())



        }


        this.setTitle("Vendor Sign Up")


        val actionbar = supportActionBar
        //actionbar!!.title = "My Cart"
        actionbar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun registerVendor(email: String, psw: String, name: String, phoneNo:String, merchantName: String, code: String) {
        auth.createUserWithEmailAndPassword(email,psw)
            .addOnCompleteListener{
                if(it.isSuccessful)
                {
                    val currentUser = auth.currentUser
                    val currentUserDb = databaseReference?.child((currentUser?.uid!!))
                    currentUserDb?.child("Full Name")?.setValue(name)
                    currentUserDb?.child("Phone Number")?.setValue(phoneNo)
                    currentUserDb?.child("Merchant Name")?.setValue(merchantName)
                    currentUserDb?.child("Rental Code")?.setValue(code)
                    currentUserDb?.child("E-wallet Balance")?.setValue("1000")
                    Toast.makeText(this@VendorRegistrationActivity, "Registration Successful", Toast.LENGTH_LONG).show()
                    finish()
                }
                else
                {
                    Toast.makeText(this@VendorRegistrationActivity, it.exception?.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }




//        if(!passwordText.matches(".*[A-Z].*".toRegex()))
//        {
//            return "Must Contain 1 Upper-case Character"
//        }
//        if(!passwordText.matches(".*[a-z].*".toRegex()))
//        {
//            return "Must Contain 1 Lower-case Character"
//        }
//        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
//        {
//            return "Must Contain 1 Special Character (@#\$%^&+=)"
//        }





}