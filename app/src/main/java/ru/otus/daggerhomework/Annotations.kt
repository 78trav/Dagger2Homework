package ru.otus.daggerhomework

import javax.inject.Qualifier
import javax.inject.Scope

@Scope
annotation class ActivityScope

@Scope
annotation class FragmentScope

@Qualifier
annotation class ActivityContext

@Qualifier
annotation class ApplicationContext
