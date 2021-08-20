package com.thicapps.artbook.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import com.thicapps.artbook.R
import com.thicapps.artbook.databinding.FragmentArtsBinding
import com.thicapps.artbook.ui.FragmentArtsDirections


class FragmentArts : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding :FragmentArtsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        binding.fab.setOnClickListener {
            val action = FragmentArtsDirections.actionFragmentArtsToFragmentDetails()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun onDestroy() {
        fragmentBinding = null
        super.onDestroy()
    }
}