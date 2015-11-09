package com.syslab.page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.joda.time.DateTime;

import com.googlecode.wickedcharts.highcharts.options.Axis;
import com.googlecode.wickedcharts.highcharts.options.ChartOptions;
import com.googlecode.wickedcharts.highcharts.options.HorizontalAlignment;
import com.googlecode.wickedcharts.highcharts.options.Legend;
import com.googlecode.wickedcharts.highcharts.options.LegendLayout;
import com.googlecode.wickedcharts.highcharts.options.Options;
import com.googlecode.wickedcharts.highcharts.options.PlotLine;
import com.googlecode.wickedcharts.highcharts.options.SeriesType;
import com.googlecode.wickedcharts.highcharts.options.Title;
import com.googlecode.wickedcharts.highcharts.options.VerticalAlignment;
import com.googlecode.wickedcharts.highcharts.options.color.HexColor;
import com.googlecode.wickedcharts.highcharts.options.color.HighchartsColor;
import com.googlecode.wickedcharts.highcharts.options.series.Point;
import com.googlecode.wickedcharts.highcharts.options.series.PointSeries;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import com.googlecode.wickedcharts.highcharts.options.series.SimpleSeries;
import com.googlecode.wickedcharts.wicket6.highcharts.Chart;
import com.syslab.entity.Diagnosis;
import com.syslab.entity.Patient;

public class StatisticsPage extends MainBasePage {
	
	private Patient selectedPatient; 
	
	public StatisticsPage() {
		
		final WebMarkupContainer generalContainer = new WebMarkupContainer("generalContainer");
		generalContainer.setOutputMarkupId(true);
		add(generalContainer);
		
		final WebMarkupContainer chartPanel = new WebMarkupContainer("chartPanel");
		chartPanel.setVisible(false);
		chartPanel.setOutputMarkupId(true);
		generalContainer.add(chartPanel);
		
		final Label noDiagnosesFoundMessage = new Label("noDiagnosesFoundMessage", "No diagnoses found.");
		noDiagnosesFoundMessage.setVisible(false);
		noDiagnosesFoundMessage.setOutputMarkupPlaceholderTag(true);
		generalContainer.add(noDiagnosesFoundMessage);
		
		
		final Chart resultsDiagnosisChart = new Chart("resultsChart", new Options());
		chartPanel.add(resultsDiagnosisChart);
		
		final Chart timelineChart = new Chart("timelineChart", new Options());
		chartPanel.add(timelineChart);
		
		
		IModel<List<Patient>> patientModel = new LoadableDetachableModel<List<Patient>>() {

			@Override
			protected List<Patient> load() {
				return loggedUser.getPatients();
			}
			
		};
		
		final DropDownChoice<Patient> patientField = new DropDownChoice<Patient>("patient", new PropertyModel(this, "selectedPatient"), patientModel);

		patientField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Patient selectedPatient = (Patient) patientField.getModelObject();
				
				Options pieOptions = buildResultsChartOptions(selectedPatient);
				resultsDiagnosisChart.setOptions(pieOptions);
				
				Options timelineOptions = buildTimelineChartOptions(selectedPatient);
				timelineChart.setOptions(timelineOptions);
				
				boolean isVisible = isChartVisible(selectedPatient);
				
				chartPanel.setVisible(isVisible);
				noDiagnosesFoundMessage.setVisible(!isVisible);
				
				target.add(generalContainer);
			}
			
		});
		
		generalContainer.add(patientField);
		
	}
	
	private boolean isChartVisible(Patient patient) {
		return patient != null && patient.getDiagnoses() != null && !patient.getDiagnoses().isEmpty();
	}
	
	

	private Options buildResultsChartOptions(Patient patient) {
		
		List<Diagnosis> diagnoses = patient.getDiagnoses();
		Double high = getDiagnosesPercentage(diagnoses, 70.0, 100.0);
		Double medium = getDiagnosesPercentage(diagnoses, 30.0, 70.0);
		Double low = getDiagnosesPercentage(diagnoses, 0.0, 30.0);
		
		
		Options options = new Options();
		options.setTitle(new Title("Mitosis cells - High, medium and low results"));
		options.setChartOptions(new ChartOptions(SeriesType.PIE));
		
		Point highDiagnosesBar = new Point("High Percentage", high).setColor(new HighchartsColor(3));
		Point mediumDiagnosesBar = new Point("Medium Percentage", medium).setColor(new HighchartsColor(1));
		Point lowDiagnosesBar = new Point("Low Percentage", low).setColor(new HighchartsColor(2));
				
		options.addSeries(new PointSeries()
				.setName("Percentage")
				.setColor(new HexColor("#000000"))
				.addPoint(highDiagnosesBar)
				.addPoint(mediumDiagnosesBar)
				.addPoint(lowDiagnosesBar));
		
		
		return options;
	}
	
	private Double getDiagnosesPercentage(List<Diagnosis> diagnoses, Double min, Double max) {
		Integer total = diagnoses.size();
		Integer count = 0;
		
		for (Diagnosis diagnosis : diagnoses) {
			if (min <= diagnosis.getResult() && diagnosis.getResult() < max) count++; 
		}
		
		Double percentage = count.doubleValue() * 100.0 / total.doubleValue();
		return percentage;

	}
	
	
	
	private Options buildTimelineChartOptions(Patient patient) {
		
		List<Diagnosis> diagnoses = patient == null ? this.loggedUser.getDiagnoses() : patient.getDiagnoses();
		
		Options options = new Options();
		options.setChartOptions(new ChartOptions(SeriesType.LINE));
		options.setTitle(new Title("Timeline"));

		Axis xAxis = new Axis();
		xAxis.setCategories(Arrays.asList(new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" }));
		options.addxAxis(xAxis);

		PlotLine plotLines = new PlotLine();
		plotLines.setValue(0f);
		plotLines.setWidth(1);
		plotLines.setColor(new HexColor("#999999"));

		Axis yAxis = new Axis();
		yAxis.setTitle(new Title("Diagnoses"));
		yAxis.setPlotLines(Collections.singletonList(plotLines));
		options.setyAxis(yAxis);

		Legend legend = new Legend();
		legend.setLayout(LegendLayout.VERTICAL);
		legend.setAlign(HorizontalAlignment.RIGHT);
		legend.setVerticalAlign(VerticalAlignment.TOP);
		legend.setX(-10);
		legend.setY(100);
		legend.setBorderWidth(0);
		options.setLegend(legend);

		Series<Number> series1 = new SimpleSeries();
		series1.setName("High");
		series1.setColor(new HighchartsColor(3));
		series1.setData(getMonthlyCount(diagnoses, 70.0, 100.0));
		options.addSeries(series1);

		Series<Number> series2 = new SimpleSeries();
		series2.setName("Medium");
		series2.setColor(new HighchartsColor(1));
		series2.setData(getMonthlyCount(diagnoses, 30.0, 70.0));
		options.addSeries(series2);

		Series<Number> series3 = new SimpleSeries();
		series3.setName("Low");
		series3.setColor(new HighchartsColor(2));
		series3.setData(getMonthlyCount(diagnoses, 0.0, 30.0));
		options.addSeries(series3);
		
		Series<Number> series4 = new SimpleSeries();
		series4.setName("Total");
		series4.setColor(new HighchartsColor(4));
		series4.setData(getMonthlyCount(diagnoses, 0.0, 100.0));
		options.addSeries(series4);
		
		return options;
	}
	
	private List<Number> getMonthlyCount(List<Diagnosis> diagnoses, Double min, Double max) {
		Integer actualYear = new DateTime().getYear();
		//Integer actualMonth = new DateTime().getMonthOfYear();
		
		int size = 12;
		Number[] count = new Integer[size];
		Arrays.fill(count, 0);
		
		for (Diagnosis diagnosis : diagnoses) {
			DateTime diagnosisDate = new DateTime(diagnosis.getCreationDate());
			Integer year = diagnosisDate.getYear();
			Integer month = diagnosisDate.getMonthOfYear();
			
			if (year.equals(actualYear) && min <= diagnosis.getResult() && diagnosis.getResult() < max) {
				count[month-1] = count[month-1].intValue() + 1;
			}
		}
		
		List<Number> monthlyCount = Arrays.asList(count);
		return monthlyCount;
	}
	
}
