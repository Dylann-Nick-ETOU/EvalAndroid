package com.example.evaldylann.features.movies.domain
/**
 * Récupère tous les films.
 *
 * @return la liste de Movie(film)
 * @throws IOException si le réseau est indisponible
 */
class GetMovieByIdUseCase(private val repo: MovieRepository) {
    suspend operator fun invoke(id: String) = repo.getMovie(id)
}
