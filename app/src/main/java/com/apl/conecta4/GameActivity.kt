package com.apl.conecta4

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.system.exitProcess

class GameActivity : AppCompatActivity() {
    // Variables para mantener el estado del juego
    private var gameBoard = Array(6) { IntArray(7) { 0 } }
    private var currentPlayer = 1
    private var turn = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Configurar el gridLayout
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        gridLayout.columnCount = 7
        gridLayout.rowCount = 6

        // Obtener referencia al botón Reset
        val resetButton = findViewById<Button>(R.id.resetButton)
        // Manejador de evento para el botón Reset
        resetButton.setOnClickListener {
            // Reiniciar el estado del juego aquí
            resetGame()
        }

        // Obtener referencia al botón Quit
        val quitButton = findViewById<Button>(R.id.quitButton)
        // Manejador de evento para el botón Quit
        quitButton.setOnClickListener {
            // Salir del juego
            exitProcess(0)
        }



        // Configurar los botones del tablero de juego
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.setOnClickListener {
                val column = i % 7
                val row = getEmptyRow(column)

                if (row != -1) {
                    // Actualizar el estado del juego
                    gameBoard[row][column] = currentPlayer

                    // Actualizar el botón en la interfaz de usuario
                    val resource =
                        if (currentPlayer == 1) R.drawable.ficharoja else R.drawable.fichaamarilla
                    button.background = ContextCompat.getDrawable(this, resource)

                    // Verificar si el juego ha terminado
                    if (checkWinner(row, column)) {
                        Toast.makeText(this, "Player $currentPlayer wins!", Toast.LENGTH_SHORT)
                            .show()
                        resetGame()
                    } else if (checkTie()) {
                        Toast.makeText(this, "Tie game!", Toast.LENGTH_SHORT).show()
                        resetGame()
                    } else {
                        // Cambiar al siguiente jugador
                        currentPlayer = if (currentPlayer == 1) 2 else 1
                    }
                }
            }
        }
    }

    /**
     * Reinicia el estado del juego.
     */
    private fun resetGame() {
        // Reiniciar el estado del juego
        gameBoard = Array(6) { IntArray(7) { 0 } }
        currentPlayer = 1

        // Redibujar el tablero
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        for (i in 0 until gridLayout.childCount) {
            val button = gridLayout.getChildAt(i) as Button
            button.background = ContextCompat.getDrawable(this, R.drawable.botongris)
        }
    }





    /**
     * Obtiene la fila vacía en la columna dada.
     */
    private fun getEmptyRow(column: Int): Int {
        // Buscar la primera fila vacía en la columna seleccionada
        for (i in 5 downTo 0) {
            if (gameBoard[i][column] == 0) {
                return i
            }
        }

        // Si no hay fila vacía en la columna seleccionada, buscar la siguiente columna disponible
        for (j in column + 1 until 7) {
            for (i in 5 downTo 0) {
                if (gameBoard[i][j] == 0) {
                    return i
                }
            }
        }

        // Si no hay ninguna columna disponible, retornar -1 para indicar que no se encontró una fila vacía
        return -1
    }


    /**
     * Verifica si hay un ganador en el juego.
     */
    private fun checkWinner(row: Int, column: Int): Boolean {
        // Verificar si hay 4 en línea en la fila
        var count = 1
        var c = column - 1
        while (c >= 0 && gameBoard[row][c] == currentPlayer) {
            count++
            c--
        }
        c = column + 1
        while (c <= 6 && gameBoard[row][c] == currentPlayer) {
            count++
            c++
        }
        if (count >= 4) {
            return true
        }

        // Verificar si hay 4 en línea en la columna
        count = 1
        var r = row - 1
        while (r >= 0 && gameBoard[r][column] == currentPlayer) {
            count++
            r--
        }
        r = row + 1
        while (r <= 5 && gameBoard[r][column] == currentPlayer) {
            count++
            r++
        }
        if (count >= 4) {
            return true
        }



        // Verificar si hay 4 en línea en la diagonal (de izquierda a derecha)
        count = 1
        r = row - 1
        c = column - 1
        while (r >= 0 && c >= 0 && gameBoard[r][c] == currentPlayer) {
            count++
            r--
            c--
        }
        r = row + 1
        c = column + 1
        while (r <= 5 && c <= 6 && gameBoard[r][c] == currentPlayer) {
            count++
            r++
            c++
        }
        if (count >= 4) {
            return true
        }

        // Verificar si hay 4 en línea en la diagonal (de derecha a izquierda)
        count = 1
        r = row - 1
        c = column + 1
        while (r >= 0 && c <= 6 && gameBoard[r][c] == currentPlayer) {
            count++
            r--
            c++
        }
        r = row + 1
        c = column - 1
        while (r <= 5 && c >= 0 && gameBoard[r][c] == currentPlayer) {
            count++
            r++
            c--
        }
        if (count >= 4) {
            return true
        }

        // Si no hay un ganador, devolver false
        return false
    }

    /**
     * Verifica si el juego ha terminado en empate.
     */
    private fun checkTie(): Boolean {
        for (i in 0..6) {
            if (gameBoard[0][i] == 0) {
                return false
            }
        }
        return true
    }
}






