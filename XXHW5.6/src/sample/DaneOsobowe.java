package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DaneOsobowe implements HierarchicalController<MainController> {

	public TextField imie;
	public TextField nazwisko;
	public TextField pesel;
	public TextField indeks;
	public TableView<Student> tabelka;
	private MainController parentController;

	public void dodaj(ActionEvent actionEvent) {
		Student st = new Student();
		st.setName(imie.getText());
		st.setSurname(nazwisko.getText());
		st.setPesel(pesel.getText());
		st.setIdx(indeks.getText());
		tabelka.getItems().add(st);
	}

	public void setParentController(MainController parentController) {
		this.parentController = parentController;
		// tabelka.getItems().addAll(parentController.getDataContainer().getStudents());
		tabelka.setItems(parentController.getDataContainer().getStudents());
	}

	public void usunZmiany() {
		tabelka.getItems().clear();
		tabelka.getItems().addAll(parentController.getDataContainer().getStudents());
	}

	public MainController getParentController() {
		return parentController;
	}

	public void initialize() {
		for (TableColumn<Student, ?> studentTableColumn : tabelka.getColumns()) {
			if ("imie".equals(studentTableColumn.getId())) {
				studentTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			} else if ("nazwisko".equals(studentTableColumn.getId())) {
				studentTableColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
			} else if ("pesel".equals(studentTableColumn.getId())) {
				studentTableColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));
			} else if ("indeks".equals(studentTableColumn.getId())) {
				studentTableColumn.setCellValueFactory(new PropertyValueFactory<>("idx"));
			}
		}

	}

	public void zapisz(ActionEvent actionEvent) throws IOException {
		// XSSFWorkbook wb = new XSSFWorkbook();
		// XSSFSheet sheet = wb.createSheet("Studenci");

		FileWriter fw = new FileWriter("KawalaCSV.csv", false);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);
		for (Student student : tabelka.getItems()) {
			pw.println(student.getName() + "," + student.getSurname() + "," + student.getPesel() + ","
					+ student.getIdx() + "," + student.getGrade() + "," + student.getGradeDetailed());
		}

		pdfMaker.createPdf("Kawala.pdf", tabelka.getItems());

		pw.flush();
		pw.close();
	}

	/**
	 * Uwaga na serializację:
	 * https://sekurak.pl/java-vs-deserializacja-niezaufanych-danych-i-zdalne-wykonanie-kodu-czesc-i/
	 */
	public void wczytaj(ActionEvent actionEvent) {
		ArrayList<Student> studentsList = new ArrayList<>();
		String fileName = "KawalaCSV.csv";
		File file = new File(fileName);
		try {
			Scanner inputStream = new Scanner(file);
			while (inputStream.hasNextLine()) {
				String data = inputStream.nextLine();
				String[] values = data.split(",");
				Student student = new Student();
				student.setName(values[0]);
				student.setSurname(values[1]);
				student.setPesel(values[2]);
				student.setIdx(values[3]);
				boolean hasGrade = true;
				try {
					Double.valueOf(values[4]);
				} catch (NumberFormatException e) {
					hasGrade = false;
				}
				if (hasGrade) {
					student.setGrade(Double.parseDouble(values[4]));
				}
				System.out.println(values.length);
				if ((values.length > 5) && (values[5] != "null")) {
					student.setGradeDetailed(values[5]);
				}
				studentsList.add(student);
			}
			tabelka.getItems().clear();
			tabelka.getItems().addAll(studentsList);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
