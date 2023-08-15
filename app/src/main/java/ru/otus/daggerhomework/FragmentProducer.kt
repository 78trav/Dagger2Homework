package ru.otus.daggerhomework

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class FragmentProducer : Fragment(R.layout.fragment_a) {

    @Inject
    lateinit var factory: ViewModelProducerFactory

    private val vm by viewModels<ViewModelProducer> { factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button).setOnClickListener {
            //отправить результат через livedata в другой фрагмент
            vm.generateColor()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFragmentProducerComponent.factory().create((requireActivity() as MainActivity).getComponent()).inject(this)
    }
}

@FragmentScope
@Component(
    dependencies = [MainActivityComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentProducerComponent {

    fun inject(fragmentProducer: FragmentProducer)

    @Component.Factory
    interface Factory {
        fun create(mainActivityComponent: MainActivityComponent): FragmentProducerComponent
    }

}

@Module
interface FragmentModule {
    @Binds
    fun bindsColorGenerator(colorGenerator: ColorGeneratorImpl): IColorGenerator
}
