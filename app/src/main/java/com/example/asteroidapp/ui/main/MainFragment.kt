package com.example.asteroidapp.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidapp.R
import com.example.asteroidapp.api.RetrofitClient
import com.example.asteroidapp.databinding.FragmentMainBinding
import com.example.asteroidapp.db.AsteroidRoomDatabase
import com.example.asteroidapp.repository.NasaRepositoryImp


class MainFragment : Fragment() {

    private lateinit var binding:FragmentMainBinding


    private lateinit var asteroidsAdapter: AsteroidAdapter

    // Lazily initialize viewModel (MainViewModel)

    private val viewModel: MainViewModel by viewModels {MainViewModelFactory(requireContext())}

    /* inflate the layout with data Binding , sets its lifeCycle owner to MainFragment
       to enable data binding to observe liveData and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater)


        // Allow Data binding to observe LiveData with the lifeCycle of this fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the MainViewModel

        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        setUpAdapter()
        viewModelObserver()

        return binding.root
    }

    // inflates the overflow menu that contains filtering options

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.main_overflow_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
           R.id.show_all_menu-> viewModel.filterAsteroids(MainViewModel.FilterOption.ALL)
               R.id.show_week_menu-> viewModel.filterAsteroids(MainViewModel.FilterOption.WEEKLY)
            R.id.show_today_menu-> viewModel.filterAsteroids(MainViewModel.FilterOption.TODAY)

        }
        return true
    }

    private fun viewModelObserver() {
        viewModel.filterOption.observe(viewLifecycleOwner){
            viewModel.asteroids.observe(viewLifecycleOwner) {
                asteroidsAdapter.submitList(it)
            }
        }

    }

    private fun setUpAdapter() {
        asteroidsAdapter = AsteroidAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionShowDetail(it)
            )
        }
        binding.asteroidRecycler.adapter = asteroidsAdapter

    }


}
