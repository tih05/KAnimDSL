package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kanimdsl.processor.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder

@KAnimation("translationY")
class TranslationYAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.TRANSLATION_Y,  *this@TranslationYAnimation.values.toFloatArray())
            .apply {
                duration = this@TranslationYAnimation.duration
                startDelay = this@TranslationYAnimation.startDelay
            }
    }
}