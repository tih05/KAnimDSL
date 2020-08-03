package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder
@KAnimation("rotation")
class RotationAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ROTATION,  *this@RotationAnimation.values.toFloatArray())
            .apply {
                duration = this@RotationAnimation.duration
                startDelay = this@RotationAnimation.startDelay
            }
    }
}