package com.tikhomirov.kanimdsl.processor.annotations

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class KAnimation(
    val name:String
)