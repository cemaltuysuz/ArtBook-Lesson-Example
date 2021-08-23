package com.thicapps.artbook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.thicapps.artbook.R
import com.thicapps.artbook.adapter.ImageRecyclerAdapter
import com.thicapps.artbook.databinding.FragmentImageApiBinding
import com.thicapps.artbook.util.Status
import com.thicapps.artbook.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentImageApi @Inject constructor(
    val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment() {

    lateinit var viewModel: ArtViewModel
    private var fragmentBinding:FragmentImageApiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_image_api, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        var job:Job ? = null

        binding.searchBar.addTextChangedListener{
            job?.cancel()
            job = lifecycleScope.launch{
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }

        subscribeOnToObservers()

        binding.imagesRecycler.adapter = imageRecyclerAdapter
        binding.imagesRecycler.layoutManager = GridLayoutManager(requireContext(),3)
        imageRecyclerAdapter.setOnClickListener {
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()
        }

    }

    private fun subscribeOnToObservers(){
        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS ->{
                    val url = it.data?.hits?.map { imageResult ->
                    imageResult.previewURL
                    }
                    imageRecyclerAdapter.images = url ?: listOf()
                    fragmentBinding?.imageApiProgress?.visibility = View.GONE
                }
                Status.ERROR -> {

                    Toast.makeText(requireContext(),"Error ! ${it.message}", Toast.LENGTH_SHORT).show()
                    fragmentBinding?.imageApiProgress?.visibility = View.GONE
                }
                Status.LOADING -> {
                    fragmentBinding?.imageApiProgress?.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}