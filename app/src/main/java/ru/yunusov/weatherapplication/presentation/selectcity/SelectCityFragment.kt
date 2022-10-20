package ru.yunusov.weatherapplication.presentation.selectcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.databinding.FragmentSelectCityBinding
import ru.yunusov.weatherapplication.other.creatorViewModel
import ru.yunusov.weatherapplication.presentation.main.MainViewModel

class SelectCityFragment : Fragment(), ViewForecast {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val viewModel: SelectCityViewModel by creatorViewModel { SelectCityViewModel(this) }
    private var _binding: FragmentSelectCityBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectCityBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.viewmodel = viewModel
        binding?.lifecycleOwner = this

        val recyclerView = binding?.resultSearchCityRecyclerView
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val drawableDivider =
            ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
        drawableDivider?.let { dividerItemDecoration.setDrawable(it) }
        recyclerView?.addItemDecoration(dividerItemDecoration)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showForecast(city: String) {
        mainViewModel.showWeather(city)
    }

    companion object {
        /**
         * Возвращает экземпляр SelectCityFragment
         * */
        fun newInstance() = SelectCityFragment()
    }
}
