package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.navigation.MoviesNavigation

class ShowMovieDetailUseCase(
    private val navigationController: MoviesNavigation,
) : NoArgsUseCase<Unit> {
    override fun invoke() {
        navigationController.goToMovieDetail()
    }
}