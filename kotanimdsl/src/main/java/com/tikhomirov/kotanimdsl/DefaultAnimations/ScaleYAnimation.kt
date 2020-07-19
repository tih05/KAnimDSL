package com.tikhomirov.kotanimdsl.DefaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kotanimdsl.Animation
import com.tikhomirov.kotanimdsl.AnimationBuilder

class ScaleYAnimation: Animation<Float>() {
    override fun createAnimation(): Animator {
        return ObjectAnimator.ofFloat(target, View.SCALE_Y,  *this@ScaleYAnimation.values.toFloatArray())
            .apply {
                duration = this@ScaleYAnimation.duration
                startDelay = this@ScaleYAnimation.startDelay
            }
    }
}

fun AnimationBuilder.scaleY(lambda: ScaleYAnimation.()->Unit) = addAnimation(ScaleYAnimation(), lambda)

fun View.animateScaleY(lambda: ScaleYAnimation.()->Unit)  = ScaleYAnimation().launchOnView(this, lambda)

fun View.scaleY(lambda: ScaleYAnimation.()->Unit): Animator  = ScaleYAnimation().createOnView(this, lambda)
