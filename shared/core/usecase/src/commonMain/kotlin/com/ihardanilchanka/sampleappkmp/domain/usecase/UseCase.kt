package com.ihardanilchanka.sampleappkmp.domain.usecase

interface UseCase<in ARG, out RESULT> {
    operator fun invoke(arg: ARG): RESULT
}

interface NoArgsUseCase<out RESULT> {
    operator fun invoke(): RESULT
}

interface SuspendUseCase<in ARG, out RESULT> {
    suspend operator fun invoke(arg: ARG): RESULT
}

interface SuspendNoArgsUseCase<out RESULT> {
    suspend operator fun invoke(): RESULT
}
