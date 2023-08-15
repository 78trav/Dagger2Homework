package ru.otus.daggerhomework

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {

    private lateinit var activityComponent: MainActivityComponent

    fun getComponent() = activityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent = DaggerMainActivityComponent
            .builder()
            .setApplicationComponent((application as App).getComponent())
            .setActivityContext(this)
            .build()

        setContentView(R.layout.activity_main)
    }
}

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [MainActivityModule::class]
)
interface MainActivityComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun setActivityContext(@ActivityContext context: Context): Builder
        fun setApplicationComponent(applicationComponent: ApplicationComponent): Builder
        fun build(): MainActivityComponent
    }

    @ActivityContext
    fun provideMainActivityContext(): Context

    @ApplicationContext
    fun provideApplicationContext(): Context

    fun provideColorState(): MutableStateFlow<Int>

}

@Module
object MainActivityModule {
    @ActivityScope
    @Provides
    fun providesColorState(): MutableStateFlow<Int> = MutableStateFlow(0)
}
