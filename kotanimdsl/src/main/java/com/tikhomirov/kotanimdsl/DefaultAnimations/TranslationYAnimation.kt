package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class TranslationYAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.TRANSLATION_Y,  *this@TranslationYAnimation.values.toFloatArray())
            .apply {
                duration = this@TranslationYAnimation.duration
                startDelay = this@TranslationYAnimation.startDelay
            }
    }
}

fun AnimationBuilder.translationY(lambda: TranslationYAnimation.()->Unit) = addAnimation(TranslationYAnimation(), lambda)

fun View.animateTranslationY(lambda: TranslationYAnimation.()->Unit)  = TranslationYAnimation().launchOnView(this, lambda)

fun View.translationY(lambda: TranslationYAnimation.()->Unit): Animator  = TranslationYAnimation().createOnView(this, lambda)