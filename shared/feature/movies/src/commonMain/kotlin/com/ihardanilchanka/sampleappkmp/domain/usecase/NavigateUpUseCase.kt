package com.ihardanilchanka.sampleappkmp.domain.usecase

import com.ihardanilchanka.sampleappkmp.domain.NavigationController
import com.ihardanilchanka.sampleappkmp.domain.NavigationDestination

class NavigateUpUseCase(
    private val navigationController: NavigationController,
) : NoArgsUseCase<Unit> {

    override fun invoke() {
        navigationController.navigate(NavigationDestination.Up)
    }
}