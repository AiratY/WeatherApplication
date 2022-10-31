package ru.yunusov.weatherapplication.presentation.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.yunusov.weatherapplication.R
import ru.yunusov.weatherapplication.databinding.FragmentWeatherBinding
import ru.yunusov.weatherapplication.other.CITY_NAME
import ru.yunusov.weatherapplication.other.creatorViewModel

class WeatherFragment : Fragment(), SelectCityCallback {

    private val viewModel: WeatherViewModel by creatorViewModel { WeatherViewModel() }
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        viewModel.onViewCreated(this)

        val weekRecyclerView = binding?.weekWeatherRecyclerView
        val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        val drawableDivider =
            ResourcesCompat.getDrawable(resources, R.drawable.divider_drawable, null)
        drawableDivider?.let { dividerItemDecoration.setDrawable(it) }
        weekRecyclerView?.addItemDecoration(dividerItemDecoration)

        viewModel.throwableMessage.observe(viewLifecycleOwner) { errorMessage ->
            showMessage(errorMessage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.onDestroyView()
    }

    override fun selectNewCity() {
        findNavController().navigate(R.id.action_weatherFragment_to_selectCityFragment)
    }

    override fun getCityName(): String? {
        return requireArguments().getString(CITY_NAME, null)
    }

    /**
     * Показывает сообщение на экран
     * @param message - сообщение
     * */
    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}
