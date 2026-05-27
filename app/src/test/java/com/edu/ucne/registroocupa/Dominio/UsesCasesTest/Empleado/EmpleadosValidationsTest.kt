package com.edu.ucne.registroocupa.Dominio.UsesCasesTest.Empleado

import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateNombres
import com.edu.ucne.registroocupa.Dominio.useCase.Empleado.validateSexo
import com.edu.ucne.registroocupa.Dominio.useCase.Ocupacion.validateSueldo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EmpleadosValidationsTest {


    @Test
    fun `validateNombres fails on short`() {
        val res = validateNombres("ab")
        assertFalse(res.isValid)
    }


    @Test
    fun `validateNombres passes on ok`() {
        val res = validateNombres("Luis")
        assertTrue(res.isValid)
        assertNull(res.error)
    }

    @Test
    fun `validateSexo fails on blank`() {
        val res = validateSexo("")
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
