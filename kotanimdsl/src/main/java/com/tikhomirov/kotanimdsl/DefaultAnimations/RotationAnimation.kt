package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class RotationAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ROTATION,  *this@RotationAnimation.values.toFloatArray())
            .apply {
                duration = this@RotationAnimation.duration
                startDelay = this@RotationAnimation.startDelay
            }
    }
}

fun AnimationBuilder.rotation(lambda: RotationAnimation.()->Unit) = addAnimation(RotationAnimation(), lambda)

fun View.animateRotation(lambda: RotationAnimation.()->Unit)  = RotationAnimation().launchOnView(this, lambda)

fun View.rotation(lambda: RotationAnimation.()->Unit): Animator  = RotationAnimation().createOnView(this, lambda)