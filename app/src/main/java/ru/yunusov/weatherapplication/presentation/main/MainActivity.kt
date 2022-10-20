package ru.yunusov.weatherapplication.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import ru.yunusov.weatherapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: MainViewModel by viewModels()

        viewModel.fragment.observe(this) { fragment ->
            replaceFragment(fragment)
        }
    }

    /**
     * Меняет фрагмент
     * @param fragment - новый фрагмент
     * */
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, fragment)
        }
    }
}
