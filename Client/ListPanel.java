import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class ListPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    private JPanel fillerPanel;
    private ArrayList<JPanel> panels;

    public ListPanel(List<JPanel> panels, int height) {
        this(panels, height, new Insets(2, 0, 2, 0));
    }

    public ListPanel(List<JPanel> panels, int height, Insets insets) {
        this();
        for (JPanel panel : panels)
            addPanel(panel, height, insets);
    }

    public ListPanel() {
        super();
        this.fillerPanel = new JPanel();
        this.fillerPanel.setMinimumSize(new Dimension(0, 0));
        this.panels = new ArrayList<JPanel>();
        setLayout(new GridBagLayout());
    }

    public void addPanel(JPanel p, int height) {
        addPanel(p, height, new Insets(2, 0, 2, 0));
    }

    public void addPanel(JPanel p, int height, Insets insets) {
        super.remove(fillerPanel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = getComponentCount();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.ipady = height;
        gbc.insets = insets;
        gbc.weightx = 1.0;
        panels.add(p);
        add(p, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = getComponentCount();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weighty = 1.0;
        add(fillerPanel, gbc);
        revalidate();
        invalidate();
        repaint();
    }

    public void removePanel(JPanel p) {
        removePanel(panels.indexOf(p));
    }

    public void removePanel(int i) {
        if(getPanels().size()>0)
        {
            super.remove(i);
            panels.remove(i);
            revalidate();
            invalidate();
            repaint();
        }
    }

    public ArrayList<JPanel> getPanels() {
        return this.panels;
    }
    
    public static JPanel getRandomJPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.add(new JLabel("This is a randomly sized JPanel"));
        panel.setBackground(new Color(new Random().nextFloat(), new Random().nextFloat(), new Random().nextFloat()));
        return panel;
    }
}