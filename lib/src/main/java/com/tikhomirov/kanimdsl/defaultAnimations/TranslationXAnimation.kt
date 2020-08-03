package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kanimdsl.processor.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder

@KAnimation("translationX")
class TranslationXAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.TRANSLATION_X,  *this@TranslationXAnimation.values.toFloatArray())
            .apply {
                duration = this@TranslationXAnimation.duration
                startDelay = this@TranslationXAnimation.startDelay
            }
    }
}