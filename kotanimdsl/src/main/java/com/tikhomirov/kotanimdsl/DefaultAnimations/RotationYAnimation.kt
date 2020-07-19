package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class RotationYAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.ROTATION_Y,  *this@RotationYAnimation.values.toFloatArray())
            .apply {
                duration = this@RotationYAnimation.duration
                startDelay = this@RotationYAnimation.startDelay
            }
    }
}

fun AnimationBuilder.rotationY(lambda: RotationYAnimation.()->Unit) = addAnimation(RotationYAnimation(), lambda)

fun View.animateRotationY(lambda: RotationYAnimation.()->Unit)  = RotationYAnimation().launchOnView(this, lambda)

fun View.rotationY(lambda: RotationYAnimation.()->Unit): Animator  = RotationYAnimation().createOnView(this, lambda)
