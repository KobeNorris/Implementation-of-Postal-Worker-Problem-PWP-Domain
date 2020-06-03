package com.aim.project.pwp.visualiser;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.aim.project.pwp.AIM_PWP;
import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPSolutionInterface;
import com.aim.project.pwp.solution.PWPSolution;

public class PWPView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7066509516892991728L;

	private PWPPanel oPanel;

	private Color oCitiesColor;

	private Color oRoutesColor;

	public PWPView(PWPInstanceInterface oInstance, AIM_PWP oProblem, Color oCitiesColor, Color oRoutesColor) {

		this.oCitiesColor = oCitiesColor;
		this.oRoutesColor = oRoutesColor;
		this.oPanel = new PWPPanel(oInstance, oProblem);
		JFrame frame = new JFrame();
		frame.setTitle("PWP Solution Visualiser");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add(this.oPanel);
		frame.setVisible(true);
	}

	class PWPPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1295525707913147839L;

		private PWPInstanceInterface oInstance;

		private AIM_PWP oProblem;

		public PWPPanel(PWPInstanceInterface oInstance, AIM_PWP oProblem) {

			this.oInstance = oInstance;
			this.oProblem = oProblem;
		}

		int map(double d, double min_x, double max_x, long out_min, long out_max) {
			return (int) ((d - min_x) * (out_max - out_min) / (max_x - min_x) + out_min);
		}

		public void updateSolution(PWPSolutionInterface[] current, PWPSolutionInterface[] candidate,
				PWPSolutionInterface best) {

			this.repaint();
		}

		LinkedList<Color> oColorStack = new LinkedList<Color>();

		private void drawEnvelope(Graphics g, int x, int y) {

			oColorStack.push(g.getColor());

			g.setColor(new Color(160, 160, 80));
			g.fillRect(x, y, 12, 9);

			g.setColor(Color.WHITE);

			// 12 x 9
			g.drawLine(x, y, x + 12, y);
			g.drawLine(x + 12, y, x + 6, y + 6);
			g.drawLine(x + 6, y + 6, x, y);
			g.drawLine(x, y, x, y + 9);
			g.drawLine(x, y + 9, x + 12, y + 9);
			g.drawLine(x + 12, y + 9, x + 12, y);

			g.setColor(oColorStack.pop());
		}

		private void drawHouse(Graphics g, int x, int y, int width, int height) {

			oColorStack.push(g.getColor());

			g.setColor(new Color(160, 80, 80));
			g.fillRect(x, y, width, height);

			g.setColor(Color.WHITE);

			int HALF_WIDTH = width / 2;
			int THIRD_WIDTH = width / 3;
			int TWOTHIRDS_WIDTH = THIRD_WIDTH * 2;
			
			int QUARTER_HEIGHT = height / 4;
			int HALF_HEIGHT = height / 2;

			// 12 x 12
			g.drawLine(x, y + QUARTER_HEIGHT, x + HALF_WIDTH, y);
			g.drawLine(x + HALF_WIDTH, y, x + width, y + QUARTER_HEIGHT);
			g.drawLine(x + width, y + QUARTER_HEIGHT, x, y + QUARTER_HEIGHT);
			g.drawLine(x, y + QUARTER_HEIGHT, x, y + height);
			g.drawLine(x, y + height, x + width, y + height);
			g.drawLine(x + width, y + height, x + width, y + QUARTER_HEIGHT);
			// door
			g.drawLine(x + THIRD_WIDTH, y + height, x + THIRD_WIDTH, y + HALF_HEIGHT);
			g.drawLine(x + THIRD_WIDTH, y + HALF_HEIGHT, x + TWOTHIRDS_WIDTH, y + HALF_HEIGHT);
			g.drawLine(x + TWOTHIRDS_WIDTH, y + height, x + TWOTHIRDS_WIDTH, y + HALF_HEIGHT);

			g.setColor(oColorStack.pop());
		}

		public void drawPWP(AIM_PWP oProblem, Graphics g) {

			PWPSolutionInterface solution = oProblem.oBestSolution;
			if (solution != null && solution.getSolutionRepresentation() != null
					&& solution.getSolutionRepresentation().getSolutionRepresentation() instanceof int[]) {

				PWPSolution oPWPSolution = (PWPSolution) solution;
				if (solution != null && solution.getSolutionRepresentation() != null) {

					int[] rep = oPWPSolution.getSolutionRepresentation().getSolutionRepresentation();
					Location oHomeLocation = oProblem.getLoadedInstance().getHomeAddress();
					Location oPostalDepotLocation = oProblem.getLoadedInstance().getPostalDepot();

					int width = getWidth();
					int height = getHeight();

					double max_x = Integer.MIN_VALUE;
					double max_y = Integer.MIN_VALUE;
					double min_x = Integer.MAX_VALUE;
					double min_y = Integer.MAX_VALUE;

					// find min and max x and y coordinates
					max_x = Math.max(max_x, oHomeLocation.getX());
					max_y = Math.max(max_y, oHomeLocation.getY());
					min_x = Math.min(min_x, oHomeLocation.getX());
					min_y = Math.min(min_y, oHomeLocation.getY());

					max_x = Math.max(max_x, oPostalDepotLocation.getX());
					max_y = Math.max(max_y, oPostalDepotLocation.getY());
					min_x = Math.min(min_x, oPostalDepotLocation.getX());
					min_y = Math.min(min_y, oPostalDepotLocation.getY());

					for (int i : rep) {

						Location l = oInstance.getLocationForDelivery(rep[i]);
						max_x = Math.max(max_x, l.getX());
						max_y = Math.max(max_y, l.getY());
						min_x = Math.min(min_x, l.getX());
						min_y = Math.min(min_y, l.getY());
					}

					// draw postal office to first delivery
					int x1, x2, y1, y2;
					Location l1 = oPostalDepotLocation, l2 = oInstance.getLocationForDelivery(rep[0]);
					x1 = map(l1.getX(), min_x, max_x, 10, width - 10);
					x2 = map(l2.getX(), min_x, max_x, 10, width - 10);
					y1 = height - map(l1.getY(), min_y, max_y, 10, height - 10);
					y2 = height - map(l2.getY(), min_y, max_y, 10, height - 10);

					g.setColor(Color.YELLOW);
					g.drawLine(x1, y1, x2, y2);

					g.setColor(oCitiesColor);
					g.fillOval(x1 - 2, y1 - 2, 4, 4);

					drawEnvelope(g, x1, y1);

					// draw delivery routes
					for (int i = 0; i < rep.length - 1; i++) {

						l1 = oInstance.getLocationForDelivery(rep[i]);
						l2 = oInstance.getLocationForDelivery(rep[i + 1]);

						x1 = map(l1.getX(), min_x, max_x, 10, width - 10);
						x2 = map(l2.getX(), min_x, max_x, 10, width - 10);
						y1 = height - map(l1.getY(), min_y, max_y, 10, height - 10);
						y2 = height - map(l2.getY(), min_y, max_y, 10, height - 10);

						g.setColor(oRoutesColor);
						g.drawLine(x1, y1, x2, y2);

						g.setColor(oCitiesColor);
						g.fillOval(x1 - 2, y1 - 2, 4, 4);
					}

					// draw route from last delivery to home
					l1 = oInstance.getLocationForDelivery(rep[rep.length - 1]);
					l2 = oHomeLocation;
					x1 = map(l1.getX(), min_x, max_x, 10, width - 10);
					x2 = map(l2.getX(), min_x, max_x, 10, width - 10);
					y1 = height - map(l1.getY(), min_y, max_y, 10, height - 10);
					y2 = height - map(l2.getY(), min_y, max_y, 10, height - 10);

					g.setColor(Color.YELLOW);
					g.drawLine(x1, y1, x2, y2);

					g.setColor(oCitiesColor);
					g.fillOval(x2 - 2, y2 - 2, 4, 4);

					drawHouse(g, x2, y2, 12, 12);

				}
			} else {
				g.setColor(Color.WHITE);
				System.out.println("Unsupported");
				g.drawString("Unsupported solution representation...", (int) (0), (int) (getHeight() / 2.0));
			}
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			int width = getWidth();
			int height = getHeight();

			g.setColor(Color.BLACK);
			g.fillRect(0, 0, width, height);

			if (oProblem != null) {
				this.drawPWP(oProblem, g);
			}

			g.dispose();

		}
	}
}
