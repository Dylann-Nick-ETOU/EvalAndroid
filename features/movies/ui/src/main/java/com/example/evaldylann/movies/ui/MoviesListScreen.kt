package com.example.evaldylann.movies.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.evaldylann.movies.ui.model.MovieUiModel

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@Composable
fun MoviesListRoute(
    vm: MoviesViewModel,
    nav: NavController
) {
    val ctx = LocalContext.current
    val state by vm.state.collectAsState()
    LaunchedEffect(Unit) { vm.dispatch(MoviesAction.Load) }

    MoviesListScreen(
        state = state,
        onRefresh = { vm.dispatch(MoviesAction.Refresh) },
        onItemClick = { id ->
            vm.dispatch(MoviesAction.ClickSound(id))
            Toast.makeText(ctx, "Open detail: $id", Toast.LENGTH_SHORT).show()
            nav.navigate("movie/$id")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesListScreen(
    state: MoviesState,
    onRefresh: () -> Unit,
    onItemClick: (String) -> Unit
) {
    val gradient = Brush.horizontalGradient(
        listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Studio Ghibli",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                ),
                modifier = Modifier
                    .background(gradient)
                    .statusBarsPadding()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onRefresh,
                containerColor = Color(0xFF2C5364),
                contentColor = Color.White
            ) {
                Icon(Icons.Rounded.Refresh, contentDescription = "Refresh")
            }
        },
        containerColor = Color(0xFF0B0C10)
    ) { padding ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF66FCF1)
                    )
                }

                state.error != null -> {
                    Text(
                        "Erreur : ${state.error}",
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.items, key = { it.id }) { item ->
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(),
                                exit = fadeOut()
                            ) {
                                MovieCardItem(item, onClick = { onItemClick(item.id) })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MovieCardItem(
    item: MovieUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2833)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Box(Modifier.fillMaxSize()) {
            // Image + placeholder léger
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(item.image)
                    .crossfade(true)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Dégradé pour lisibilité du texte
            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(Color.Transparent, Color(0xCC0B0C10))
                        )
                    )
            )

            // Texte du film
            Column(
                Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    item.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    item.subtitle,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFFB0BEC5)),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
