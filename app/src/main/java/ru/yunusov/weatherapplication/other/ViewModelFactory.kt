package ru.yunusov.weatherapplication.other

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

typealias Creator = () -> ViewModel?

class ViewModelFactory(private val creator: Creator = { null }) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator() as T
    }
}

inline fun <reified VM : ViewModel> Fragment.creatorViewModel(noinline creator: Creator): Lazy<VM> {
    return viewModels { ViewModelFactory(creator) }
}
