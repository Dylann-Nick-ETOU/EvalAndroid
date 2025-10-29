package com.example.evaldylann.movies.ui.model


/**
 * Modèle utilisé pour l’affichage dans l’interface.
 *
 * C’est une version simplifiée de [Movie] (domaine) avec uniquement
 * les champs nécessaires pour la liste et le détail.
 */
data class MovieUiModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val image: String? = null
)