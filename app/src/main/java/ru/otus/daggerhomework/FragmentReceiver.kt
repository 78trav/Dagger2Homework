package ru.otus.daggerhomework

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.Component
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentReceiver : Fragment(R.layout.fragment_b) {

    @Inject
    lateinit var factory: ViewModelReceiverFactory

    private val vm by viewModels<ViewModelReceiver> { factory }

    private lateinit var frame: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frame = view.findViewById(R.id.frame)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.getColor().collect {
                    vm.observeColors()
                    populateColor(it)
                }
            }
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFragmentReceiverComponent.factory().create((requireActivity() as MainActivity).getComponent()).inject(this)
    }

    private fun populateColor(@ColorInt color: Int) {
        frame.setBackgroundColor(color)
    }
}

@FragmentScope
@Component(
    dependencies = [MainActivityComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentReceiverComponent {

    fun inject(fragmentReceiver: FragmentReceiver)

    @Component.Factory
    interface Factory {
        fun create(mainActivityComponent: MainActivityComponent): FragmentReceiverComponent
    }

}
