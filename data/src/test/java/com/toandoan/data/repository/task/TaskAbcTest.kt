package com.toandoan.data.repository.task

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TaskAbcTest {

    @Mock
    private lateinit var title: String

    private lateinit var task: TaskAbc

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        task = TaskAbc(title)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testShouldBeSuccess() {
        task.abc()
        Mockito.verify(title.plus("DEFAULT"))
    }
}