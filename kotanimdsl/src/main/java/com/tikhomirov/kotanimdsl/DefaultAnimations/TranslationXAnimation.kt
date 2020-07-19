package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class TranslationXAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.TRANSLATION_X,  *this@TranslationXAnimation.values.toFloatArray())
            .apply {
                duration = this@TranslationXAnimation.duration
                startDelay = this@TranslationXAnimation.startDelay
            }
    }
}

fun AnimationBuilder.translationX(lambda: TranslationXAnimation.()->Unit) = addAnimation(TranslationXAnimation(), lambda)

fun View.animateTranslationX(lambda: TranslationXAnimation.()->Unit)  = TranslationXAnimation().launchOnView(this, lambda)

fun View.translationX(lambda: TranslationXAnimation.()->Unit): Animator  = TranslationXAnimation().createOnView(this, lambda)
