package com.radlance.timesapp.di

import android.content.Context
import android.location.Geocoder
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.radlance.presentation.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton

private const val LOCATION_PREFERENCES_NAME = "location_preferences"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LOCATION_PREFERENCES_NAME
)

@Module
@InstallIn(SingletonComponent::class)
class TimesFeatureModule {

    @Provides
    @Singleton
    fun provideFusedClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context, Locale.getDefault())
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): UserPreferencesRepository {
        return UserPreferencesRepository(context.dataStore)
    }
}