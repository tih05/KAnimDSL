package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder

@KAnimation("rotationY")
class RotationYAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ROTATION_Y,  *this@RotationYAnimation.values.toFloatArray())
            .apply {
                duration = this@RotationYAnimation.duration
                startDelay = this@RotationYAnimation.startDelay
            }
    }
}