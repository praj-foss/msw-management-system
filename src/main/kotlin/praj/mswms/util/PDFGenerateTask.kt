/**
 * This file is licensed under the MIT license.
 * See the LICENSE file in project root for details.
 */

package praj.mswms.util

import javafx.concurrent.Task
import javafx.embed.swing.SwingFXUtils
import javafx.scene.image.WritableImage
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.PDPageContentStream
import org.apache.pdfbox.pdmodel.common.PDRectangle
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject
import org.vandeseer.easytable.TableDrawer
import org.vandeseer.easytable.structure.Row
import org.vandeseer.easytable.structure.Table
import org.vandeseer.easytable.structure.cell.CellText
import praj.mswms.service.RepositoryService
import java.awt.Color
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO

/**
 * JavaFX Task to print tables to PDF.
 */
class PDFGenerateTask(
        private val path: File, private val charts: WritableImage
) : Task<Boolean>() {
    private val padding = 50F
    private val headerColor = Color.LIGHT_GRAY
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")

    override fun call(): Boolean {
        PDDocument().apply {
            addImage(charts)
            addTable(locationTable())
            addTable(vehicleTable())
            addTable(collectionTable())
            save(path)
            close()
        }

        return true
    }

    private fun locationTable(): Table {
        val tableBuilder = Table.builder()
                .addColumnsOfWidth(50F, 150F, 150F)
                .addRow(Row.builder()
                        .backgroundColor(headerColor)
                        .add(CellText.builder().text("ID").build())
                        .add(CellText.builder().text("Location").build())
                        .add(CellText.builder().text("Location Type").build())
                        .build())

        RepositoryService.locationRepository.elementList.sorted { o1, o2 -> o1.id - o2.id }.forEach {
            tableBuilder.addRow(Row.builder()
                    .add(CellText.builder().text(it.id.toString()).build())
                    .add(CellText.builder().text(it.name).build())
                    .add(CellText.builder().text(it.type).build())
                    .build())
        }

        return tableBuilder.build()
    }

    private fun vehicleTable(): Table {
        val tableBuilder = Table.builder()
                .addColumnsOfWidth(50F, 150F, 80F, 150F, 100F)
                .addRow(Row.builder()
                        .backgroundColor(headerColor)
                        .add(CellText.builder().text("ID").build())
                        .add(CellText.builder().text("Model Name").build())
                        .add(CellText.builder().text("Capacity").build())
                        .add(CellText.builder().text("Last Location").build())
                        .add(CellText.builder().text("Status").build())
                        .build())

        RepositoryService.vehicleRepository.elementList.sorted { o1, o2 -> o1.id - o2.id }.forEach {
            tableBuilder.addRow(Row.builder()
                    .add(CellText.builder().text(it.id.toString()).build())
                    .add(CellText.builder().text(it.model).build())
                    .add(CellText.builder().text(it.capacity.toString()).build())
                    .add(CellText.builder().text(it.lastLocation.name).build())
                    .add(CellText.builder().text(it.status).build())
                    .build())
        }

        return tableBuilder.build()
    }

    private fun collectionTable(): Table {
        val tableBuilder = Table.builder()
                .addColumnsOfWidth(30F, 130F, 150F, 120F, 50F)
                .addRow(Row.builder()
                        .backgroundColor(headerColor)
                        .add(CellText.builder().text("ID").build())
                        .add(CellText.builder().text("Collection Time").build())
                        .add(CellText.builder().text("Location").build())
                        .add(CellText.builder().text("Vehicle ID").build())
                        .add(CellText.builder().text("Tons").build())
                        .build())

        RepositoryService.collectionRepository.elementList.sorted { o1, o2 -> o1.id - o2.id }.forEach {
            tableBuilder.addRow(Row.builder()
                    .add(CellText.builder().text(it.id.toString()).build())
                    .add(CellText.builder().text(it.time.format(dateFormatter)).build())
                    .add(CellText.builder().text(it.location.name).build())
                    .add(CellText.builder().text(it.vehicle.toString()).build())
                    .add(CellText.builder().text(it.amount.toString()).build())
                    .build())
        }

        return tableBuilder.build()
    }

    private fun PDDocument.addTable(table: Table) {
        val drawer = TableDrawer.builder()
                .table(table)
                .startX(padding)
                .endY(padding)
                .build()

        do {
            val page = PDPage(PDRectangle.A4)
            this.addPage(page)
            drawer.startY(page.mediaBox.height - padding)
            PDPageContentStream(this, page).use { drawer.contentStream(it).draw() }
        } while (! drawer.isFinished)
    }

    private fun PDDocument.addImage(image: WritableImage) {
        val imgByteStream = ByteArrayOutputStream()
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", imgByteStream)
        val imgObject = PDImageXObject.createFromByteArray(this, imgByteStream.toByteArray(), "Charts")

        val page = PDPage(PDRectangle.A4)
        this.addPage(page)

        val width  = page.mediaBox.width - 2 * padding
        val height = width * (image.height / image.width).toFloat()
        val startX = (page.mediaBox.width - width) / 2
        val startY = page.mediaBox.height - height - padding

        PDPageContentStream(this, page).use { it.drawImage(imgObject, startX, startY, width, height) }
    }
}