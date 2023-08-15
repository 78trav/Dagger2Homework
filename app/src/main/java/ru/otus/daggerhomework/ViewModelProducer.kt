package ru.otus.daggerhomework

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ViewModelProducer (
    private val context: Context,
    private val colorGenerator: IColorGenerator,
    private val color: MutableStateFlow<Int>
) : ViewModel() {

    fun generateColor() {
        if (context !is FragmentActivity) throw RuntimeException("Здесь нужен контекст активити")
        Toast.makeText(context, "Color sent", Toast.LENGTH_SHORT).show()
        color.value = colorGenerator.generateColor()
    }
}

@Suppress("UNCHECKED_CAST")
@FragmentScope
class ViewModelProducerFactory @Inject constructor(
    @ActivityContext private val context: Context,
    private val colorGenerator: IColorGenerator,
    private val color: MutableStateFlow<Int>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ViewModelProducer(context, colorGenerator, color) as T
    }
}
