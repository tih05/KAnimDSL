package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class RotationXAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ROTATION_X,  *this@RotationXAnimation.values.toFloatArray())
            .apply {
                duration = this@RotationXAnimation.duration
                startDelay = this@RotationXAnimation.startDelay
            }
    }
}

fun AnimationBuilder.rotationX(lambda: RotationXAnimation.()->Unit) = addAnimation(RotationXAnimation(), lambda)

fun View.animateRotationX(lambda: RotationXAnimation.()->Unit)  = RotationXAnimation().launchOnView(this, lambda)

fun View.rotation(lambda: RotationXAnimation.()->Unit): Animator  = RotationXAnimation().createOnView(this, lambda)
