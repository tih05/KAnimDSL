package com.tikhomirov.kotanimdsl

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.View

class AnimationBuilder(
    private val view: View,
    private var mode: BuildMode = BuildMode.TOGETHER
) {
    private val animatorList = mutableListOf<Animator>()


    fun <N : Number, T : Animation<N>> addAnimation(animation: T, lambda: T.() -> Unit) {
        animation
            .apply { target = view }
            .apply(lambda)
        animatorList.add(
            animation.getAnimator()
        )
    }

    private fun addAnimator(animator: Animator) = animatorList.add(animator)


    fun together(lambda: AnimationBuilder.() -> Unit) {
        val builder = AnimationBuilder(view).apply(lambda)
        addAnimator(builder.build())
    }

    fun sequentially(lambda: AnimationBuilder.() -> Unit) {
        val builder =
            AnimationBuilder(
                view = view,
                mode = BuildMode.SEQUENTIALLY
            )
                .apply(lambda)
        addAnimator(builder.build())
    }


    fun build(): Animator {
        val set = AnimatorSet()
        var maxDuration: Long = 0L
        var sumDuration: Long = 0L
        animatorList.map {
            if(it.duration>maxDuration)
                maxDuration = it.duration
            sumDuration += it.duration
        }

        return when (mode) {
            BuildMode.TOGETHER ->
                AnimatorSet().apply {
                    playTogether(animatorList)
                    duration = maxDuration
                }
            BuildMode.SEQUENTIALLY ->
                AnimatorSet().apply {
                    playSequentially(animatorList)
                    duration = sumDuration
                }
        }


    }

    enum class BuildMode {
        TOGETHER,
        SEQUENTIALLY
    }
}


fun View.animateTogether(lambda: AnimationBuilder.() -> Unit): Animator {
    val animationBuilder = AnimationBuilder(this)
    return animationBuilder
        .apply(lambda)
        .build()

}

fun View.animateSequentially(lambda: AnimationBuilder.() -> Unit): Animator {
    val animationBuilder = AnimationBuilder(this, AnimationBuilder.BuildMode.SEQUENTIALLY)
    return animationBuilder
        .apply(lambda)
        .build()
}