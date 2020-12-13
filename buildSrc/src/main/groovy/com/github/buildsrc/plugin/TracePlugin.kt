package com.github.buildsrc.plugin

import com.android.build.gradle.AppExtension
import com.github.buildsrc.transform.TraceTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by Lyongwang on 2020/12/13 18: 08.
 *
 * Email: liyongwang@yiche.com
 */
class TracePlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.getByType(AppExtension::class.java)
                .registerTransform(TraceTransform())
    }
}