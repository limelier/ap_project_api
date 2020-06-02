package data

import javafx.scene.image.Image
import java.io.FileInputStream

enum class Color {
    WHITE,
    BLACK
}

enum class Rank {
    PAWN,
    ROOK,
    KNIGHT,
    BISHOP,
    QUEEN,
    KING
}

data class Piece(
    val color: Color,
    val rank: Rank
) {
    /**
     * Virtual property, uses [data.Piece.Companion.getImage] to get a reference to an image already in memory.
     */
    val image: Image
        get() = getImage(this)

    companion object {
        private val pieceImages: Map<Piece, Image>

        /**
         * Get the image for a [piece].
         *
         * Should only be called once per piece type.
         */
        private fun loadImage(piece: Piece): Image {
            val color = piece.color.name.toLowerCase()
            val rank = piece.rank.name.toLowerCase()

            return Image(FileInputStream("src/main/resources/images/${color}_${rank}.png"))
        }

        /**
         * Get the image for a [piece] from the map.
         */
        @Suppress("MapGetWithNotNullAssertionOperator")
        // the map is populated on init, NPE cannot occur
        private fun getImage(piece: Piece): Image {
            return pieceImages[piece]!!
        }

        /**
         * Call [data.Piece.Companion.loadImage] once for every possible piece, populating the map.
         */
        init {
            val ranks = Rank.values()
            val colors = Color.values()
            val pieces = ranks.flatMap { rank -> colors.map { color -> Piece(color, rank) } }
            pieceImages = pieces.associateWith {
                loadImage(
                    it
                )
            }
        }
    }
}

