package com.thicapps.artbook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.thicapps.artbook.R
import com.thicapps.artbook.databinding.FragmentDetailsBinding
import com.thicapps.artbook.dependencyinjection.AppModule
import com.thicapps.artbook.util.Resource
import com.thicapps.artbook.util.Status
import com.thicapps.artbook.viewmodel.ArtViewModel
import javax.inject.Inject

class FragmentDetails @Inject constructor(
    val glide:RequestManager
) : Fragment(R.layout.fragment_details) {

    private var fragmentBinding : FragmentDetailsBinding? = null
    lateinit var viewModel : ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()
        binding.detailsArtHolder.setOnClickListener {
            findNavController().navigate(FragmentDetailsDirections.actionFragmentDetailsToFragmentImageApi())
        }

        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.detailsArtSave.setOnClickListener{
            viewModel.makeArt(binding.detailsArtName.toString(),binding.detailsArtistName.toString()
                     ,binding.detailsArtYear.toString())
        }
    }

    private fun subscribeToObservers(){

        viewModel.selectedUrl.observe(viewLifecycleOwner, Observer { url ->
        fragmentBinding?.let {
            glide.load(url).into(it.detailsArtImage)
            Toast.makeText(requireContext(),"$url !", Toast.LENGTH_SHORT).show()
        }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(),"Success !", Toast.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(),"Error !", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                }
            }
        })
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}