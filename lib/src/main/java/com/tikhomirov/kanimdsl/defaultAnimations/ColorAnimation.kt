package com.tikhomirov.kanimdsl.defaultAnimations

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.tikhomirov.kanimdsl.Animation
import com.tikhomirov.kanimdsl.AnimationBuilder
import com.tikhomirov.kanimdsl.processor.annotations.KAnimation

@KAnimation("color")
class ColorAnimation: Animation<Int>() {
    lateinit var property: String
    override fun createAnimation(): Animator {

        return ObjectAnimator.ofArgb(target, property, *this@ColorAnimation.values.toIntArray())
            .apply {
                duration = this@ColorAnimation.duration
                startDelay = this@ColorAnimation.startDelay

            }
    }
}
