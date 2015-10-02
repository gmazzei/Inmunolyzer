package com.syslab.page;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

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
		
	public StatisticsPage() {
		
		final WebMarkupContainer chartPanel = new WebMarkupContainer("chartPanel");
		chartPanel.setOutputMarkupId(true);
		this.add(chartPanel);
		
		Options pieOptions = buildResultsChartOptions(null);
		final Chart resultsDiagnosisChart = new Chart("resultsChart", pieOptions);
		chartPanel.add(resultsDiagnosisChart);
		
		
		
		Options timelineOptions = buildTimelineChartOptions();
		final Chart timelineChart = new Chart("timelineChart", timelineOptions);
		chartPanel.add(timelineChart);
		
		IModel<List<Patient>> patientModel = new LoadableDetachableModel<List<Patient>>() {

			@Override
			protected List<Patient> load() {
				return loggedUser.getPatients();
			}
			
		};
		
		final DropDownChoice<Patient> patientField = new DropDownChoice<Patient>("patient", patientModel);
		patientField.setModel(Model.of(new Patient()));
		patientField.add(new AjaxFormComponentUpdatingBehavior("onchange") {
			
			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				Patient selectedPatient = (Patient) patientField.getModelObject();
				Options options = buildResultsChartOptions(selectedPatient);
				resultsDiagnosisChart.setOptions(options);
				target.add(chartPanel);
			}
			
		});
		
		this.add(patientField);
		
	}
	
	

	private Options buildResultsChartOptions(Patient patient) {
		
		List<Diagnosis> diagnoses = patient == null ? this.loggedUser.getDiagnoses() : patient.getDiagnoses();
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
	
	
	
	private Options buildTimelineChartOptions() {
		
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
		series1.setName("Positive");
		series1.setData(Arrays.asList(new Number[] { 7, 6, 9, 14, 18, 21, 25, 26, 23, 18, 13, 9 }));
		options.addSeries(series1);

		Series<Number> series2 = new SimpleSeries();
		series2.setName("Negative");
		series2.setData(Arrays.asList(new Number[] { 2, 5, 14, 6, 4, 2, 4, 17, 2, 4, 6, 2 }));
		options.addSeries(series2);

		Series<Number> series3 = new SimpleSeries();
		series3.setName("Total");
		series3.setData(Arrays.asList(new Number[] { 9, 11, 23, 20, 22, 33, 40, 37, 44, 40, 26, 25 }));
		options.addSeries(series3);
		
		return options;
	}
	
}
