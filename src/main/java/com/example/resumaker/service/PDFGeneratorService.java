package com.example.resumaker.service;
import com.example.resumaker.model.Education;
import com.example.resumaker.DTO.ResumeRequest;
import com.example.resumaker.model.WorkExperience;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.ElementList;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

@Slf4j
@Service
public class PDFGeneratorService {
    public static final String HR = "<hr> </hr>";
    Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    Font boldUnderlineFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD | Font.UNDERLINE);

    public byte[] generatePDF(ResumeRequest resumeData) {
        // code to generate PDF
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            addHeaders(document, resumeData);
            addSummary(document, resumeData);
            addEducation(document, resumeData);
            addWorkExperience(document, resumeData);
            addSkills(document, resumeData);
            document.close();


        } catch (Exception e) {
            log.error("Error occurred: {0}", e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    private void addHeaders(Document document, ResumeRequest resumeData) throws DocumentException {
        // Title
        Paragraph name = new Paragraph(resumeData.getContact().getName(), boldFont);
        name.setAlignment(Element.ALIGN_CENTER);
        document.add(name);
        addHtmlContentWithCss(document, HR, null);
        addNewLine(-10, document);

        // Contact Information
        Paragraph contactInfo = new Paragraph(resumeData.getContact().getEmail() + " | "
                + resumeData.getContact().getPhone() + " | "
                + resumeData.getContact().getAddress());
        contactInfo.setAlignment(Element.ALIGN_CENTER);
        document.add(contactInfo);
        document.add(new Paragraph("\n"));
    }

    private void addSummary(Document document, ResumeRequest resumeData) throws DocumentException {
        // code to add summary
        Paragraph summaryHeader= new Paragraph("Summary", boldFont);
        summaryHeader.setAlignment(Element.ALIGN_CENTER);
        document.add(summaryHeader);
        addHtmlContentWithCss(document, HR, null);
        addNewLine(-10, document);
        Paragraph summary = new Paragraph(resumeData.getContact().getSummary());
        document.add(summary);
        document.add(new Paragraph("\n"));
    }

    private void addEducation(Document document, ResumeRequest resumeData) throws DocumentException {
        Paragraph education = new Paragraph("Education", boldFont);
        education.setAlignment(Element.ALIGN_CENTER);
        document.add(education);
        addHtmlContentWithCss(document, HR, null);

        addNewLine(-10, document);

        for (Education edu : resumeData.getEducation()) {
            Paragraph degree = new Paragraph(edu.getDegree(), boldFont);
            Paragraph university = new Paragraph(edu.getUniversity(), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            document.add(university);
            document.add(degree);
        }
        document.add(new Paragraph("\n"));
    }

    private void addWorkExperience(Document document, ResumeRequest resumeData) throws DocumentException {
        Paragraph workExperience = new Paragraph("Work Experience", boldFont);
        //workExperience.setSpacingAfter();//determines the space between lines
        workExperience.setAlignment(Element.ALIGN_CENTER);
        document.add(workExperience);
        addHtmlContentWithCss(document, HR, null);
        addNewLine(-10, document);

        for (WorkExperience work : resumeData.getWorkExperience()) {
            Paragraph company = new Paragraph(work.getCompany(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
            Paragraph title = new Paragraph(work.getTitle(), FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLDITALIC));
            document.add(company);
            document.add(title);
            addUnOrderedList(document, work.getPoints());

            addNewLine(5, document);
        }

        document.add(new Paragraph("\n"));
    }


    private void addSkills(Document document, ResumeRequest resumeData) throws DocumentException {
        Paragraph skills = new Paragraph("Skills", boldFont);
        skills.setAlignment(Element.ALIGN_CENTER);
        document.add(skills);
        addHtmlContentWithCss(document, HR, null);
        addNewLine(5, document);

        Map<String, java.util.List<String>> skillsList = resumeData.getSkills().getSkills();

        //adding every skill category and its skills
        for (Map.Entry<String, java.util.List<String>> entry : skillsList.entrySet()) {
            Chunk skillCategory = new Chunk(entry.getKey() + ": ", boldUnderlineFont);
            document.add(skillCategory);

            String joined = String.join(", ", entry.getValue());
            document.add(new Chunk(joined));
            addNewLine(10, document);
        }

        document.add(new Paragraph("\n"));
    }

    /**
     * Add an unordered list to the document
     * @param document - the document to add the list to
     * @param items - the items to add to the list
     * @throws DocumentException - if an error occurs while adding the list
     */
    private void addUnOrderedList(Document document, String[] items) throws DocumentException {
        // code to add unordered list
       List list = new List(List.UNORDERED);
       list.setListSymbol(new Chunk("• ", boldFont));
        for (String item : items) {
            list.add(new ListItem(item));
        }
        document.add(list);
    }

    /**
     * Add the html content to the document with the css content
     */
    private void addHtmlContentWithCss( Document document, String htmlContent, String cssContent)  {
        try {
            ElementList elements = XMLWorkerHelper.parseToElementList(htmlContent, cssContent);
            for (Element element : elements) {
                document.add(element);
            }

        } catch (Exception e) {
            log.error("Error occurred: {0}", e);
        }
    }

    /**
     * Add the html content to the document
     */
    private void addHtmlContent(PdfWriter pdfWriter, Document document, String htmlContent) throws DocumentException, IOException {
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new StringReader(htmlContent));
    }

    /**
     * Add a new line to the document
     * @param fixedLeading - the leading of the new line
     * @param document   - the document to add the new line to
     * @throws DocumentException - if an error occurs while adding the new line
     */
    private static void addNewLine(int fixedLeading, Document document) throws DocumentException {
        Paragraph newLine = new Paragraph("\n");
        newLine.setLeading(fixedLeading);
        document.add(newLine);
    }

    /**
     * Add a link to the document
     * @param document - the document to add the link to
     * @param displayText - the text to display for the link
     * @param url - the url to link to
     * @throws DocumentException - if an error occurs while adding the link
     */
    private void addLink(Document document, String displayText, String url) throws DocumentException {
        Anchor anchor = new Anchor(displayText);
        anchor.setReference(url);
        document.add(anchor);
    }

}
