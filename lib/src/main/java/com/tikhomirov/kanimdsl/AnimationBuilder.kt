package com.tikhomirov.kanimdsl

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View

class AnimationBuilder(
    private val view: View,
    private var mode: BuildMode = BuildMode.TOGETHER
) {
    private val animatorList = mutableListOf<Animator>()
    var startDelay: Long = 0L
    var repeatCount = 0
    var repeatMode: Int = 1

    //Callbacks
    var onStart: (() -> Unit)? = null
    var onEnd: (() -> Unit)? = null
    var onCancel: (() -> Unit)? = null
    var onRepeat: (() -> Unit)? = null


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
        var maxDuration = 0L
        var sumDuration = 0L
        animatorList.map {
            if (it.duration > maxDuration)
                maxDuration = it.duration
            sumDuration += it.duration
        }

        animatorList.map {
            if (it is ObjectAnimator) {
                it.repeatMode = this.repeatMode
                it.repeatCount = this.repeatCount
            }
        }
        set.startDelay = this.startDelay
        set.setCallbacks()

        return when (mode) {
            BuildMode.TOGETHER ->
                set.apply {
                    playTogether(animatorList)
                    duration = maxDuration
                }
            BuildMode.SEQUENTIALLY ->
                set.apply {
                    playSequentially(animatorList)
                    duration = sumDuration
                }
        }
    }

    private fun AnimatorSet.setCallbacks() {
        val hasCallbacks =
            onStart != null ||
                    onEnd != null ||
                    onCancel != null ||
                    onRepeat != null

        if (hasCallbacks) {
            val animationListener = AnimationCallbackListener(
                onStart = onStart,
                onEnd = onEnd,
                onCancel = onCancel,
                onRepeat = onRepeat
            )
            this.addListener(animationListener)
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

//fun AnimatorSet.invert(animator: AnimatorSet = this, mode: Int = animator.): AnimatorSet {
//    when (animator) {
//
//        is AnimatorSet -> {
//            val childs = animator.childAnimations
//            val animationList = mutableListOf<Animator>()
//
//            when (mode) {
//                AnimationBuilder.BuildMode.TOGETHER -> {
//                    for (animation in childs) {
//                        when (animation) {
//                            is ValueAnimator -> {
//                                val reversedArray = animation.values.reversedArray()
//                                animation.setValues(*reversedArray)
//                            }
//                            is AnimatorSet -> {
//                                animation.invert()
//                            }
//                        }
//                    }
//                }
//
//                AnimationBuilder.BuildMode.SEQUENTIALLY -> {
//                    val reversedList = animator.childAnimations.reversed()
//                    animator.playSequentially(*reversedList.toTypedArray())
//                    for (animation in reversedList) {
//                        when (animation) {
//                            is ValueAnimator -> {
//                                val reversedArray = animation.values.reversedArray()
//                                animation.setValues(*reversedArray)
//                            }
//                            is AnimatorSet -> {
//                                animation.invert()
//                            }
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//    return animator
//}