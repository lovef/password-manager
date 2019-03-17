package se.lovef.password.gradle

import groovy.lang.Closure
import org.gradle.api.Plugin
import org.gradle.api.Project
import se.lovef.password.formatter.ReadableFormatter
import se.lovef.password.hasher.PasswordHasher

class GradlePasswordManagerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create(
            "passwordManager", PasswordManagerExtension::class.java,
            this, project
        )
    }
}

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class PasswordManagerExtension(
    owner: GradlePasswordManagerPlugin,
    project: Project
) : Closure<String>(owner, owner) {

    private val passwordManager = PasswordManagerImpl(
        SaltFileFactoryImpl(project.projectDir),
        PasswordHasher(),
        ReadableFormatter(),
        PasswordReaderImpl()
    )

    operator fun get(passwordIdentifier: String) = passwordManager.get(passwordIdentifier)

    fun saltDirectories(vararg paths: String) {
        paths.forEach { saltDirectory(it) }
    }

    fun saltDirectory(path: String) {
        passwordManager.saltDirectory(path)
    }
}
