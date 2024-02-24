package com.add.file.services;

import java.io.InputStream;

import org.apache.tika.Tika;

import org.apache.tika.metadata.Metadata;

import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;

import com.add.file.dto.ContentDTO;
import com.add.file.model.Content;

import org.apache.commons.io.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

@Slf4j
@Service
@RequiredArgsConstructor
public class TextExtractionService {

    private final ITesseract tesseract;

    public String imageToText(InputStream stream) {

        try {

            BufferedImage bi = ImageIO.read(stream);
            String result = normalizeWord(tesseract.doOCR(bi));
            closeInputStram(stream);
            return result;
        } catch (Exception e) {
            log.info(e.getMessage());
            closeInputStram(stream);
            return "";
        }

    }

    public String normalizeWord(String wordContent) {
        return wordContent.replaceAll("[^a-zA-Z\\d]", "")
                .replace("null", "")
                .replace("(\\.)*[\n]+", ".\n")
                .replace("\n", " ")
                .replace("\r", " ")
                .replaceAll("[\\s]+", " ");
    }

    public String detectDocTypeUsingFacade(InputStream stream) {
        try {
            Tika tika = new Tika();
            String result = tika.detect(stream);
            closeInputStram(stream);
            return result;
        } catch (Exception e) {
            log.info(e.getMessage());
            closeInputStram(stream);
            return "";
        }

    }

    public String extractContentUsingParser(InputStream stream) {

        try {
            Parser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            parser.parse(stream, handler, metadata, context);
            closeInputStram(stream);
            return handler.toString();
        } catch (Exception e) {
            log.info(e.getMessage());
            closeInputStram(stream);
            return "";
        }
    }

    public String extractContentUsingFacade(InputStream stream) {

        try {
            Tika tika = new Tika();
            String content = tika.parseToString(stream);
            closeInputStram(stream);
            return content;

        } catch (Exception e) {
            log.info(e.getMessage());
            closeInputStram(stream);
            return "";
        }
    }

    public Metadata extractMetadatatUsingParser(InputStream stream) {

        try {
            Parser parser = new AutoDetectParser();
            ContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();

            parser.parse(stream, handler, metadata, context);
            closeInputStram(stream);
            return metadata;

        } catch (Exception e) {
            log.info(e.getMessage());
            closeInputStram(stream);
            return null;
        }
    }

    public Metadata extractMetadatatUsingFacade(InputStream stream) {

        try {
            Tika tika = new Tika();
            Metadata metadata = new Metadata();
            tika.parse(stream, metadata);
            closeInputStram(stream);
            return metadata;

        } catch (Exception e) {
            log.info(e.getMessage());
            closeInputStram(stream);
            return null;
        }
    }

    public String extract(Content file, ContentDTO contentDTO) {

        try {
            if (file.getFileType().isImage()) {
                return imageToText(contentDTO.getFile().getInputStream());
            } else if (file.getFileType().isDocument() || file.getFileType().isFile()) {
                return extractContentUsingParser(contentDTO.getFile().getInputStream());
            } else {
                return "";
            }

        } catch (Exception e) {

            log.error(e.getMessage());
            return "";

        }
    }

    public void closeInputStram(InputStream stream) {
        IOUtils.closeQuietly(stream);
    }

    public String extract2(ContentDTO contentDTO) {

        try {

            return imageToText(contentDTO.getFile().getInputStream());

        } catch (Exception e) {

            log.error(e.getMessage());
            return "";

        }
    }

}
