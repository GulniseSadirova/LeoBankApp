package com.leobank.presentation.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.leobank.R
import com.leobank.databinding.FragmentProfilesBinding
import com.leobank.domain.Profiles
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfilesFragment : Fragment() {
    private lateinit var binding: FragmentProfilesBinding

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firestore: FirebaseFirestore

    @Inject
    lateinit var storage: FirebaseStorage

    private lateinit var imageUrl: ImageView
    private lateinit var buttonChange: Button
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilesBinding.inflate(inflater, container, false)

        imageUrl = binding.imageUrl
        buttonChange = binding.buttonChange
        buttonChange.setOnClickListener {
            openGallery()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firestore'dan verileri alma ve gösterme işlemi
        getProfilesFromFirestore()

        // Çıkış yap butonu
        setLogOut()
    }

    private fun getProfilesFromFirestore() {
        firestore.collection("profiles")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val profile = document.toObject(Profiles::class.java)
                    // Her bir profil için yapılacak işlemleri burada yapabilirsiniz
                    // Örneğin, TextView'lere atama yapabilirsiniz
                    binding.textName.text = profile.name
                    binding.textEpoct.text = profile.epoct
                    binding.textNumber.text = profile.phone

                    // Profil resmini yüklemek için Glide ya da Picasso gibi kütüphaneleri kullanabilirsiniz
                    // Örnek Glide kullanımı:
                     Glide.with(requireContext()).load(profile.imageUrl).into(binding.imageUrl)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("ProfilesFragment", "Error getting profiles", exception)
            }
    }

    private fun setLogOut() {
        binding.logOut.setOnClickListener {
            firebaseAuth.signOut()
            findNavController().navigate(R.id.action_profilesFragment_to_numberAddFragment)
        }
    }
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            imageUrl.setImageURI(imageUri)
            // Firebase Storage'a yükleme işlemini buraya ekleyebilirsiniz
           uploadImageToFirebaseStorage(imageUri)

        }
    }


    // Firebase Storage'a resim yükleme fonksiyonu
     private fun uploadImageToFirebaseStorage(imageUri: Uri) {
         val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("images/bmw.jpg")

         val uploadTask = imagesRef.putFile(imageUri)

         uploadTask.addOnFailureListener {
    //         // Handle unsuccessful uploads
         }.addOnSuccessListener { taskSnapshot ->
             // Handle successful uploads
       }
     }


}