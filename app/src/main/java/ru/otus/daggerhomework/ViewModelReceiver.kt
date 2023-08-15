package ru.otus.daggerhomework

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class ViewModelReceiver @Inject constructor (
    private val context: Context,
    private val color: StateFlow<Int>
) : ViewModel() {

    fun getColor() = color.filter { it != 0 }

    fun observeColors() {
        if (context !is Application) throw RuntimeException("Здесь нужен контекст апликейшена")
        Toast.makeText(context, "Color received", Toast.LENGTH_SHORT).show()
    }

}

@Suppress("UNCHECKED_CAST")
@FragmentScope
class ViewModelReceiverFactory @Inject constructor(
    @ApplicationContext private val context: Context,
    private val color: MutableStateFlow<Int>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelReceiver(context, color.asStateFlow()) as T
    }
}
