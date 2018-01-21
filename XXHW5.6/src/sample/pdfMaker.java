package sample;

import java.io.FileNotFoundException;

import com.itextpdf.io.IOException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import javafx.collections.ObservableList;

public class pdfMaker {

	public static void createPdf(String dest, ObservableList<Student> observableList)
			throws IOException, FileNotFoundException {
		// Initialize PDF writer
		PdfWriter writer = new PdfWriter(dest);

		// Initialize PDF document
		PdfDocument pdf = new PdfDocument(writer);

		// Initialize document
		Document document = new Document(pdf);

		Table table = new Table(6);
		table.addHeaderCell("Imię");
		table.addHeaderCell("Nazwisko");
		table.addHeaderCell("PESEL");
		table.addHeaderCell("Numer indeksu");
		table.addHeaderCell("Ocena");
		table.addHeaderCell("Uzasadnienie");
		for (Student student : observableList) {
			if (student.getName() != null)
				table.addCell(student.getName());
			else
				table.addCell("Brak");
			if (student.getSurname() != null)
				table.addCell(student.getSurname());
			else
				table.addCell("Brak");
			if (student.getPesel() != null)
				table.addCell(student.getPesel());
			else
				table.addCell("Brak");
			if (student.getIdx() != null)
				table.addCell(student.getIdx());
			else
				table.addCell("Brak");
			if (student.getGrade() != null)
				table.addCell(Double.toString(student.getGrade()));
			else
				table.addCell("brak");
			if (student.getGradeDetailed() != null)
				table.addCell(student.getGradeDetailed());
			else
				table.addCell("Brak opisu.");
		}
		document.add(table);

		// Close document
		document.close();
	}
}