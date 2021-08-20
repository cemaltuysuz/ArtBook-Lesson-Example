package com.thicapps.artbook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thicapps.artbook.R
import com.thicapps.artbook.adapter.ArtRecyclerAdapter
import com.thicapps.artbook.databinding.FragmentArtsBinding
import com.thicapps.artbook.viewmodel.ArtViewModel
import javax.inject.Inject


class FragmentArts @Inject constructor(
    val artRecyclerAdapter : ArtRecyclerAdapter
) : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding :FragmentArtsBinding? = null
    lateinit var viewModel:ArtViewModel

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.artListRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.artListRecycler.adapter = artRecyclerAdapter
        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.artListRecycler)

        binding.fab.setOnClickListener {
            val action = FragmentArtsDirections.actionFragmentArtsToFragmentDetails()
            Navigation.findNavController(it).navigate(action)
        }

    }

    private fun subscribeToObservers(){
        viewModel.artist.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}