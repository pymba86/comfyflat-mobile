package com.gitlab.pymba86.comfyflat.mobile.toothpick

import javax.inject.Qualifier

/**
 * Created by Konstantin Tskhovrebov (aka @terrakok) on 03.02.18.
 */

@Qualifier
annotation class DefaultPageSize

@Qualifier
annotation class DefaultServerPath

@Qualifier
annotation class ServerPath

@Qualifier
annotation class AppDevelopersPath

@Qualifier
annotation class IssueId

@Qualifier
annotation class MergeRequestId

@Qualifier
annotation class RoomId

@Qualifier
annotation class RoomName

@Qualifier
annotation class ProjectListMode

@Qualifier
annotation class TodoListPendingState

@Qualifier
annotation class UserId

@Qualifier
annotation class CacheLifetime

@Qualifier
annotation class WithErrorHandler