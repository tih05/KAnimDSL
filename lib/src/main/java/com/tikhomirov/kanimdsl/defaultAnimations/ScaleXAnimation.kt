package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kanimdsl.processor.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder

@KAnimation("scaleX")
class ScaleXAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.SCALE_X,  *this@ScaleXAnimation.values.toFloatArray())
            .apply {
                duration = this@ScaleXAnimation.duration
                startDelay = this@ScaleXAnimation.startDelay

            }
    }
}