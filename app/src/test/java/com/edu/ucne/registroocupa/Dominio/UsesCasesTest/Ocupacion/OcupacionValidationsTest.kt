package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Ocupacion

import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.validateDescription
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.validateSueldo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class OcupacionValidationsTest {

    @Test
    fun `validateDescription fails on blank`() {
        val res = validateDescription("", emptyList())
        assertFalse(res.isValid)
        assertNotNull(res.error)
    }

    @Test
    fun `validateDescription fails on short`() {
        val res = validateDescription("ab", emptyList())
        assertFalse(res.isValid)
    }

    @Test
    fun `validateDescription fails when description exists`() {
        val res = validateDescription("Chofer", listOf("Chofer", "Ingeniero"))
        assertFalse(res.isValid)
        assertEquals("Ya Hiciste una Ocupacion con esa Descripcion", res.error)
    }

    @Test
    fun `validateDescription passes on ok`() {
        val res = validateDescription("Doctor", emptyList())
        assertTrue(res.isValid)
        assertNull(res.error)
    }

    @Test
    fun `validateSueldo fails on blank`() {
        val res = validateSueldo("")
        assertFalse(res.isValid)
    }

    @Test
    fun `validateSueldo fails on non numeric`() {
        val res = validateSueldo("abc")
        assertFalse(res.isValid)
    }

    @Test
    fun `validateSueldo fails on zero or negative`() {
        assertFalse(validateSueldo("0").isValid)
        assertFalse(validateSueldo("-1").isValid)
    }

    @Test
    fun `validateSueldo passes on positive integer`() {
        val res = validateSueldo("50000")
        assertTrue(res.isValid)
    }
}
