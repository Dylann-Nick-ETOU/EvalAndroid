package com.example.evaldylann.movies.ui.detail

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.example.evaldylann.features.movies.domain.Movie
import com.example.evaldylann.movies.ui.components.ExpandableTextCard
import com.example.evaldylann.movies.ui.components.InfoCard
import com.example.evaldylann.movies.ui.components.MovieBanner
import com.example.evaldylann.movies.ui.components.MovieDetailError
import com.example.evaldylann.movies.ui.components.MovieDetailLoading
import org.koin.androidx.compose.koinViewModel


@Composable
fun MovieDetailRoute(
    movieId: String,
    nav: NavController? = null,
    vm: MovieDetailViewModel = koinViewModel()
) {
    LaunchedEffect(movieId) { vm.load(movieId) }
    val state by vm.state.collectAsState()

    when {
        state.loading -> MovieDetailLoading()
        state.error != null -> MovieDetailError(state.error!!) { vm.load(movieId) }
        state.movie != null -> MovieDetailScreen(state.movie!!) { nav?.popBackStack() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movie: Movie,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val lazyState = rememberLazyListState()
    val surfaceColor = MaterialTheme.colorScheme.surface

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(movie.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    scrolledContainerColor = surfaceColor.copy(alpha = 0.98f)
                )
            )
        }
    ) { padding ->
        LazyColumn(
            state = lazyState,
            contentPadding = padding,
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            // Banner avec overlay & parallax
            item {
                MovieBanner(
                    bannerUrl = movie.bannerUrl,
                    posterUrl = movie.posterUrl,
                    title = movie.title
                )
            }

            // Tags / chips
            item {
                Row(
                    Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(onClick = {}, label = { Text(movie.releaseYear) })
                    if (movie.runtimeMinutes > 0) {
                        AssistChip(onClick = {}, label = { Text("${movie.runtimeMinutes} min") })
                    }
                    if (movie.score > 0) {
                        AssistChip(onClick = {}, label = { Text("Score ${movie.score}") })
                    }
                    AssistChip(onClick = {}, label = { Text("Dir. ${movie.director}") })
                }
            }

            // Synopsis (extensible)
            item {
                ExpandableTextCard(
                    title = "Synopsis",
                    text = movie.description
                )
            }

            // Fiche technique
            item {
                InfoCard(
                    title = "Fiche",
                    rows = listOf(
                        "Titre original" to (movie.title ?: "—"),
                        "Réalisateur" to movie.director,
                        "Producteur" to movie.producer,
                        "Année" to movie.releaseYear
                    )
                )
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}
