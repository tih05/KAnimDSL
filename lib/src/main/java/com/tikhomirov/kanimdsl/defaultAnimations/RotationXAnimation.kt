package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.annotations.KAnimation
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder
@KAnimation("rotationX")
class RotationXAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ROTATION_X,  *this@RotationXAnimation.values.toFloatArray())
            .apply {
                duration = this@RotationXAnimation.duration
                startDelay = this@RotationXAnimation.startDelay
            }
    }
}
