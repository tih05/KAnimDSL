package com.tikhomirov.kanimdsl

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

abstract class  Animation<T:Number> {
    //Properties
    lateinit var target: View
    lateinit var values: List<T>
    var duration: Long = 0L
    var startDelay: Long = 0L
    var repeatCount = 0
    var repeatMode: Int = 1

    //Callbacks
    open var onStart:  (() -> Unit)? = null
    open var onEnd:    (() -> Unit)? = null
    open var onCancel: (() -> Unit)? = null
    open var onRepeat: (() -> Unit)? = null

    open fun getAnimator(): Animator {
        val animator = createAnimation()
        animator.apply {
            duration = this@Animation.duration
            startDelay = this@Animation.startDelay
        }
        animator.setCallbacks()
        return animator
    }

    protected open fun Animator.setCallbacks() {
        val hasCallbacks =
                    onStart  != null ||
                    onEnd    != null ||
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

    @Suppress("UNCHECKED_CAST")
    fun <S : Animation<T>> launchOnView(view: View, lambda: S.() -> Unit) {
        target = view
        (this as S).apply(lambda)
        val animator = getAnimator()
        animator.start()
    }

    @Suppress("UNCHECKED_CAST")
    fun <S : Animation<T>> createOnView(view: View, lambda: S.() -> Unit):Animator {
        target = view
        (this as S).apply(lambda)
        return getAnimator()
    }


    protected abstract fun createAnimation(): Animator

}