package com.jmg.checkagro.check.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.jmg.checkagro.check.exception.CheckException;
import com.jmg.checkagro.check.exception.MessageCode;
import com.jmg.checkagro.check.repository.CheckRepository;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class CheckPrintService {

    public static final String CHECK_FILE_PDF = "checkFile.pdf";
    private final CheckRepository checkRepository;

    public CheckPrintService(CheckRepository checkRepository) {
        this.checkRepository = checkRepository;
    }

    public byte[] printCheck(Long checkId) throws CheckException {
        // Crear el documento
        Document document = new Document();
        var check = checkRepository.findById(checkId).orElseThrow(() -> new CheckException(MessageCode.CHECK_NOT_FOUND));
        try {
            // Crear el archivo PDF
            var file = new FileOutputStream(CHECK_FILE_PDF);
            PdfWriter.getInstance(document, file);
            Rectangle page = PageSize.A6.rotate();
            float marginLeft = 10;
            float marginRight = 10;
            float marginTop = 10;
            float marginBottom = 10;
            document.setPageSize(page);
            document.setMargins(marginLeft, marginRight, marginTop, marginBottom);
            // Abrir el documento para edición
            document.open();

            // Agregar el título del cheque
            Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 20, Font.UNDERLINE);
            Paragraph title = new Paragraph("CHECK", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Agregar la información del emisor y destinatario
            Font titleFontSubtitulo = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.NORMAL);
            Font infoFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
            Paragraph info = new Paragraph();
            info.add(new Chunk("Customer: ", titleFontSubtitulo));
            info.add(Chunk.NEWLINE);
            info.add(new Chunk("Document Type: " + check.getDocumentTypeCustomer(), infoFont));
            info.add(Chunk.NEWLINE);
            info.add(new Chunk("Document Number: " + check.getDocumentValueCustomer(), infoFont));
            info.add(Chunk.NEWLINE);
            info.add(new Chunk("Provider: ", titleFontSubtitulo));
            info.add(Chunk.NEWLINE);
            info.add(new Chunk("Document Type: " + check.getDocumentTypeProvider(), infoFont));
            info.add(Chunk.NEWLINE);
            info.add(new Chunk("Document Number: " + check.getDocumentValueProvider(), infoFont));
            info.add(Chunk.NEWLINE);
            info.add(Chunk.NEWLINE);
            info.setAlignment(Element.ALIGN_LEFT);
            document.add(info);

            // Agregar el importe y la fecha de emisión
            Font amountFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD);
            Paragraph amount = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add(Chunk.NEWLINE);
            amount.add(new Chunk("Amount: $" + check.getAmountTotal(), amountFont));
            amount.add(Chunk.NEWLINE);
            info.add(Chunk.NEWLINE);
            info.add(Chunk.NEWLINE);
            amount.add(new Chunk("Emission Date: " + check.getEmitDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), infoFont));
            amount.setAlignment(Element.ALIGN_CENTER);
            document.add(amount);

            // Agregar la cantidad de meses de vigencia
            Font validityFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14);
            Paragraph validity = new Paragraph();
            validity.add(new Chunk("Valid from " + check.getMonthsDuration() + " months", validityFont));
            validity.setAlignment(Element.ALIGN_CENTER);
            document.add(validity);

            // Cerrar el documento
            document.close();

            byte[] pdfBytes = null;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            FileInputStream fis = new FileInputStream(CHECK_FILE_PDF);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            pdfBytes = outputStream.toByteArray();
            fis.close();

            return pdfBytes;
        } catch (Exception e) {
            throw new CheckException(MessageCode.CHECK_NOT_FOUND);
        }
    }
}
