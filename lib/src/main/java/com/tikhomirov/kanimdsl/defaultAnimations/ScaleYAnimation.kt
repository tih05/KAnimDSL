package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder

@KAnimation("scaleY")
class ScaleYAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.SCALE_Y,  *this@ScaleYAnimation.values.toFloatArray())
            .apply {
                duration = this@ScaleYAnimation.duration
                startDelay = this@ScaleYAnimation.startDelay
            }
    }
}
