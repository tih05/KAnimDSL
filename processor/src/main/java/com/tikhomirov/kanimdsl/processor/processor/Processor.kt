package com.tikhomirov.kanimdsl.processor.processor

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.tikhomirov.kanimdsl.processor.annotations.KAnimation
import java.io.File
import java.util.*
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement

@AutoService(Processor::class)
class Processor : AbstractProcessor() {
    companion object {
        const val FILE_NAME = "AnimationExtensions"
        const val PACKAGE_NAME = "com.tikhomirov.kanimdsl.generated"
        const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
    }

    val UNIT_CLASS_NAME = ClassName("kotlin", "Unit")
    val VIEW_CLASS_NAME =
        ClassName("android.view", "View")
    val ANIMATOR_CLASS_NAME =
        ClassName("android.animation", "Animator")
    val ANIMATION_BUILDER_CLASS_NAME = ClassName(
        "com.tikhomirov.kanimdsl",
        "AnimationBuilder"
    )

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(KAnimation::class.java.name)
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment
    ): Boolean {
        val annotatedClasses = roundEnv.getElementsAnnotatedWith(KAnimation::class.java)

        if (annotatedClasses.isEmpty())
            return true

        val fileSpec = FileSpec.builder(
            PACKAGE_NAME,
            FILE_NAME
        )

        annotatedClasses.map {
            generateExtensions(
                element = it,
                fileSpec = fileSpec
            )
        }

        writeToFile(fileSpec)
        return true
    }

    private fun generateExtensions(element: Element, fileSpec: FileSpec.Builder) {
        val annotation = element.getAnnotation(KAnimation::class.java)
        val className = element.simpleName.toString()
        val packageName = processingEnv.elementUtils.getPackageOf(element).qualifiedName.toString()

        val viewExtension = generateViewExtension(
            name = annotation.name,
            className = className,
            packageName = packageName
        )

        val viewAnimateExtension = generateViewAnimateExtension(
            name = annotation.name,
            className = className,
            packageName = packageName
        )

        val animationBuilderExtension = generateAnimationBuilderExtension(
            name = annotation.name,
            className = className,
            packageName = packageName
        )

        fileSpec
            .addFunction(viewExtension)
            .addFunction(viewAnimateExtension)
            .addFunction(animationBuilderExtension)
    }

    private fun generateViewExtension(
        name: String,
        className: String,
        packageName: String
    ): FunSpec {
        return FunSpec.builder(name)
            .receiver(VIEW_CLASS_NAME)
            .addParameter(
                getAnimationLambdaParameter(className, packageName)
            )
            .addCode(
                CodeBlock.builder()
                    .addStatement("return ${className}().createOnView(this, lambda)")
                    .build()
            )
            .returns(
                returnType = ANIMATOR_CLASS_NAME
            )
            .build()

    }

    private fun generateViewAnimateExtension(
        name: String,
        className: String,
        packageName: String
    ): FunSpec {
        val functionName = "animate${name.capitalize()}"
        return FunSpec.builder(functionName)
            .receiver(VIEW_CLASS_NAME)
            .addParameter(
                getAnimationLambdaParameter(className, packageName)
            )
            .addCode(
                CodeBlock.builder()
                    .addStatement("${className}().launchOnView(this, lambda)")
                    .build()
            )
            .build()
    }

    private fun generateAnimationBuilderExtension(
        name: String,
        className: String,
        packageName: String
    ): FunSpec {
        return FunSpec.builder(name)
            .receiver(ANIMATION_BUILDER_CLASS_NAME)
            .addParameter(
                getAnimationLambdaParameter(className, packageName)
            )
            .addCode(
                CodeBlock.builder()
                    .addStatement("addAnimation(${className}(), lambda)")
                    .build()
            )
            .build()
    }

    private fun getAnimationLambdaParameter(className: String, packageName: String): ParameterSpec {
        return ParameterSpec.builder(
            name = "lambda",
            type = LambdaTypeName.get(
                receiver = ClassName(
                    packageName,
                    className
                ),
                returnType = UNIT_CLASS_NAME
            )
        ).build()
    }

    private fun writeToFile(fileSpec: FileSpec.Builder) {
        val generatedDirectory = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME]
        fileSpec
            .build()
            .writeTo(File(generatedDirectory,
                FILE_NAME
            ))
    }

}