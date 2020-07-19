package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class ScaleXAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.SCALE_X,  *this@ScaleXAnimation.values.toFloatArray())
            .apply {
                duration = this@ScaleXAnimation.duration
                startDelay = this@ScaleXAnimation.startDelay

            }
    }
}

fun AnimationBuilder.scaleX(lambda: ScaleXAnimation.()->Unit) = addAnimation(ScaleXAnimation(), lambda)

fun View.animateScaleX(lambda: ScaleXAnimation.()->Unit)  = ScaleXAnimation().launchOnView(this, lambda)

fun View.scaleX(lambda: ScaleXAnimation.()->Unit): Animator  = ScaleXAnimation().createOnView(this, lambda)