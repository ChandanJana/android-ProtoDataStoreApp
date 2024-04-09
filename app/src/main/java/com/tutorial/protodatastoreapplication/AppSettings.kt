package com.tutorial.protodatastoreapplication

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

/**
 * Created by Chandan Jana on 30-08-2023.
 * Company name: Mindteck
 * Email: chandan.jana@mindteck.com
 */
@Serializable
data class AppSettings(
    val language: Language = Language.ENGLISH,
    val knownLocations: PersistentList<Location> = persistentListOf()
)

@Serializable
data class Location(
    val lat: Double,
    val lng: Double
)

enum class Language {
    ENGLISH, GERMAN, SPANISH, BENGALI
}